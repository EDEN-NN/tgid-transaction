package br.com.tgid.tgidtransaction.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private String ownerRef;

    private String emailFrom;

    private String emailTo;

    private String subject;

    private String text;

    private LocalDateTime sendDateEmail;

    private String statusEmail;

}
