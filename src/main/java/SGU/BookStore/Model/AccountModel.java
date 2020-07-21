package SGU.BookStore.Model;

import SGU.BookStore.Entity.Account;
import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Repository.AccountRepo;
import SGU.BookStore.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class AccountModel {
    //tự động inject các bean cần thiết
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    RoleModel roleModel;

    @Autowired
    CartitemModel cartitemModel;
    //đường dẫn thư mục sẽ lưu ảnh được tải về từ CSDL
    //hạn chế truy xuất khi không cần thiết
    private static final String UPLOAD_DIR = "src/main/images/";

    //tìm kiếm tài khoản trong CSDL dựa theo tên đăng nhập
    public Account findByUserName(String s) {
        Optional<Account> account = accountRepo.findByUserName(s);
        return account.isPresent() ? account.get() : null;//trả về null nếu không tìm thấy
    }

    //truy xuất tất cả tài khoản có trong CSDL
    public List<Account> getAllAccount() {
        List<Account> accounts = (List<Account>) accountRepo.findAll();
        return accounts.size() > 0 ? accounts : null;
    }

    //tạo mới hoặc cập nhật tài khoản
    public Account createOrUpdate(Account account) {
        //tạo mới
        //chưa có id -> không tồn tại trong CSDL
        if (account.getId() == null) {
            account = accountRepo.save(account);//khởi tạo

            return account;//trả về đối tượng tương ứng trong CSDL(có id)
        }
        //cập nhật
        //có id
        else {
            Optional<Account> entity = accountRepo.findById(account.getId());
            //có trong database
            if (entity.isPresent()) {
                //newEntity đại diện cho đối tượng tương ứng trong CSDL
                Account newEntity = account.copy();

                newEntity = accountRepo.save(newEntity);

                return newEntity;//trả về đối tượng đã được cập nhật
            } else {
                //không có trong CSDL(được đặt id vì lý do nào đó)
                account = accountRepo.save(account);//khởi tạo

                return account;
            }
        }
    }

    //chịu trách nhiệm tạo ra 2 tài khoản mặc định là admin và user nếu chưa tồn tại
    public void createDefault() {
        //tự động tạo ra 2 quyền mặc định là ADMIN và USER nếu chưa tồn tại
        roleModel.createDefault();
        //tìm kiếm để xem đã tồn tại trong CSDL hay chưa
        Account admin = findByUserName("admin");
        Account user = findByUserName("user");
        //nếu chưa
        if (admin == null) {
            admin = Account.builder()
                    //tạo tài khoản với tên là ADMIN
                    .fullName("ADMIN")
                    //tạo tài khoản với tên đăng nhập là admin
                    .userName("admin")
                    //tạo tài khoản với mật khẩu là admin đã được mã hóa
                    .pwd(new BCryptPasswordEncoder().encode("admin"))//su dung bean
                    //gán quyên là ADMIN
                    .role(roleModel.findByName("ADMIN"))
                    .build();
            createOrUpdate(admin);
        }
        //tương tự như admin
        if (user == null) {
            user = Account.builder()
                    .fullName("USER")
                    .userName("user")
                    .pwd(new BCryptPasswordEncoder().encode("user"))//su dung bean
                    .role(roleModel.findByName("USER"))
                    .build();
            createOrUpdate(user);
        }
    }

    //luu hình ảnh đại diện của các tài khoản vào thư mục cục bộ
    public void saveUserImageToLocalFolder(){
        List<Account> accounts=accountRepo.findAll();
        for(Account account: accounts)
            if (account.getImage()!=null)
                HelperUtils.saveImageFromDatabaseToLocalFolder(account.getImage(), Paths.get(UPLOAD_DIR + "USER_ID=" + account.getId() + ".jpg"));
    }

    //kiếm tra tài khoản đã tồn tại hay chưa dựa trên tên đăng nhập
    public boolean isExist(String username) {
        List<Account> accounts = getAllAccount();
        for (int i = 0; i < accounts.size(); i++)
            if (accounts.get(i).equals(username))
                return true;
        return false;
    }

    //tìm tất cả các tài khoản
    public List<Account> findAll() {
        List<Account> accounts = (List<Account>) accountRepo.findAll();
        return accounts.size()>0?accounts:null;
    }

    //lấy tài khoản thông qua id của tài khoản
    public Account getAccount(Integer Id)
    {
        Optional<Account> account=accountRepo.findById(Id);
        return account.isPresent()?account.get():null;//trả về null nếu không tìm thấy
    }

    //lấy các sản phẩm mà tài khoản có id là id đã mua
    public boolean clientPurchase(int id,List<Cartitem> cartitems)
    {
        for (Cartitem cartitem:cartitems){
            if (id==cartitem.getAccount().getId())
                return true;
        }
        return false;
    }

    //sử dụng để thống kê số lượng khách hàng đã mưa nhiều sản phẩm nhất
    public List<Account> topClients(){
        //lấy tất cả khách hàng
        List<Account> results=accountRepo.findAll();
        List<Account> accounts=new ArrayList<Account>();
        //lặp qua các tài khoản
        for (Account result:results){
            //lưu mảng các khách hàng đã từng mua sản phẩm
            if (clientPurchase(result.getId(),cartitemModel.getAll()))
                accounts.add(result);
        }

        //lặp qua các khách hàng đã từng mua sản phẩm
        for (Account account:accounts){
            List<Cartitem> cartitems=cartitemModel.getCartItemStatisticBy(account.getId());
            int quantity=0;
            //đếm số lượng sản phẩm đã mua
            for (Cartitem cartitem:cartitems){
                quantity+=cartitem.getQuantity();
            }
            account.setQuantityPurchsed(quantity);
        }
        //sắp xếp theo thức tự giảm dần
        //sử dụng thuật toán sắp xếp đổi chỗ trực tiếp
        for (int i=0;i<accounts.size()-1;i++) {
            for (int j=i+1;j<accounts.size();j++) {
                if (accounts.get(i).getQuantityPurchsed()<accounts.get(j).getQuantityPurchsed())
                    Collections.swap(accounts, i, j);
            }
        }
        //trả về mảng các khách hàng đã mua sản phẩm với số lượng sản phẩm đã mua
        return accounts;
    }
}
