package SGU.BookStore.Model;

import SGU.BookStore.Entity.Account;
import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Repository.CartItemRepo;
import SGU.BookStore.Utils.HelperUtils;
import SGU.BookStore.Utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//annotation @Service thông báo cho Spring đây là class đảm nhận chức năng thao tác với CSDL(M trong MVC)
@Service
public class CartitemModel {
    //tự động inject các bean cần thiết
    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    AccountModel accountModel;
    //trả về mảng các sản phẩm chưa được thanh toán của tài khoản đang đăng nhập
    public List<Cartitem> getCartItemToCheckOut(){
        Account account=accountModel.findByUserName(SecurityUtils.getUserName());
        List<Cartitem> cartitems=account.getCartitems();
        List<Cartitem> result=new ArrayList<Cartitem>();
        for (Cartitem cartitem: cartitems)
            //trang thái hàng 0 -> chưa được thanh toán
            if (cartitem.getStatus()==0)
                result.add(cartitem);
        return result;
    }

    //trả về sản phẩm được thêm vào giỏ hàng dựa trên id
    public Cartitem getCartItem(Integer Id)
    {
        Optional<Cartitem> cartitem=cartItemRepo.findById(Id);
        return cartitem.isPresent()?cartitem.get():null;//trả về null nếu không tìm thấy
    }

    //tạo mới hoặc cập nhật sản phẩm trong giở hàng
    public Cartitem createOrUpdate(Cartitem cartitem)
    {
        //tạo mới
        //chưa có id -> không tồn tại trong CSDL
        if(cartitem.getId()  == null)
        {
            cartitem = cartItemRepo.save(cartitem);//Khởi tạo
            return cartitem;//trả về đối tượng tương ứng trong CSDL(có id)
        }

        //cập nhật
        //có id
        else
        {
            Cartitem entity = getCartItem(cartitem.getId());
            //Tìm thấy sản phẩm trong giỏ hàng có id là Id
            if(entity!=null)
            {
                return cartItemRepo.save(cartitem);//Cập nhật
            } else {
                //đối tượng không tồn tại trong bảng sản phẩm trong giỏ hàng
                //đã được đặt id vì lý do nào đó
                entity = cartItemRepo.save(entity);//Khởi tạo
                return cartItemRepo.save(entity);//trả về đối tượng vừa được khởi tạo
            }
        }
    }

    //trả về mảng các sản phẩm đã được thanh toán của tài khoản có id là id
    public List<Cartitem> getCartItemPurchasedBy(int id){
        Account accounts=accountModel.getAccount(id);
        List<Cartitem> cartitems=accounts.getCartitems();
        List<Cartitem> result=new ArrayList<Cartitem>();
        for (Cartitem cartitem:cartitems){
            if (cartitem.getStatus()==1)
                result.add(cartitem);
        }
        return result;
    }

    //lấy số lượng sản phẩm đã được thanh toán hoặc giao hàng
    public List<Cartitem> getCartItemStatisticBy(int id){
        //lấy tất cả khách hàng
        Account accounts=accountModel.getAccount(id);
        List<Cartitem> cartitems=accounts.getCartitems();
        List<Cartitem> result=new ArrayList<Cartitem>();
        //lặp qua các giỏ hàng
        for (Cartitem cartitem:cartitems){
            //lưu mảng các giỏ đã được thanh toán hoặc giao hàng
            if (cartitem.getStatus()>0)
                result.add(cartitem);
        }
        return result;
    }

    //lấy mảng các sản phẩm đã được thanh toán hoặc giao hàng của tài khoản đang đăng nhập
    public List<Cartitem> getCartItemPurchased(){
        Account accounts=accountModel.findByUserName(SecurityUtils.getUserName());
        List<Cartitem> cartitems=accounts.getCartitems();
        List<Cartitem> result=new ArrayList<Cartitem>();
        for (Cartitem cartitem:cartitems){
            if (cartitem.getStatus()>0)
                result.add(cartitem);
        }
        return result;
    }

    //gộp hóa đơn
    public void save(Cartitem cartitem) {
        Account account=accountModel.findByUserName(SecurityUtils.getUserName());
        List<Cartitem> cartitems=account.getCartitems();
        for (Cartitem index:cartitems){
            //nếu đã tồn tại sản phẩm do tài khoản đang đăng nhập đã mua và có trạng thái sản phẩm giống nhau
            if (index.getBook().getId()==cartitem.getBook().getId()&&index.getStatus()==cartitem.getStatus()){
                //gộp số lượng
                index.setQuantity(index.getQuantity()+cartitem.getQuantity());
                //cập nhật ngày đặt hàng
                index.setOrderDate(HelperUtils.getTime());
                cartItemRepo.save(index);
                return;
            }
        }
        //nếu chưa tồn tại sẽ tạo mới
        cartitem.setAccount(account);
        //đặt ngày đặt hàng
        cartitem.setOrderDate(HelperUtils.getTime());
        //lưu vào CSDL
        cartItemRepo.save(cartitem);
    }

    //trả về giá tiền của các sản phẩm mà tài khoản hiện đang đăng nhập cần thanh toán
    public int getPurchasePrice() {
        Account account=accountModel.findByUserName(SecurityUtils.getUserName());
        List<Cartitem> cartitems=account.getCartitems();
        int totalPrice=0;
        for (Cartitem index:cartitems){
            if (index.getStatus()==0)
                totalPrice+=index.getQuantity()*index.getBook().getPrice();
        }
        return totalPrice;
    }

    //xóa sản phẩm trong giỏ hàng dựa trên di
    public void deleteById(int id) {
        cartItemRepo.deleteById(id);
    }

    //tổng giá tiền sản phẩm
    public String getSubTotalPrice() {
        return HelperUtils.currencyFormat(getPurchasePrice());
    }

    //chi phí vận chuyển
    public String getShippingTotalPrice() {
        return HelperUtils.currencyFormat((float)(getPurchasePrice()*0.05));
    }

    //tổng số tiền cần thanh toán
    public String getTotalPrice() {
        int purchasePirce=getPurchasePrice();
        return HelperUtils.currencyFormat((float)(getPurchasePrice()+getPurchasePrice()*0.05));
    }

    //thực hiện thanh toán
    public void checkoutCartItem(){
        List<Cartitem> cartitems=getCartItemToCheckOut();
        for (Cartitem cartitem:cartitems){
            //cập nhật trạng thái của các sản phẩm
            cartitem.setStatus((byte) 1);
            //
           // cartitem.setShippingID(shippingID);
            //
            cartItemRepo.save(cartitem);
        }
    }

    //sử dụng để thống kê khách hàng đã mưa nhiều sản phẩm nhất
    public  List<Cartitem> getStatisticPopular(){
        List<Cartitem> result=new ArrayList<Cartitem>();
        //lấy tất cả các sản phẩm trong giở hàng
        List<Cartitem> cartitems=cartItemRepo.findAll();
        //lặp qua các sản phẩm trong giở hàng
        for (Cartitem cartitem:cartitems) {
            //lưu mảng các sản phẩm đã được thanh toán hoặc giao hàng
            if (cartitem.getStatus()>0){
                int index=HelperUtils.checkIfBookisItem(cartitem.getBook().getId(),result);
                if (index==-1) {//chua ton tai
                    //thêm vào mảng các kết quả
                    cartitem.setQuantityPurchsed(cartitem.getQuantity());
                    result.add(cartitem);
                }
                else
                    //đã tồn tại, cập nhật số lượng
                    result.get(index).setQuantityPurchsed(result.get(index).getQuantityPurchsed()+cartitem.getQuantity());
            }
        }
        //sắp xếp theo thức tự giảm dần
        //sử dụng thuật toán sắp xếp đổi chỗ trực tiếp
        for (int i=0;i<result.size()-1;i++) {
            for (int j=i+1;j<result.size();j++) {
                if (result.get(i).getQuantityPurchsed()<result.get(j).getQuantityPurchsed())
                    Collections.swap(result, i, j);
            }
        }
        //trả về mảng các khách hàng cùng với số lượng sản phẩm đã mua
        return result;
    }

    //trả về các sản phẩm trong giỏ hàng
    public List<Cartitem> getAll()
    {
        List<Cartitem> cartitems = (List<Cartitem>) cartItemRepo.findAll();
        return cartitems.size()>0?cartitems:null;
    }

}
