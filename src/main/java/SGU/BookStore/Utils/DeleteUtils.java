package SGU.BookStore.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteUtils {
    //xóa hình ảnh dựa trên đường dẫn
    public static void deleteImage(Path pathName){
        try {
            if(Files.exists(pathName))
                Files.delete(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
