package SGU.BookStore.Controller;

import SGU.BookStore.Entity.Cartitem;
import SGU.BookStore.Entity.Shipping;
import SGU.BookStore.Model.BookModel;
import SGU.BookStore.Model.CartitemModel;
import SGU.BookStore.Model.ShippingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
//annotation @Controller thông báo cho Spring đây là class đảm nhận chức năng điều khiển(C trong MVC)
@Controller
public class CartController {
    //tự động inject các bean cần thiết
    @Autowired
    ShippingModel shippingModel;
    @Autowired
    CartitemModel cartitemModel;
    @Autowired
    BookModel bookModel;
    //khi có yêu cầu xem các sản phẩm đã đặt hành mà chưa thanh toán
    //đóng vai trò như gio hảng trong các siêu thị
    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model){
        //các sản phẩm mà tài khoản này đã thêm vào giỏ hàng
        model.addAttribute("items",cartitemModel.getCartItemToCheckOut());
        //giá của các sản phẩm
        model.addAttribute("subTotalPrice",cartitemModel.getSubTotalPrice());
        //chi phí vận chuyển
        model.addAttribute("shippingPrice",cartitemModel.getShippingTotalPrice());
        //tổng giá tiền phải thanh toán
        model.addAttribute("totalPrice",cartitemModel.getTotalPrice());
        //tạo vào một đối tượng shipping đóng vai trò như hóa đơn
        //để lưu trữ các thông tin để thanh toán và giao hàng
        model.addAttribute("shipping",new Shipping());
        return "shoppingCart";
    }

    //thanh toán
    @RequestMapping("/checkout")
    public String checkout(@ModelAttribute("shipping") Shipping shipping){
        //lưu hóa đơn vào CSDL
        shippingModel.save(shipping);
        //hiện thị trang cảm ơn
        return "thanks";
    }

    //khi có yêu cầu thêm vào giỏ  hàng
    @RequestMapping(value = {"/addToCart/{bookID}"})
    public String search(Model model, @ModelAttribute("cartitem") Cartitem cartitem, @PathVariable("bookID") Optional<Integer> bookID){
        //chỉ lưu vào CSDL nếu số lượng <0
        if (cartitem.getQuantity()>0){
            cartitem.setBook(bookModel.getBook(bookID.get()));
            cartitemModel.save(cartitem);
        }
        //chuyển đến trang tiếp tục mua sắm
        return "redirect:/books";
    }

    //khi có yêu cầu xem lịch sử giao dịch
    @RequestMapping("/transaction")
    public String transaction(Model model){
        //mảng các sản phẩm đã thanh toán hoặc đã giao hàng sẽ được gán vào biến items
        model.addAttribute("items",cartitemModel.getCartItemPurchased());
        //hiển thị trang lịch sử giao dịch
        return "transaction";
    }

    //khi muốn xóa 1 sản phẩm trong giỏ hàng
    @RequestMapping("/cartitem/delete/{id}")
    public String deleteCartItem(@PathVariable("id") Optional<Integer> id){
        //xóa sản phẩm dựa trên id có được từ URL
        cartitemModel.deleteById(id.get());
        return "redirect:/shoppingCart";
    }
}
