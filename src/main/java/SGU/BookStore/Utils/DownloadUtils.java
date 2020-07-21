package SGU.BookStore.Utils;

import SGU.BookStore.Entity.Account;
import SGU.BookStore.Model.AccountModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DownloadUtils {
    private static final String UPLOAD_DIR = "src/main/images/";

    //tải hình ảnh từ CSDL lưu vào thư mục cục bộ
    public static void dowloadImagesFromDatabaseToLocalFolder() {
        AccountModel accountModel=new AccountModel();
        List<Account> accounts =accountModel.findAll();
        for (Account account : accounts) {
            Path path = Paths.get(UPLOAD_DIR + "USER_ID=" + account.getId() + ".jpg");
            byte[] images = account.getImage();
            if (images != null) {
                try {
                    Files.write(path, account.getImage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
