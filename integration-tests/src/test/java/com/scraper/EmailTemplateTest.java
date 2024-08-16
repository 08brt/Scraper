package com.scraper;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.service.EmailTemplateQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class EmailTemplateTest extends AbstractTests {

    @Autowired
    private EmailTemplateQuery emailTemplateQuery;

    @Test
    void emailTemplateIsNotEmpty() {
        Optional<EmailTemplate> emailTemplate = emailTemplateQuery.getEmailTemplate(TemplateType.HEALTH_CHECK_PROMO);

        Assertions.assertTrue(emailTemplate.isPresent());
        Assertions.assertEquals(TemplateType.HEALTH_CHECK_PROMO, emailTemplate.get().getTemplateType());
    }
}
