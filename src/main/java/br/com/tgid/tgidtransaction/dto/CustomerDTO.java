package br.com.tgid.tgidtransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String name;
    private String cpf;
    private Double amount;
    private Long companyId;

}
