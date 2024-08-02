package com.scraper.repository;

import com.scraper.enums.TemplateType;
import com.scraper.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>, JpaSpecificationExecutor<EmailTemplate> {

    Optional<EmailTemplate> findFirstByTemplateType(TemplateType templateType);
}
