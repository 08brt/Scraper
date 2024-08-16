package com.scraper;

import com.scraper.data.TestData;
import com.scraper.model.Communication;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.CommunicationRepository;
import com.scraper.repository.MailRepository;
import com.scraper.repository.ScrapedBusinessRepository;
import com.scraper.service.CommunicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class CommunicationServiceTest extends AbstractTests {

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private ScrapedBusinessRepository scrapedBusinessRepository;

    @Autowired
    private MailRepository mailRepository;

    private Mail mail;
    private ScrapedBusiness scrapedBusiness;

    @BeforeEach
    void setUp() {
        scrapedBusiness = scrapedBusinessRepository.save(TestData.createScrapedBusiness());
        mail = mailRepository.save(TestData.createMail());
    }

    @AfterEach
    void cleanUp() {
        communicationRepository.deleteAll();
        scrapedBusinessRepository.deleteAll();
        mailRepository.deleteAll();

    }

    @Test
    void testSaveCommunication() {
        communicationService.saveCommunication(scrapedBusiness, mail);

        List<Communication> communications = communicationRepository.findAll();

        Assertions.assertNotNull(communications);
        Assertions.assertEquals(scrapedBusiness.getId(), communications.get(0).getScrapedBusiness().getId());
        Assertions.assertEquals(mail.getId(), communications.get(0).getMail().getId());
        Assertions.assertTrue(communications.get(0).isContacted());
    }
}
