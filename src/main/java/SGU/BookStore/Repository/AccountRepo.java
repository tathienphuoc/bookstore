package SGU.BookStore.Repository;

import SGU.BookStore.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//anotation @Repository thông báo với Spring đây là interface có nhiệm vụ thao tác với CSDL
@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserName(String username);
}
