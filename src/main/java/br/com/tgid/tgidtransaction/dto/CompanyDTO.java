package br.com.tgid.tgidtransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO implements Serializable {

    private String name;
    private String cnpj;

}
