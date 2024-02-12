package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.repository.CompanyRepository;
import br.com.tgid.tgidtransaction.repository.UserRepository;
import br.com.tgid.tgidtransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;


}
