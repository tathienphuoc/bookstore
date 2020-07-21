package SGU.BookStore.Model;


import SGU.BookStore.Entity.Category;
import SGU.BookStore.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class CategoryModel {
    //tự động inject các bean cần thiết
    @Autowired
    CategoryRepo categoryRepo;

    //trả về mảng các thể loại có trong CSDL
    public List<Category> getAllCategory()
    {
        List<Category> categories = (List<Category>) categoryRepo.findAll();
        return categories.size()>0?categories:null;
    }

    //tìm thể loại dựa trên tên thể loại
    public Category findByName(String s) {
        Optional<Category> category = categoryRepo.findByName(s);
        return category.isPresent() ? category.get() : null;//trả về null nếu không tìm thấy
    }

    //tìm thể loại dựa trên id
    public Category findById(Integer id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.isPresent() ? category.get() : null;//trả về null nếu không tìm thấy
    }
}
