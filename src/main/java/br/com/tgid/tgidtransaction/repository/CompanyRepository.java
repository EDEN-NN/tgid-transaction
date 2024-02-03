package br.com.tgid.tgidtransaction.repository;

import br.com.tgid.tgidtransaction.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByCnpj(String cnpj);

}
