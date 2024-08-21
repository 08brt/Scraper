package com.scraper;

import com.scraper.data.TestData;
import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.CommunicationRepository;
import com.scraper.repository.MailRepository;
import com.scraper.repository.ScrapedBusinessRepository;
import com.scraper.service.EmailTemplateQuery;
import com.scraper.service.MailQuery;
import com.scraper.service.MailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MailTest extends AbstractTests {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private Mail mail;
    private ScrapedBusiness scrapedBusiness;

    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    private CommunicationRepository communicationRepository;
    @Autowired
    private EmailTemplateQuery emailTemplateQuery;
    @Autowired
    private MailQuery mailQuery;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private ScrapedBusinessRepository scrapedBusinessRepository;

    @BeforeEach
    void setUp() {
        scrapedBusiness = scrapedBusinessRepository.save(TestData.createScrapedBusiness());
        mail = mailRepository.save(TestData.createMail());
    }

    @AfterEach
    void cleanUp() {
        communicationRepository.deleteAll();
        mailRepository.deleteAll();
        scrapedBusinessRepository.deleteAll();
    }

    @Test
    void sendEmail() {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        mailService.processMail(scrapedBusiness, mail);

        Assertions.assertTrue(mailRepository.findById(mail.getId()).isPresent(), "Mail should be saved in the database");

        Mail savedMail = mailRepository.findById(mail.getId()).get();
        Assertions.assertEquals(mail.getFromEmail(), savedMail.getFromEmail());
        Assertions.assertEquals(mail.getToEmails(), savedMail.getToEmails());
        Assertions.assertEquals(mail.getSubject(), savedMail.getSubject());
        Assertions.assertEquals(mail.getBody(), savedMail.getBody());

        verify(mailSender, times(1)).send(mimeMessage);
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
