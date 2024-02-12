package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.dto.AccountDTO;
import br.com.tgid.tgidtransaction.dto.TransactionDTO;
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

    public TransactionDTO saveWithdraw(Long accountId, Double value) {
        Account account = accountService.findEntityById(accountId);
        AccountDTO accountDTO = accountService.findById(accountId);
        Transaction transaction = new Transaction();
        TransactionDTO transactionDTO = new TransactionDTO();

        transaction.setTransactionValue(value - (value * 0.02));
        transaction.setAccount(account);
        transaction.setType(TransactionType.WITHDRAW);

        accountDTO.setBalance(accountDTO.getBalance() - value);


        BeanUtils.copyProperties(accountDTO, account);
        BeanUtils.copyProperties(transaction, transactionDTO);

        accountService.updateAccount(accountDTO, accountId);
        transactionRepository.save(transaction);
        return transactionDTO;
    }

}
