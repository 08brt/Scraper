package com.scraper.processor;

import com.scraper.enums.TemplateType;
import com.scraper.model.Mail;
import com.scraper.model.ScrappedBusiness;
import com.scraper.service.MailQuery;
import com.scraper.service.MailService;
import com.scraper.service.ScrappedBusinessQuery;
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
    private final ScrappedBusinessQuery scrappedBusinessQuery;

    @Scheduled(fixedRate = 1000)
    public void sendEmailsToScrappedBusiness() {
        processScrapedEmails();
    }

    public void processScrapedEmails() {
        scrappedBusinessQuery
                .getScrappedBusinessByEmailNotNull(20)
                .forEach(this::processEmail);
    }

    public void processEmail(ScrappedBusiness scrappedBusiness) {
        Optional<Mail> mail = mailQuery.createMailObjectWithScrappedDetails(scrappedBusiness, TemplateType.HEALTH_CHECK_PROMO);

        if (mail.isEmpty()) {
            log.info("Cannot send email, mail is empty!");
            return;
        }

        mailService.processMail(scrappedBusiness, mail.get());

    }
}
