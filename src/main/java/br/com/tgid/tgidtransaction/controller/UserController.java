package br.com.tgid.tgidtransaction.controller;

import br.com.tgid.tgidtransaction.dto.UserDTO;
import br.com.tgid.tgidtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.created(URI.create("/users")).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return ResponseEntity.accepted().body(userService.updateUser(userDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("withdraw/{id}")
//    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestBody Double value) {
//        customerService.withdraw(id, value);
//        return ResponseEntity.accepted().build();
//    }
//
//    @PostMapping("deposit/{id}")
//    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody Double value) {
//        customerService.deposit(id, value);
//        return ResponseEntity.accepted().build();
//    }

}
