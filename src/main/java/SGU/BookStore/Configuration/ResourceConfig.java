package SGU.BookStore.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//annotation @Configuration thông báo cho Spring đây là class đảm nhận chức năng cấu hình
@Configuration
//thục thi interface WebMvcConfigurer để hỗ trợ cấu hình phần view(V trong MVC) cho hệ thống
public class ResourceConfig implements WebMvcConfigurer {

    //ghi đè phương thức addResourceHandlers để chỉ định đường dẫn
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        //chỉ định nơi để lấy ảnh
        // /images thay thay cho đường dẫn tuyệt đối BookStore/src/main/images/
        registry.addResourceHandler("/images/**").addResourceLocations("file:src/main/images/");
        //tương tự như trên
        registry.addResourceHandler("/default/**").addResourceLocations("file:src/main/java/default/");

    }
}
