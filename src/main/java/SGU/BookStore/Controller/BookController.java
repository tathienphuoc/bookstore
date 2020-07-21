package SGU.BookStore.Controller;

import SGU.BookStore.Entity.Book;
import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Model.AccountModel;
import SGU.BookStore.Model.AuthorModel;
import SGU.BookStore.Model.BookModel;
import SGU.BookStore.Model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
//annotation @Controller thông báo cho Spring đây là class đảm nhận chức năng điều khiển(C trong MVC)
@Controller
public class BookController {
    //đường dẫn thư mục sẽ lưu ảnh được tải về từ CSDL
    //hạn chế truy xuất khi không cần thiết
    private final String UPLOAD_DIR = "src/main/images/";

    //tự động inject các bean cần thiết
    @Autowired
    BookModel bookModel;
    @Autowired
    AccountModel accountModel;
    @Autowired
    AuthorModel authorModel;
    @Autowired
    CategoryModel categoryModel;
    @RequestMapping("/abcd")
    public String abcd(Model model){

        model.addAttribute("users",accountModel.findAll());
        return "abcd";
    }

    @RequestMapping("/babc")
    public String babc(Model model){
        model.addAttribute("books",bookModel.getAllBook());
        return "babc";
    }

    //trang bán hàng được hiển thị dành cho người dùng
    //các chức năng phân trang tương tự như trang quản trị
    //nhưng không thể thêm sửa hoặc xóa sản phẩm
    @RequestMapping(value = {"/books","/books/page={page}"})
    public String books(Model model, @PathVariable(value = "page") Optional<Integer> page){
        bookModel.saveProductImageToLocalFolder();
        int current = (page.isPresent() && page.get() > 0) ? page.get() : 1;
        Page<Book> books;
        int totalPages;
        books = bookModel.getAllBook(current , 8);
        totalPages = books.getTotalPages();
        if (current > totalPages) {
            current = totalPages;
            books = bookModel.getAllBook(current , 8);
        }
        int begin = Math.max(1, current - 2);
        int end = Math.min(begin + 3, totalPages);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("current", current);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("books", books);
        return "books";
    }

    //khi yêu cầu sắp xếp gửi tới
    //có thể sắp xếp theo tên hoặc theo tên
    @RequestMapping(value = {"/books/sortBy=title","/books/sortBy=title/page={page}"})
    public String booksName(Model model, @PathVariable(value = "page") Optional<Integer> page){
        int current = (page.isPresent() && page.get() > 0) ? page.get() : 1;
        Page<Book> books;
        int totalPages;
        books = bookModel.getAllBook(current , 8,"title");
        totalPages = books.getTotalPages();
        if (current > totalPages) {
            current = totalPages;
            books = bookModel.getAllBook(current , 8);
        }
        int begin = Math.max(1, current - 2);
        int end = Math.min(begin + 3, totalPages);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("current", current);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("books", books);
        //trang sắp xếp theo tên đuọc trả về
        return "booksName";
    }


    //khi yêu cầu sắp xếp gửi tới
    //có thể sắp xếp theo tên hoặc theo giá
    @RequestMapping(value = {"/books/sortBy=price","/books/sortBy=price/page={page}"})
    public String booksPrice(Model model, @PathVariable(value = "page") Optional<Integer> page){
        int current = (page.isPresent() && page.get() > 0) ? page.get() : 1;
        Page<Book> books;
        int totalPages;
        books = bookModel.getAllBook(current , 8,"price");
        totalPages = books.getTotalPages();
        if (current > totalPages) {
            current = totalPages;
            books = bookModel.getAllBook(current , 8);
        }
        int begin = Math.max(1, current - 2);
        int end = Math.min(begin + 3, totalPages);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("current", current);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("books", books);
        //trang sắp xếp theo giá đuọc trả về
        return "booksPrice";
    }

    //yêu cầu tìm kiếm của người dùng tương tự yêu cầu tìm kiếm của người quản trị
    //nhưng không thể thêm sửa hoặc xóa sản phẩm
    //chỉ có thể xem sản phẩm
    @GetMapping(value = {"/books/search","/books/sortBy=title/search","/books/sortBy=price/search"})
    public String search(Model model,@RequestParam(defaultValue="") String search){
        if (search.equalsIgnoreCase(""))
            return books(model, Optional.of(1));
        model.addAttribute("books",bookModel.findByNameLike(search));
        return "booksSearch";
    }

    //khi yêu cầu xem chi tiết thông tin sản phẩm được gửi tới
    //dựa trên id được cung cấp
    @GetMapping(value = {"/viewBook/{id}","/books/viewBook/{id}"})
    public String productPage(Model model,@PathVariable("id") Optional<Integer> id){
        if(!id.isPresent())
            return books(model, Optional.of(1));
        Book book=bookModel.getBook(id.get());
        //thông tin về sách được trả về
        model.addAttribute("book",book);
        //1 giỏ hàng tương ứng được thêm vào nếu đặt hàng
        model.addAttribute("cartitem",new Cartitem());
        //trả về trang xem sản phẩm
        return "viewBook";
    }

    //khi có yêu cầu xóa hoặc sửa
    @GetMapping(value = {"/add-edit-book"})
    public String productPage(Model model){
        model.addAttribute("book",new Book());
        model.addAttribute("categories",categoryModel.getAllCategory());
        model.addAttribute("authors",authorModel.findAll());
        //trả về trang xóa hoặc sửa tùy vào địa chỉ URL
        return "add-edit-book";
    }
}
