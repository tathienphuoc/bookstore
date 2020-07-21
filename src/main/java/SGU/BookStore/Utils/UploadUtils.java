package SGU.BookStore.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UploadUtils {
    //lưu hình ảnh vào thư mục cục bộ
    public static void uploadToLocal(MultipartFile file, Path path) throws IOException {
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
