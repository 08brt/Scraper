package com.scraper.service;

import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailRepository mailRepository;
    private final CommunicationService communicationService;
    private final ScrapedBusinessService scrapedBusinessService;

    @Transactional
    public void processMail(ScrapedBusiness scrapedBusiness, Mail mail) {

        try {
            log.info("Preparing to send email");

            // Save mail
            log.info("Saving Mail");
            mail = saveMail(mail);

            // Save communication
            log.info("Saving communication");
            communicationService.saveCommunication(scrapedBusiness, mail);

            // Send Mail
            log.info("Sending email");
            mailSender.send(setSimpleMailMessage(mail));

            // Mark as emailed
            scrapedBusinessService.setAsEmailed(scrapedBusiness);
        } catch (Exception e) {
            log.error("Error occurred while emailing ScrapedBusinessId {}", scrapedBusiness.getId(), e);
            scrapedBusinessService.setErrorMessageAndMarkError(scrapedBusiness, e.getMessage());
        }

    }

    private MimeMessage setSimpleMailMessage(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(mail.getFromEmail());
        helper.setTo(mail.getToEmails());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);

        return message;
    }

    private Mail saveMail(Mail mail) {
        return mailRepository.save(mail);
    }

}
