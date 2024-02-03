package br.com.tgid.tgidtransaction.repository;

import br.com.tgid.tgidtransaction.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}
