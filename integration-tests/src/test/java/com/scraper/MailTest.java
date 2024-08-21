package com.scraper;

import com.scraper.data.TestData;
import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import com.scraper.service.EmailTemplateQuery;
import com.scraper.service.MailQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

class MailTest extends AbstractTests {

    private ScrapedBusiness scrapedBusiness;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private MailQuery mailQuery;

    @Autowired
    private EmailTemplateQuery emailTemplateQuery;

    @BeforeEach
    void setUp() {
        scrapedBusiness = TestData.createScrapedBusiness();
    }

    @Test
    void createMailObject() {
        Optional<Mail> mailOptional = mailQuery.createMailObjectWithScrapedDetails(scrapedBusiness, TemplateType.HEALTH_CHECK_PROMO);

        Assertions.assertTrue(mailOptional.isPresent(), "Mail object should be present");

        Mail mail = mailOptional.get();
        Assertions.assertEquals(fromEmail, mail.getFromEmail(), "The 'from' email does not match the expected value.");
        Assertions.assertEquals(scrapedBusiness.getEmailAddress(), mail.getToEmails(), "The 'to' email does not match the scraped business email.");

        Optional<String> subjectOptional = getSubject();
        Assertions.assertTrue(subjectOptional.isPresent(), "Email subject should be present");
        subjectOptional.ifPresent(subject -> {
            Assertions.assertEquals(subject, mail.getSubject(), "The email subject does not match the expected value.");
        });

        Optional<String> bodyOptional = getBody();
        Assertions.assertTrue(bodyOptional.isPresent(), "Email body should be present");
        bodyOptional.ifPresent(body -> {
            Assertions.assertEquals(body, mail.getBody(), "The email body does not match the expected personalized content.");
        });

    }

    private Optional<String> getSubject() {
        return emailTemplateQuery.getEmailTemplate(TemplateType.HEALTH_CHECK_PROMO)
                .map(EmailTemplate::getSubject);
    }

    private Optional<String> getBody() {
        return emailTemplateQuery.getEmailTemplate(TemplateType.HEALTH_CHECK_PROMO)
                .map(emailTemplate -> mailQuery.getPersonalisedBody(scrapedBusiness, emailTemplate.getTemplate()));
    }
}
