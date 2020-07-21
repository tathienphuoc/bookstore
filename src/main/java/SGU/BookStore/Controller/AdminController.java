package SGU.BookStore.Controller;

import SGU.BookStore.Entity.Book;
import SGU.BookStore.Model.*;
import SGU.BookStore.Utils.ExportExcel;
import SGU.BookStore.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
//annotation @Controller thông báo cho Spring đây là class đảm nhận chức năng điều khiển(C trong MVC)
@Controller
public class AdminController {
    //đường dẫn thư mục sẽ lưu ảnh được tải về từ CSDL
    //hạn chế truy xuất khi không cần thiết
    private final String UPLOAD_DIR = "src/main/images/";
    private final String pathExportExcel="C:/Users/Pc/Desktop/BookStore/src/main/java/default/statistic.xlsx";
    //tự động inject các bean cần thiết
    @Autowired
    BookModel bookModel;
    @Autowired
    CategoryModel categoryModel;
    @Autowired
    AuthorModel authorModel;
    @Autowired
    ShippingModel shippingModel;
    @Autowired
    CartitemModel cartitemModel;
    @Autowired
    AccountModel accountModel;

    //khi có yêu cầu có địa chỉ là /booksAdmin, /booksAdmin/page={page}
    @RequestMapping(value = {"/booksAdmin","/booksAdmin/page={page}"})
    //phương thức booksAdmin sẽ chịu trách nhiệm xử lý yêu cầu
    //Model là đối tượng được sử dụng để truyền các thông cần thiết cho thymeleaf(V của MVC)
    //Annotation @PathVariable sẽ lấy giá trị có tên là page( {page}) trên đường dẫn
    //Giá trị page sẽ được truyền vào Option<Integer> để có thể xử lý trường họp giá trị page không được truyền giá trị
    public String booksAdmin(Model model, @PathVariable(value = "page") Optional<Integer> page){
        //phương thức saveProductImageToLocalFolder() dùng để lưu các hình ảnh của sản phẩm được lưu trữ trên CSDL
        //vào thư mục cục bộ nhằm mục đích tải ảnh nhanh hơn
        bookModel.saveProductImageToLocalFolder();
        //biến current( chỉ trang hiện tại cần truy xuất)
        // sẽ lưu giá trị page nếu giá trị biến page chưa được gán thì sẽ mặc định current=1
        int current = (page.isPresent() && page.get() > 0) ? page.get() : 1;
        Page<Book> books;
        int totalPages;
        //books chứa các trang được phân với kích thước mỗi trang là 8
        books = bookModel.getAllBook(current , 8);
        //totalPage là số lượng trang tối đa được phân
        totalPages = books.getTotalPages();
        //nếu biến page được gán giá trị > số lượng trang hiện có
        if (current > totalPages) {
            //thì biến current sẽ được gán là trang cuối cùng
            current = totalPages;
            books = bookModel.getAllBook(current , 8);
        }
        //begin sẽ hiển thị nút trang đầu tiên có thể là 1 hoặc trang hiện lại -2
        int begin = Math.max(1, current - 2);
        //end sẽ hiển thị nút trang cuối cùng có thể là begin+3 hoặc số trang cuối cùng
        int end = Math.min(begin + 3, totalPages);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        //truyền giá trị cần thiết cho V để phục vụ mục đích phân trang
        model.addAttribute("current", current);
        model.addAttribute("totalPages", totalPages);
        //books sẽ được truyền cho V dưới cái tên books
        model.addAttribute("books", books);
        //tải trang phù hợp để hiển thị các thông tin
        return "booksAdmin";
    }

    //khi có yêu cầu có địa chỉ URL là /editBook/{id}, /addBook
    @RequestMapping(value = {"/editBook/{id}", "/addBook"})
    //phương thức addEditBook sẽ chịu trách nhiệm xử lý yêu cầu
    public String addEditBook(Model model, @PathVariable("id") Optional<Integer> id) {
        //truyền mảng các danh mục và tác giả để người quản trị có thể lựa chọn
        model.addAttribute("categories",categoryModel.getAllCategory());
        model.addAttribute("authors",authorModel.findAll());
        //nếu biến id được gán thì đó là yêu cầu chỉnh sửa /editBook/{id}
        if (id.isPresent()) {
            Book book = bookModel.getBook(id.get());
            //gửi đến form chỉnh sửa đối tượng có id là {id }trong bảng BOOK
            model.addAttribute("book", book);
            return "add-edit-book";
        }
        //ngược lại là yêu cầu thêm mới /addBook
        //gửi đến form chỉnh sửa đối tượng mới
        model.addAttribute("book",new Book());
        return "add-edit-book";
    }


    //sau khi đã thêm mới hoặc chỉnh sửa những thông tin đó sẽ gửi yêu cầu lưu trữ( action của form)
    @RequestMapping("/saveBook")
    //@ModelAttribute("book") sẽ lấy đối tượng đã được gửi đến form thông qua phương thức addEditBook
    //@RequestParam("file") sẽ đảm nhận việc lưu trữ hình ảnh
    public String saveProfile(@ModelAttribute("book")Book book, @RequestParam("file") MultipartFile file
            , @RequestParam("categoryId") Optional<Integer> categoryID, @RequestParam("authorId") int authorID){
        //tên tác giả được cập nhật
        book.setAuthor(authorModel.findById(authorID));
        //tên tác giả được cập nhật mặc định sẽ là thể lại Khác(id là 1)
        book.setCategory(categoryModel.findById(categoryID.isPresent()?categoryID.get():1));
        //lưu vào CSDL
        bookModel.createOrUpdate(book);
        //nếu có file ảnh đi kèm
        if (!file.isEmpty()) {
            try {
                //chuyển file ảnh thảnh mảng byte sau đó lưu vào đối tượng
                book.setImage(file.getBytes());

                //lưu ảnh vào thư mực cục bộ với tên BOOK_ID=?.jpg
                //với ? là id của sản phẩm
                Path path = Paths.get(UPLOAD_DIR+"BOOK_ID=" + book.getId() + ".jpg");
                //lưu ảnh thay thế nếu đã tồn tại ảnh có cùng tên
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                //lưu ảnh không thành công sẽ ném ra ngoại lệ
                e.printStackTrace();
            }
            //cập nhật vào CSDL
            bookModel.createOrUpdate(book);
        }
        //quay về trang quản trị trước đó sau khi thêm thành công
        return "redirect:/booksAdmin";
    }

    //khi có yêu cầu xóa sản phẩm được gửi đến thông qua URL /deleteBook/{id}
    @RequestMapping(value = {"/deleteBook/{id}"})
    //phương thức deleteBook sẽ xử lý yêu cầu xóa sản phẩm
    public String deleteBook( @PathVariable("id") Optional<Integer> id) {
        //lấy đối tượng trong CSDL
        Book book=bookModel.getBook(id.get());
        //xóa ảnh được lưu trữ trong CSDL( nếu có )
        HelperUtils.deleteImage(Paths.get(UPLOAD_DIR+"BOOK_ID=" + book.getId() + ".jpg"));
        //xóa đối tượng trong CSDL
        bookModel.deleteBook(id.get());
        //quay về trang quản trị trước đó sau khi xóa thành công
        return "redirect:/booksAdmin";
    }

    //khi có yêu cầu truy cập trang giao hàng gửi đến thông qua URL /shipping
    @RequestMapping(value = {"/shipping"})
    public String shipping(Model model) {
        //gửi đến trang shipping 1 đối tượng shipping đóng vai trò như là một hóa đơn
        model.addAttribute("shippings",shippingModel.getShipping());
        return "shipping";
    }

    //khi có yêu cầu thực hiện giao hàng gửi đến thông qua URL /shipping{id}
    @RequestMapping(value = {"/shipping/{id}"})
    public String shipping(Model model,@PathVariable("id") Optional<Integer> id) {
        //lưu đối tượng vào CSDL
        shippingModel.shipping(id.get());
        //làm mới trang giao hàng
        return "redirect:/shipping";
    }

    //khi có yêu cầu thống kê gửi đến thông qua URL /statistic
    @RequestMapping(value = {"/statistic"})
    public String statistic(Model model) {
        //mảng các sách bán chạy nhất sẽ được gửi đến trang thống kê thông qua biến popular
        model.addAttribute("popular",cartitemModel.getStatisticPopular());
        //mảng các tài khoản mua nhiều sách nhất sẽ được gửi đến trang thống kê thông qua biến clients
        model.addAttribute("clients",accountModel.topClients());
        //hiển thị trang thống kê
        try {
            File fileExcel=new File(pathExportExcel);
            if(fileExcel.exists()){
                fileExcel.delete();
            }
            fileExcel.createNewFile();
            ExportExcel.writeExcel(cartitemModel.getStatisticPopular(), pathExportExcel);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "statistic";
    }

    //khi có yêu cầu xem thông tin thành viên trong nhóm
    @RequestMapping(value = {"/info"})
    public String info(Model model) {
        //trang info sẽ hiển thị tên và MSSV của thành viên
        return "info";
    }

    //khi có yêu cầu tìm kiếm theo từ khóa
    @GetMapping(value = {"/booksAdmin/search"})
    //mặc định từ khóa tìm kiếm là null
    public String search(Model model,@RequestParam(defaultValue="") String search){
        //nếu không có từ khóa sẽ hiện thị trang quản trị với tất cả sản phẩm
        if (search.equalsIgnoreCase(""))
            return booksAdmin(model, Optional.of(1));
        //nếu có từ khóa tìm kiếm sẽ hiện thị trang  với kết quả tìm kiếm tương ứng
        model.addAttribute("books",bookModel.findByNameLike(search));
        //hiển thị trang tìm kiếm
        return "booksSearchAdmin";
    }
}
