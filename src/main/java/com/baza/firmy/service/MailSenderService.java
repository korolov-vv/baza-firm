package com.baza.firmy.service;

import com.baza.firmy.entity.FileEntity;
import com.baza.firmy.repository.FileRepository;
import com.baza.firmy.util.FileUtills;
import com.baza.firmy.util.MailSenderUtills;
import jakarta.mail.MessagingException;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

  private final MailSenderUtills mailSenderUtills;
  private final FileRepository fileRepository;
  private final FileUtills fileUtills;

  public void sendEmailWithFirms(String to, String subject, String body) {

    FileEntity fileEntity = fileRepository.findFirstByOrderByIdDesc()
        .orElseThrow(() -> new RuntimeException("File not found found"));

    File attachment = fileUtills.getFile(fileEntity.getPath(), fileEntity.getFileName());

    try {
      mailSenderUtills.sendMessageWithAttachment(to, subject, body, attachment);
    } catch (MessagingException e) {
      log.info("Failed to send an email to: {}, cause: {}", to, e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
