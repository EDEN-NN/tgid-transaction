package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.dto.AccountDTO;
import br.com.tgid.tgidtransaction.dto.TransactionDTO;
import br.com.tgid.tgidtransaction.exception.TransactionException;
import br.com.tgid.tgidtransaction.model.Account;
import br.com.tgid.tgidtransaction.model.Transaction;
import br.com.tgid.tgidtransaction.model.TransactionType;
import br.com.tgid.tgidtransaction.repository.AccountRepository;
import br.com.tgid.tgidtransaction.repository.CompanyRepository;
import br.com.tgid.tgidtransaction.repository.UserRepository;
import br.com.tgid.tgidtransaction.repository.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    public TransactionDTO saveWithdraw(Long accountId, Double value) {
        Account account = accountService.findEntityById(accountId);
        AccountDTO accountDTO = accountService.findById(accountId);
        Transaction transaction = new Transaction();
        TransactionDTO transactionDTO = new TransactionDTO();

        if (value > account.getBalance()) {
            throw new TransactionException("You cannot withdraw this value.");
        }
        transaction.setTransactionValue(value - (value * 0.02));
        transaction.setAccount(account);
        transaction.setType(TransactionType.WITHDRAW);

        accountDTO.setBalance(accountDTO.getBalance() - value);


        BeanUtils.copyProperties(accountDTO, account);
        BeanUtils.copyProperties(transaction, transactionDTO);

        accountService.updateAccount(accountDTO, accountId);
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        this.prepareTransactionEmail(account, transaction);
        return transactionDTO;
    }

    public TransactionDTO saveDeposit(Long accountId, Double value) {
        Account account = accountService.findEntityById(accountId);
        AccountDTO accountDTO = accountService.findById(accountId);
        Transaction transaction = new Transaction();
        TransactionDTO transactionDTO = new TransactionDTO();

        transaction.setTransactionValue(value - (value * 0.02));
        transaction.setAccount(account);
        transaction.setType(TransactionType.DEPOSIT);

        accountDTO.setBalance(accountDTO.getBalance() + value - (value * 0.02));


        BeanUtils.copyProperties(accountDTO, account);
        BeanUtils.copyProperties(transaction, transactionDTO);
        transactionDTO.setAccount(account);

        accountService.updateAccount(accountDTO, accountId);
        transactionRepository.save(transaction);
        this.prepareTransactionEmail(account, transaction);
        return transactionDTO;
    }

    public void prepareTransactionEmail(Account account, Transaction transaction) {
        this.emailService.prepareEmail(account.getUser().getName(), account.getCompany().getName(), transaction.getTransactionValue(), transaction.getType().toString());
    }

}
