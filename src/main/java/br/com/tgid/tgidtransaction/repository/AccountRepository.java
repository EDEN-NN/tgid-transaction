package br.com.tgid.tgidtransaction.repository;

import br.com.tgid.tgidtransaction.model.Account;
import br.com.tgid.tgidtransaction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM accounts account WHERE account.user_id = ?", nativeQuery = true)
    List<Account> findByUserId(Long id);

}
