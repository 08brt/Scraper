package com.scraper.service;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailQuery {

    private final EmailTemplateQuery emailTemplateQuery;

    public Optional<Mail> createMailObjectWithScrapedDetails(ScrapedBusiness scrapedBusiness, TemplateType templateType) {
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateQuery.getEmailTemplate(templateType);

        if (optionalEmailTemplate.isEmpty()) {
            return Optional.empty();
        }

        EmailTemplate emailTemplate = optionalEmailTemplate.get();

        // Get personalised body
        String body = getPersonalisedBody(scrapedBusiness, emailTemplate.getTemplate());

        return Optional.of(Mail.builder()
                .fromEmail("bartoszw2000@gmail.com")
                .toEmails(scrapedBusiness.getEmailAddress())
                .subject(emailTemplate.getSubject())
                .body(body)
                .build());
    }

    private String getPersonalisedBody(ScrapedBusiness scrapedBusiness, String body) {
        return body.replace("{Business Owner}", scrapedBusiness.getName());
    }
}
