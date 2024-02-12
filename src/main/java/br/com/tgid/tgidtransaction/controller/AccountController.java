package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.dto.AccountDTO;
import br.com.tgid.tgidtransaction.model.Account;
import br.com.tgid.tgidtransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<Set<AccountDTO>> findAll() {
        return ResponseEntity.ok().body(accountService.findAll());
    }

    @PostMapping("assign-account/{userId}/{companyId}")
    public ResponseEntity<AccountDTO> assignAccount(@PathVariable Long userId, @PathVariable Long companyId, @RequestBody Double balance) {
        return ResponseEntity.accepted().body(accountService.assignUserAndCompany(userId, companyId, balance));
    }

    @GetMapping("account/{userId}")
    public ResponseEntity<Set<AccountDTO>> findAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(accountService.findByUserId(userId));
    }

}
