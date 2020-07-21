package SGU.BookStore;

import SGU.BookStore.Utils.CreateUtils;
import SGU.BookStore.Utils.HelperUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class BookStoreApplication {
	public static void main(String[] args){
		SpringApplication.run(BookStoreApplication.class, args);
		CreateUtils.createDirImages();
	}

}
