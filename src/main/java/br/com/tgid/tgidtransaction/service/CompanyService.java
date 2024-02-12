package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.dto.CompanyDTO;
import br.com.tgid.tgidtransaction.exception.CompanyAlreadyExistsException;
import br.com.tgid.tgidtransaction.exception.CompanyNotFoundException;
import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        Optional<Company> company = companyRepository.findCompanyByCnpj(companyDTO.getCnpj());
        if (company.isPresent()) {
            throw new CompanyAlreadyExistsException("Company already exists!");
        }
        Company newCompany = new Company();
        BeanUtils.copyProperties(companyDTO, newCompany);
        newCompany.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newCompany.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        companyRepository.save(newCompany);
        return companyDTO;
    }

    public List<CompanyDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        companies.forEach(c -> {
            CompanyDTO companyDTO = new CompanyDTO();
            BeanUtils.copyProperties(c, companyDTO);
            companiesDTO.add(companyDTO);
        });
        return companiesDTO;
    }

    public CompanyDTO updateCompany(CompanyDTO companyDTO, Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Unable to find this company to update");
        }

        BeanUtils.copyProperties(companyDTO, company.get());
        company.get().setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        companyRepository.save(company.get());

        return companyDTO;
    }

    public void deleteCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Unable to find this company to delete");
        }

        companyRepository.delete(company.get());
    }

    public CompanyDTO findById(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new CompanyNotFoundException("Unable to find this company");
        }

        CompanyDTO companyDTO = new CompanyDTO();

        BeanUtils.copyProperties(company.get(), companyDTO);

        return companyDTO;
    }

//    public Company assignCustomer(Long customerId, Long companyId) {
//        try {
//            Customer customer = customerService.findById(customerId);
//            Company company = this.findById(companyId);
//            customer.setCompany(company);
//            company.getCustomers().add(customer);
//
//            customerService.updateCustomer(customer, customerId);
//            return companyRepository.save(company);
//        } catch (AssignCustomerException e) {
//            throw new AssignCustomerException("Unable to assign customer to this company.");
//        }
//    }
}
