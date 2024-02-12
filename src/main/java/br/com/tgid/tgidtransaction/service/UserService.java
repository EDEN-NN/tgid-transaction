package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.dto.UserDTO;
import br.com.tgid.tgidtransaction.exception.UserAlreadyExistsException;
import br.com.tgid.tgidtransaction.exception.UserNotFoundException;
import br.com.tgid.tgidtransaction.model.User;
import br.com.tgid.tgidtransaction.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CompanyService companyService;

    @Autowired
    public TransactionService transactionService;

    @Autowired
    public EmailService emailService;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Optional<User> existentUser = userRepository.findByCpf(user.getCpf());
        if (existentUser.isPresent()) {
            throw new UserAlreadyExistsException("This CPF is already in use.");
        }
        userRepository.save(user);
        return userDTO;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(c -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(c, userDTO);
            userDTOS.add(userDTO);
        });
        return userDTOS;
    }

    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }
        BeanUtils.copyProperties(userDTO, user.get());
        userRepository.save(user.get());

        return userDTO;
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(user.get());
    }

}
