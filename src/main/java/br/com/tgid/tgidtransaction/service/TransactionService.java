package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.model.Transaction;
import br.com.tgid.tgidtransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Double saveWithdraw(Long customerId, Long companyId, double transactionValue) {
        Transaction transaction = this.doTransaction(customerId, companyId, transactionValue, "WITHDRAW");
        return transaction.getTransactionValue();
    }

    public Double saveDeposit(Long customerId, Long companyId, double transactionValue) {
        Transaction transaction = this.doTransaction(customerId, companyId, transactionValue, "DEPOSIT");
        return transaction.getTransactionValue();
    }

    private Transaction doTransaction(Long customerId, Long companyId, double transactionValue, String type) {
        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setCompanyId(companyId);
        transaction.setType(type);
        transaction.setTransactionValue(transactionValue - (transactionValue * 0.02));

        transactionRepository.save(transaction);
        return transaction;
    }

}
