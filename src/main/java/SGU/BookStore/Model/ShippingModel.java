package SGU.BookStore.Model;

import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Entity.Shipping;
import SGU.BookStore.Repository.ShippingRepo;
import SGU.BookStore.Utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingModel {
    @Autowired
    ShippingRepo shippingRepo;
    @Autowired
    AccountModel accountModel;
    @Autowired
    CartitemModel cartitemModel;
    public void save(Shipping shipping){
        shipping.setAccount(accountModel.findByUserName(SecurityUtils.getUserName()));
        cartitemModel.checkoutCartItem();
        shippingRepo.save(shipping);
    }
    //lay hoa don chua giao hang
    public List<Shipping> ship(){
        List<Shipping> shippings=shippingRepo.findAll();
        List<Shipping> result=new ArrayList<Shipping>();
        for (Shipping s:shippings){
            if (s.getStatus()==0)
                result.add(s);
        }
        //shipping da co mang cac sp da thanh toan
        return result;
    }
    //sach + hoa don chua giao
    public List<Shipping> getShipping(){
        List<Shipping> shippings=ship();
        for (Shipping s:shippings){
            //mang cac san pham da thanh toan
            List<Cartitem> cartitems=cartitemModel.getCartItemPurchasedBy(s.getAccount().getId());
            //dua vao shipping
            s.setCartitems(cartitems);
        }
        //shipping da co mang cac sp da thanh toan
        return shippings;
    }
    public Shipping getShipping(Integer Id)
    {
        Optional<Shipping> shipping=shippingRepo.findById(Id);
        return shipping.isPresent()?shipping.get():null;//trả về null nếu không tìm thấy
    }
    public void shipping(int id){
        Shipping shipping=getShipping(id);
        shipping.setStatus((byte)1);
        List<Cartitem> cartitems=cartitemModel.getCartItemPurchasedBy(getShipping(id).getAccount().getId());
        for(Cartitem cartitem:cartitems){
            cartitem.setStatus((byte) 2);
            cartitemModel.createOrUpdate(cartitem);
        }
        shippingRepo.save(shipping);
    }
}
