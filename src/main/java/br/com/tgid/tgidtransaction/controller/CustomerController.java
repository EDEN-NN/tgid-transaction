package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.model.Customer;
import br.com.tgid.tgidtransaction.service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping("create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return ResponseEntity.created(URI.create("/customers")).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        return ResponseEntity.accepted().body(customerService.updateCustomer(customer, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("withdraw/{id}")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody Double value) {
        customerService.withdraw(id, value);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("deposit/{id}")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody Double value) {
        customerService.deposit(id, value);
        return ResponseEntity.accepted().build();
    }

}
