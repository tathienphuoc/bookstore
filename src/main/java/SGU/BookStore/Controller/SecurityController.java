package SGU.BookStore.Controller;

import SGU.BookStore.Entity.Account;
import SGU.BookStore.Model.AccountModel;
import SGU.BookStore.Model.RoleModel;
import SGU.BookStore.Utils.HelperUtils;
import SGU.BookStore.Utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//annotation @Controller thông báo cho Spring đây là class đảm nhận chức năng điều khiển(C trong MVC)
@Controller
//class này sẽ đảm nhận các vấn đề liên quan đến bảo mật
public class SecurityController {
    public static final String USER="USER";
    //tự động inject các bean cần thiết
    @Autowired
    RoleModel roleModel;
    @Autowired
    AccountModel accountModel;
    //đường dẫn thư mục sẽ lưu ảnh được tải về từ CSDL
    //hạn chế truy xuất khi không cần thiết
    private final String UPLOAD_DIR = "src/main/images/";

    //yêu cầu đăng nhập
    @RequestMapping("/login")
    public String login(){
        //2 tài khoản mặc định là admin và user sẽ được tạo ra
        accountModel.createDefault();
        //hiển thị trang đăng nhập
        return "login";
    }

    //yêu cầu đăng ký
    @RequestMapping("/registry")
    public String registry(Model model){
        //khỏi tạo 1 tài khoản mới
        model.addAttribute("account",new Account());
        //tìm tất cả tên của các tài khoản
        //phục vụ cho việc kiểm tra tên đăng ký đã tồn tại hay chưa
        model.addAttribute("users",accountModel.findAll());
        //hiển thị trang đăng ký
        return "registry";
    }

    //yêu cầu khôi phục mật khẩu
    @RequestMapping("/forgotpwd")
    public String forgotpwd(Model model){
        model.addAttribute("account",new Account());
        //tìm tất cả tên của các tài khoản
        //chỉ có thể khôi phục mật khẩu khi tên mật khẩu đã tồn tại
        model.addAttribute("users",accountModel.findAll());
        return "forgotpwd";
    }

    //xử lý action của form
    @RequestMapping("/changepwd")
    public String changepwd(@ModelAttribute("account") Account account){
        //lấy tài khoản từ CSDL
        Account entity=accountModel.findByUserName(account.getUserName());
        //mã hóa mật khẩu
        entity.setPwd(new BCryptPasswordEncoder().encode(account.getPwd()));
        //cập nhật tài khoản
        accountModel.createOrUpdate(entity);
        //hiển thi trang đăng nhập
        return "login";
    }

    //xử lý yêu cầu xem thông tin cá nhân
    @RequestMapping("/profile")
    public String profile(Model model){
        //lấy tên của tài khoản đang đăng nhập
        String name=SecurityUtils.getUserName();
        //lấy tài khoản từ CSDL thông qua tên
        Account user=accountModel.findByUserName(name);
        //lưu hình ảnh truy xuất từ CSDL vào thư mực cục bộ nếu có
        HelperUtils.saveImageFromDatabaseToLocalFolder(user.getImage(),Paths.get(UPLOAD_DIR + "USER_ID=" + user.getId() + ".jpg"));
        model.addAttribute("users",accountModel.findAll());
        //truyền thông tin tài khoản vào V thông qua tên user
        model.addAttribute("user",user);
        model.addAttribute("pwd",user.getPwd());
        return "profile";
    }

    //xử lý action lưu thay đổi của tài khoản
    @RequestMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("user")Account account,@RequestParam("file") MultipartFile file
            ,@RequestParam("roleID") int roleID){
        //mã hóa mật khẩu
        account.setPwd(new BCryptPasswordEncoder().encode(account.getPwd()));
        //cập nhật lại vai trò
        account.setRole(roleModel.getRole(roleID));
        //lưu tài khoản vào CSDL
        accountModel.createOrUpdate(account);
        //nếu có file ảnh đi kèm
        if (!file.isEmpty()) {
            try {
                //chuyển file ảnh thảnh mảng byte sau đó lưu vào đối tượng
                account.setImage(file.getBytes());

                //lưu ảnh vào thư mực cục bộ với tên USER_ID=?.jpg
                //với ? là id của tài khoản
                Path path = Paths.get(UPLOAD_DIR+"USER_ID=" + account.getId() + ".jpg");
                //lưu ảnh thay thế nếu đã tồn tại ảnh có cùng tên
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                //lưu ảnh không thành công sẽ ném ra ngoại lệ
                e.printStackTrace();
            }
            //cập nhật vào CSDL
            accountModel.createOrUpdate(account);
        }
        //hiển thị trang thông tin cá nhân
        return "profile";
    }

    //xử lý action của form đăng ký
    @RequestMapping("/saveAccount")
    public String saveAccount(@ModelAttribute("account")Account account){
        //mặc định khi đăng ký sẽ có quyền là USER
        //chỉ có 1 tài khoản duy nhất có quyền ADMIN
        account.setRole(roleModel.findByName(USER));
        //mã hóa mật khẩu
        account.setPwd(new BCryptPasswordEncoder().encode(account.getPwd()));
        //lưu vào CSDL
        accountModel.createOrUpdate(account);
        //đăng ký thành công sẽ chuyển đến trang đăng nhập
        return "login";
    }

    //hiện thị trang từ chối truy cập nếu truy cập vào trang vượt qua quyền của bản thân
    @RequestMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}
