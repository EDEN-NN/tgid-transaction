package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping("create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return ResponseEntity.created(URI.create("/companies")).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable Long id) {
        return ResponseEntity.accepted().body(companyService.updateCompany(company, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

}
