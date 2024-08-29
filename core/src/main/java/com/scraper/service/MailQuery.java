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
    private final OpenAIService openAIService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${openai.prompt.body}")
    private String prompt;

    /**
     * Creates a Mail object populated with details from the given ScrapedBusiness and email template.
     * This method uses OpenAI's GPT model to generate an email body, aiming to create content that is
     * less likely to be flagged as spam. It combines a predefined email template with AI-generated text
     * to enhance engagement and personalization.
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

        // Create an AI email body to avoid getting flagged for spam
        String body = openAIService.getChatGptResponse(prompt + "\n\n" + emailTemplate.getTemplate());

        // Personalise body with company name
        body = getPersonalisedBody(scrapedBusiness, body);

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
    public String getPersonalisedBody(ScrapedBusiness scrapedBusiness, String body) {
        return body.replace("{Business Owner}", scrapedBusiness.getName());
    }
}
