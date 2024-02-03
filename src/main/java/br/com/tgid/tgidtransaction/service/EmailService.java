package br.com.tgid.tgidtransaction.service;

import br.com.tgid.tgidtransaction.model.Email;
import br.com.tgid.tgidtransaction.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender mailSender;

    private Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            mailSender.send(message);

            email.setStatusEmail("SENT");
        } catch (MailException e){
            email.setStatusEmail("ERROR");
        } finally {
            return emailRepository.save(email);
        }
    }

    public void prepareEmail(String customerName, String companyName, Double transactionValue, String type) {
        Email email = new Email();
        email.setEmailFrom("9c9bd215-2520-451f-9cd5-4b1ffa99c2ab@email.webhook.site");
        email.setEmailTo("9c9bd215-2520-451f-9cd5-4b1ffa99c2ab@email.webhook.site");
        email.setSubject("Success on transaction!");
        email.setText(type + " TRANSACTION REALIZED BY " + customerName + " ON THE COMPANY " + companyName + ". VALUE: " + transactionValue);
        this.sendEmail(email);

    }
}
