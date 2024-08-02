package com.scraper.service;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import com.scraper.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTemplateQuery {

    private final EmailTemplateRepository emailTemplateRepository;

    public Optional<EmailTemplate> getEmailTemplate(TemplateType templateType) {
        return emailTemplateRepository.findFirstByTemplateType(templateType);
    }
}
