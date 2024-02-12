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
    public ResponseEntity<List<CompanyDTO>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping("create")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(companyService.createCompany(companyDTO)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long id) {
        return ResponseEntity.accepted().body(companyService.updateCompany(companyDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("{id}/assign")
//    public ResponseEntity<Company> assignCustomer(@PathVariable Long companyId, @RequestBody Long customerId) {
//        return ResponseEntity.accepted().body(companyService.assignCustomer(customerId, companyId));
//    }

}
