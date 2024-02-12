package br.com.tgid.tgidtransaction.dto;


import br.com.tgid.tgidtransaction.model.Company;
import br.com.tgid.tgidtransaction.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {

    private User user;

    private Company company;

    private Double balance;

}
