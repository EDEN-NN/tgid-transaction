package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.exception.CustomerAlreadyExistsException;
import br.com.tgid.tgidtransaction.exception.CustomerNotFoundException;
import br.com.tgid.tgidtransaction.exception.TransactionException;
import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.model.Customer;
import br.com.tgid.tgidtransaction.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EmailService emailService;

    public Customer createCustomer(Customer customer) {

        Optional<Customer> customer1 = customerRepository.findByCpf(customer.getCpf());
        if (customer1.isPresent()) {
            throw new CustomerAlreadyExistsException("This CPF is already in use.");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found!");
        }

        return customer.get();
    }

    public Customer updateCustomer(Customer customer, Long id) {
        Optional<Customer> customer1 = customerRepository.findById(id);

        if (customer1.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found.");
        }

        Customer customerAux = customer1.get();
        customerAux = customer;

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }

        customerRepository.delete(customer.get());

    }

    public void withdraw(Long customerId, double value) {
        Customer customer = this.findById(customerId);
        Company company = companyService.findById(customer.getCompanyId());

        try {
            if (value <= company.getValue()) {
                customer.setAmount(value + customer.getAmount());
                Double transactionValue = transactionService.saveWithdraw(customerId, company.getId(), value);
                company.setValue(company.getValue() - transactionValue);
                System.out.println("Customer :"
                        + customer.getName() +
                        " realized a withdraw. Value: " +
                        transactionValue +
                        " Company: " + company.getName());
                customerRepository.save(customer);
                companyService.updateCompany(company, company.getId());
                emailService.prepareEmail(customer.getName(), company.getName(), transactionValue, "WITHDRAW");
            } else {
                throw new TransactionException("You don't have money enough to do this transaction!");
            }
        } catch(Exception e) {
            e.getMessage();
        }
    }

    public void deposit(Long customerId, double value) {
        Customer customer = this.findById(customerId);
        Company company = companyService.findById(customer.getCompanyId());

            if (value <= customer.getAmount()) {
                customer.setAmount(customer.getAmount() - value);
                Double transactionValue = transactionService.saveDeposit(customerId, company.getId(), value);
                company.setValue(company.getValue() + transactionValue);
                System.out.println("Customer :"
                        + customer.getName() +
                        " realized a deposit. Value: " +
                        transactionValue +
                        " Company: " + company.getName());
                customerRepository.save(customer);
                companyService.updateCompany(company, company.getId());
                emailService.prepareEmail(customer.getName(), company.getName(), transactionValue, "DEPOSIT");
            } else {
                throw new TransactionException("You don't have money enough to do this transaction!");
            }
    }

}
