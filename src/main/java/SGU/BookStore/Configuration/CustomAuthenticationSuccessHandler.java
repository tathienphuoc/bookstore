package SGU.BookStore.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

//annotation @Configuration thông báo cho Spring đây là class đảm nhận chức năng cấu hình
@Configuration
//thục thi interface AuthenticationSuccessHandler để điều hướng trang web sau khi đăng nhập thành công
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //ghi đè phương thức onAuthenticationSuccess
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException, IOException {

        //mảng roles lưu trữ tên các quyền
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //nếu quyền là ADMIN
        if (roles.contains("ADMIN")) {
            //điều hướng đến trang quản trị
            httpServletResponse.sendRedirect("/booksAdmin");
        } else {
            //tất cả các quyền còn lại sẽ được điều hướng đến trang mua sách
            httpServletResponse.sendRedirect("/books");
        }
    }
}
