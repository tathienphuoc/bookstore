package SGU.BookStore.Utils;

import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Model.AccountModel;
import SGU.BookStore.Model.CartitemModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HelperUtils {
    //inject các bean cần thiết
    @Autowired
    private static CartitemModel cartitemModel;
    @Autowired
    private static AccountModel accountModel;

    //trả về thời gian hiện tại có định dạng là yyyy-MM-dd
    public static Date getTime(){
        LocalDateTime localDateTime=LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Date.valueOf(dateTimeFormatter.format(localDateTime));
    }

    //định dạng số tiền
    public static String currencyFormat(float number){
        DecimalFormat decimalFormat = new DecimalFormat( "#,###,###,##0.0" );
        return decimalFormat.format(number);
    }

    //lưu hình vào đường dẫn
    public static void saveImageFromDatabaseToLocalFolder(byte[] image,Path path){
        if (image != null) {
            try {
                Files.write(path, image);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    //xóa hình dựa vào đường dẫn
    public static void deleteImage(Path pathName){
        try {
            if(Files.exists(pathName))
                Files.delete(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //kiểm tra sản phẩm đã được từng mua hay chưa
    public static int checkIfBookisItem(int id,List<Cartitem> cartitems){
        for (Cartitem cartitem:cartitems)
            if (cartitem.getBook().getId()==id)
                return cartitems.indexOf(cartitem);//tra ve vi tri tim thay
        return -1;
    }
}
