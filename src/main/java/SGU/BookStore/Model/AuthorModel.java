package SGU.BookStore.Model;

import SGU.BookStore.Entity.Author;
import SGU.BookStore.Repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class AuthorModel {
    //tự động inject các bean cần thiết
    @Autowired
    AuthorRepo authorRepo;
    //trả về mảng các tác giả có trong CSDL
    public List<Author> findAll(){
        return authorRepo.findAll();
    }
    //tìm tác giả dựa theo id
    public Author findById(Integer id) {
        Optional<Author> author = authorRepo.findById(id);
        return author.isPresent() ? author.get() : null;//trả về null nếu không tìm thấy
    }
}
