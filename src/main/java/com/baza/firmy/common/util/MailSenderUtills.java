package com.baza.firmy.common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderUtills {

   private final JavaMailSender mailSender;

   public void sendEmail(String to, String subject, String body) {
     log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);
     SimpleMailMessage message = new SimpleMailMessage();
     message.setTo(to);
     message.setBcc("vadymkorolov@gmail.com");
     message.setSubject(subject);
     message.setText(body);

     mailSender.send(message);
     log.info("Email sent successfully to: {}, subject: {}", to, subject);
   }

  public void sendMessageWithAttachment(
      String to, String subject, String body, File attachment) throws MessagingException {
    log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);

    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom("bazafirm@gmail.com");
    helper.setTo(to);
    helper.setBcc("vadymkorolov@gmail.com");
    helper.setSubject(subject);
    helper.setText(body);

    FileSystemResource file
        = new FileSystemResource(attachment);
    helper.addAttachment(file.getFilename() != null ? file.getFilename() : "Lista_firm.xlsx", file);

    mailSender.send(message);
    log.info("The email to: {}, subject: {}, body: {} was sent", to, subject, body);

  }
}
