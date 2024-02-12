package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.dto.AccountDTO;
import br.com.tgid.tgidtransaction.exception.AssignUserException;
import br.com.tgid.tgidtransaction.model.Account;
import br.com.tgid.tgidtransaction.model.User;
import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.repository.AccountRepository;
import br.com.tgid.tgidtransaction.repository.CompanyRepository;
import br.com.tgid.tgidtransaction.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Set<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();
        Set<AccountDTO> accountDTOS = new HashSet<>();
        accounts.forEach(account -> {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account, accountDTO);
            accountDTOS.add(accountDTO);
        });
        return accountDTOS;
    }

    public Set<AccountDTO> findByUserId(Long id) {
        Set<Account> accounts = new HashSet<>(accountRepository.findByUserId(id));
        Set<AccountDTO> accountDTOS = new HashSet<>();
        accounts.forEach(account -> {
            AccountDTO accountDTO = new AccountDTO();
            BeanUtils.copyProperties(account, accountDTO);
            accountDTOS.add(accountDTO);
        });
        return accountDTOS;
    }

    public AccountDTO findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new AssignUserException("Account not found.");
        }
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account.get(), accountDTO);
        return accountDTO;
    }

    public Account findEntityById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new AssignUserException("Account not found.");
        }

        return account.get();
    }

    public AccountDTO updateAccount(AccountDTO accountDTO, Long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isEmpty()) {
            throw new AssignUserException("Account not found.");
        }

        BeanUtils.copyProperties(accountDTO, account.get());
        account.get().setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        accountRepository.save(account.get());
        return accountDTO;
    }

    public AccountDTO assignUserAndCompany(Long userId, Long companyId, Double balance) throws AssignUserException{
        Optional<User> user = userRepository.findById(userId);
        Optional<Company> company = companyRepository.findById(companyId);

        if (user.isEmpty() || company.isEmpty()) {
            throw new AssignUserException("Unable to continue this assignment. Please select a existent account, user and company.");
        }

        Account account = new Account();
        account.setUser(user.get());
        account.setCompany(company.get());
        account.setBalance(balance);
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);

        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);

        return accountDTO;
    }

}
