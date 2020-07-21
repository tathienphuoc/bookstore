package SGU.BookStore.Repository;

import SGU.BookStore.Entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepo extends JpaRepository<Shipping,Integer> {
}

