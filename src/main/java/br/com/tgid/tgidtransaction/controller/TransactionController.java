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

//    @PostMapping("customer/{id}/withdraw")
//    public ResponseEntity<TransactionDTO> doWithdraw(@PathVariable Long customerId, @RequestBody Double value) {
//        return ResponseEntity.accepted().body(transactionService.saveWithdraw(customerId, value));
//    }

}
