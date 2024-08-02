package com.scraper.model;

import com.scraper.enums.TemplateType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "email_template")
@Getter
@Setter
public class EmailTemplate extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private TemplateType templateType;

    @NotNull
    @Column(nullable = false, length = 100)
    private String subject;

    @NotBlank
    @Size(max = 50000)
    @Column(nullable = false, length = 50000)
    private String template;
}
