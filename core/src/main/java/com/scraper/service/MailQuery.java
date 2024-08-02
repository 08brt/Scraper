package com.scraper.service;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.model.Mail;
import com.scraper.model.ScrappedBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailQuery {

    private final EmailTemplateQuery emailTemplateQuery;

    public Optional<Mail> createMailObjectWithScrappedDetails(ScrappedBusiness scrappedBusiness, TemplateType templateType) {
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateQuery.getEmailTemplate(templateType);

        if (optionalEmailTemplate.isEmpty()) {
            return Optional.empty();
        }

        EmailTemplate emailTemplate = optionalEmailTemplate.get();

        // Get personalised body
        String body = getPersonalisedBody(scrappedBusiness, emailTemplate.getTemplate());

        return Optional.of(Mail.builder()
                .fromEmail("bartoszw2000@gmail.com")
                .toEmails(scrappedBusiness.getEmailAddress())
                .subject(emailTemplate.getSubject())
                .body(body)
                .build());
    }

    private String getPersonalisedBody(ScrappedBusiness scrappedBusiness, String body) {
        return body.replace("{Business Owner}", scrappedBusiness.getName());
    }
}
