package SGU.BookStore.Utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    //kiểm tra đã có tài khoản đăng nhập hay chưa
    public static boolean isPresent(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return (authentication instanceof AnonymousAuthenticationToken)?false:true;
    }

    //trả về tên sản phẩm đang đăng nhập
    public static String getUserName(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return (authentication instanceof AnonymousAuthenticationToken)?null:authentication.getName();
    }
}
