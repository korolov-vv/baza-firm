package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.service.MailSenderService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class WyslijListeJdgNoweService implements BazowySchedulerService {

 private final MailSenderService mailSenderService;

 @Override
 public void executeScheduler(JobExecutionContext jobExecutionContext) {
  mailSenderService.sendMessageWithFirms(
      "k.grabowski@schrack.pl",
      "Lista JDG",
      String.format("Cześć! W załączniku firmy z %s", LocalDate.now().minusDays(3)));
 }
}
