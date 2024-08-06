package com.scraper.service;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailQuery {

    private final EmailTemplateQuery emailTemplateQuery;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Creates a Mail object populated with details from the given ScrapedBusiness and email template.
     *
     * @param scrapedBusiness the business details obtained from scraping
     * @param templateType the type of template to use for the email
     * @return an Optional containing the constructed Mail object, or an empty Optional if no template is found
     */
    public Optional<Mail> createMailObjectWithScrapedDetails(ScrapedBusiness scrapedBusiness, TemplateType templateType) {
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateQuery.getEmailTemplate(templateType);

        if (optionalEmailTemplate.isEmpty()) {
            return Optional.empty();
        }

        EmailTemplate emailTemplate = optionalEmailTemplate.get();

        // Get personalised body
        String body = getPersonalisedBody(scrapedBusiness, emailTemplate.getTemplate());

        return Optional.of(Mail.builder()
                .fromEmail(fromEmail)
                .toEmails(scrapedBusiness.getEmailAddress())
                .subject(emailTemplate.getSubject())
                .body(body)
                .build());
    }

    /**
     * Generates a personalized email body by replacing placeholders with details from the ScrapedBusiness.
     *
     * @param scrapedBusiness the business details obtained from scraping
     * @param body the email template body containing placeholders
     * @return a personalized email body with placeholders replaced by business-specific details
     */
    private String getPersonalisedBody(ScrapedBusiness scrapedBusiness, String body) {
        return body.replace("{Business Owner}", scrapedBusiness.getName());
    }
}
