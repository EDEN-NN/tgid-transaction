package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.exception.CompanyException;
import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {

        Optional<Company> company1 = companyRepository.findCompanyByCnpj(company.getCnpj());
        if (company1.isPresent()) {
            throw new CompanyException("Company already exists!");
        }
        return companyRepository.save(company);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company updateCompany(Company company, Long id) {
        Optional<Company> company1 = companyRepository.findById(id);

        if (company1.isEmpty()) {
            throw new CompanyException("This user doesn't exist!");
        }

        Company companyAux = company1.get();
        companyAux = company;

        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new CompanyException("This company doesn't exist!");
        }

        companyRepository.delete(company.get());
    }

    public Company findById(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new CompanyException("This company doesn't exist!");
        }

        return company.get();
    }

}
