package SGU.BookStore.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateUtils {

    //tạo thư mục cục bộ dùng để lưu hình ảnh với tên là images
    public static void createDirImages(){
        createDir("images");
    }

    //tạo thư mục cục bộ dùng để lưu hình ảnh với tên thư mục được truyền từ bên ngoài vào
    public static void createDir(String nameDir){
        String pathName="src/main/"+nameDir;
        Path path= Paths.get(pathName);
        if(Files.notExists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Create folder "+pathName+" failed");
            }
        }
    }
}
