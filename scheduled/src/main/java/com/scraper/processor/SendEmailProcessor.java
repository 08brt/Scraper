package com.scraper.processor;

import com.scraper.enums.TemplateType;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import com.scraper.service.MailQuery;
import com.scraper.service.MailService;
import com.scraper.service.ScrapedBusinessQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailProcessor {

    private final MailService mailService;
    private final MailQuery mailQuery;
    private final ScrapedBusinessQuery scrapedBusinessQuery;

    @Scheduled(fixedRate = 3600000) // 1 Hour
    public void sendEmailsToScrapedBusiness() {
        processScrapedEmails();
    }

    public void processScrapedEmails() {
        scrapedBusinessQuery
                .getScrapedBusinessByEmailNotNull(20)
                .forEach(this::processEmail);
    }

    /**
     * Creates and sends an email for the given ScrapedBusiness using a specified template type.
     * Logs an informational message if the email cannot be created.
     *
     * @param scrapedBusiness the ScrapedBusiness entity for which to create and send an email
     */
    public void processEmail(ScrapedBusiness scrapedBusiness) {
        Optional<Mail> mail = mailQuery.createMailObjectWithScrapedDetails(scrapedBusiness, TemplateType.HEALTH_CHECK_PROMO);

        if (mail.isEmpty()) {
            log.info("Cannot send email, mail is empty!");
            return;
        }

        mailService.processMail(scrapedBusiness, mail.get());

    }
}
