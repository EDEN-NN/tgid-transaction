package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.dto.CompanyDTO;
import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company);;
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyService.createCompany(company).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long id) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company);
        return ResponseEntity.accepted().body(companyService.updateCompany(company, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

}
