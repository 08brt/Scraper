package com.scraper;

import com.scraper.data.TestData;
import com.scraper.mapper.CommunicationMapper;
import com.scraper.model.Communication;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommunicationMapperTest extends AbstractTests {

    @Autowired
    private CommunicationMapper communicationMapper;

    private Mail mail;
    private ScrapedBusiness scrapedBusiness;

    @BeforeEach
    void setUp() {
        scrapedBusiness = TestData.createScrapedBusiness();
        mail = TestData.createMail();
    }

    @Test
    void testMap() {
        Communication communication = communicationMapper.map(scrapedBusiness, mail);

        Assertions.assertNotNull(communication);
        Assertions.assertEquals(scrapedBusiness, communication.getScrapedBusiness());
        Assertions.assertEquals(mail, communication.getMail());
        Assertions.assertTrue(communication.isContacted());
    }
}
