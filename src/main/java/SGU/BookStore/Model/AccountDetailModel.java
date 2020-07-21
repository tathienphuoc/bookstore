package SGU.BookStore.Model;

import SGU.BookStore.Entity.Account;
import SGU.BookStore.Entity.AccountDetail;
import SGU.BookStore.Repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailModel implements UserDetailsService {
    @Autowired
    AccountRepo accountRepo;
    @Override
    public AccountDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<Account> account = accountRepo.findByUserName(username);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountDetail(account.get());
    }
}
