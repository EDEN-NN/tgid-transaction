package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.dto.TransactionDTO;
import br.com.tgid.tgidtransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("transactions/{accountId}/withdraw")
    public ResponseEntity<TransactionDTO> doWithdraw(@PathVariable Long accountId, @RequestBody Double value) {
        return ResponseEntity.accepted().body(transactionService.saveWithdraw(accountId, value));
    }

    @PostMapping("transactions/{accountId}/deposit")
    public ResponseEntity<TransactionDTO> doDeposit(@PathVariable Long accountId, @RequestBody Double value) {
        return ResponseEntity.accepted().body(transactionService.saveDeposit(accountId, value));
    }

}
