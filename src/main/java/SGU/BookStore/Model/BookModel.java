package SGU.BookStore.Model;

import SGU.BookStore.Entity.Book;
import SGU.BookStore.Repository.BookRepo;
import SGU.BookStore.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class BookModel {
	//tự động inject các bean cần thiết
	@Autowired
	BookRepo bookRepo;
	//đường dẫn thư mục sẽ lưu ảnh được tải về từ CSDL
	//hạn chế truy xuất khi không cần thiết
	private static final String UPLOAD_DIR = "src/main/images/";

	//trả về mảng các sách có trong CSDL
	public List<Book> getAllBook()
	{
		List<Book> books = (List<Book>) bookRepo.findAll();
		//trả về null nếu mảng trống
		return books.size()>0?books:null;
	}

	//phục vụ cho mục đích phân trang
	//truyền vào số trang và kích thước
	public Page<Book> getAllBook(int page, int size)
	{
		//Mặc định book được thêm sau cùng sẽ hiện đầu tiên(mặc định sắp xếp theo id)
		Page<Book> books =bookRepo.findAll(PageRequest.of(page-1,size, Sort.Direction.DESC,"id"));
		//trả về mảng các sách thuộc trang đó
		return books;
	}

	//phục vụ cho mục đích phân trang
	//truyền vào số trang và kích thước
	//có yêu cầu sắp xếp
	public Page<Book> getAllBook(int page, int size,String sort)
	{
		Page<Book> books=null;
		//Mặc định book được thêm sau cùng sẽ hiện đầu tiên(mặc định sắp xếp theo id)
		//theo giá
		if (sort.equalsIgnoreCase("price")) {
			books =bookRepo.findAll(PageRequest.of(page-1,size, Sort.Direction.DESC,sort));
		}
		else{//theo tên
			books =bookRepo.findAll(PageRequest.of(page-1,size, Sort.Direction.ASC,sort));
		}
		return books;
	}


	//lấy sách dựa trên id
	public Book getBook(Integer Id)
	{
		Optional<Book> book=bookRepo.findById(Id);
		return book.isPresent()?book.get():null;//trả về null nếu không tìm thấy
	}

	//tạo mới hoặc cập nhật sách
	public Book createOrUpdate(Book book)
	{
		//tạo mới
		//chưa có id -> không tồn tại trong CSDL
		if(book.getId()  == null)
		{
			book = bookRepo.save(book);//Khởi tạo
			return book;//trả về đối tượng tương ứng trong CSDL(có id)
		}

		//cập nhật
		//có id
		else
		{
			Book entity = getBook(book.getId());
			//Tìm thấy đối tượng có id là Id trong bảng sách
			if(entity!=null)
			{
				return bookRepo.save(book);//Cập nhật
			} else {
				//đối tượng không tồn tại trong table BOOK
				//đã được đặt id vì lý do nào đó
				entity = bookRepo.save(entity);//Khởi tạo
				return bookRepo.save(entity);//trả về đối tượng vùa được khởi tại
			}
		}
	}

	//xóa khỏi bảng sách dựa theo id
	public void deleteBook(Integer Id){
		Book book =getBook(Id);
		//Nếu có có đối tượng có id là ID thì xóa
		if(book!=null)
			bookRepo.deleteById(Id);
	}

	//Dùng cho chức năng search
	public List<Book> findByNameLike(String s) {
		return bookRepo.findByTitleLike("%"+s+"%");
	}

	//phương thức được dùng để tải các hình ảnh của bảng BOOk trong CSDl
	//và lưu trữ những hình ảnh đó dưới tên BOOK_ID=? với ? là id của bảng ghi trong CSDL
	public void saveProductImageToLocalFolder(){
		List<Book> books=bookRepo.findAll();
		for(Book book: books)
			if (book.getImage()!=null)
				HelperUtils.saveImageFromDatabaseToLocalFolder(book.getImage(), Paths.get(UPLOAD_DIR + "BOOK_ID=" + book.getId() + ".jpg"));

	}
}