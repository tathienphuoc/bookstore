package SGU.BookStore.Controller;

import SGU.BookStore.Model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//annotation @Controller thông báo cho Spring đây là class đảm nhận chức năng điều khiển(C trong MVC)
@Controller
public class HomeController {
    //tự động inject các bean cần thiết
    @Autowired
    BookModel bookModel;
    //khi có yêu cầu xem trang chủ
    @RequestMapping(value = {"/","/index","home"})
    public String home(){
        //hiển thị trang chủ
        return "index";
    }


}
