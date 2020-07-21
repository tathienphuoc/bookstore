package SGU.BookStore.Repository;

import SGU.BookStore.Entity.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<Cartitem,Integer> {

}
