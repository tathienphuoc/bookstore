package SGU.BookStore.Configuration;

import SGU.BookStore.Model.AccountDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

//annotation @EnableWebSecurity thông báo cho Spring đây là class đảm nhận chức năng cấu hình
@EnableWebSecurity
//kế thừa class WebSecurityConfigurerAdapter để cấu hình bảo mật cho hệ thống
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //tự động inject bean phù hợp
    @Autowired
    AccountDetailModel accountDetailModel;

    private static final String ADMIN="ADMIN";
    private static final String USER="USER";

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    //tự động inject bean phù hợp vào phương thức
    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    //khai báo bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        //Sử dụng thuật toán BCrypt để mã hóa password
        return new BCryptPasswordEncoder();
    }

    //ghi đè phương thức configure để xác thực đăng nhập
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Chỉ định lớp service và cách mã hóa password
        auth.userDetailsService(accountDetailModel);//.passwordEncoder(passwordEncoder());
    }

    //ghi đè phương thức configure để phân quyền
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Phân quyền
        http
                //khi có yêu cầu gửi tới
                .authorizeRequests()

                //chỉ các tài khoản đã đăng nhập và có quyền tên ADMIN mới có thể truy cập các đường dẫn này
                .antMatchers("/statistic/**","/shipping/**","/addBook/**","/editBook/**","/booksAdmin/**").hasAnyAuthority(ADMIN)

                //các đường dẫn dưới đây yêu cầu phải đăng nhập
                .antMatchers("/profile","/transaction","/shoppingCart").authenticated()//.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

                //những yêu cầu còn lại được truy cập mà không yêu cầu gì
                .anyRequest().permitAll()
                .and()

                .formLogin()
                //chỉ định trang đăng nhập tự tạo
                .loginPage("/login")
                //điều hướng đăng nhập dựa trên authenticationSuccessHandler
                .successHandler(authenticationSuccessHandler)
                //trang đăng nhập có thể truy cập mà không yêu cầu gì
                .permitAll()
                .and()

                //tất cả tài khoản đều được phép đăng xuất
                .logout()
                .permitAll()
                .and()

                //nếu truy cập vào trang không nằm trong phạm vi phân quyền
                .exceptionHandling()
                //bị chuyển hướng đến trang từ chối
                .accessDeniedPage("/accessDenied");

    }

    //Spring security sẽ tạo ra trang login mặc định
    //khi muốn tự tạo phải thêm phương thức này vào
    public void addViewControllers(ViewControllerRegistry registry) {
        // /login là đường dẫn đến trang đăng nhập tự tạo
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
