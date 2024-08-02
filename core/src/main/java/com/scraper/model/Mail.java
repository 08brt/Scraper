package com.scraper.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Mail extends AbstractEntity {

    @NotNull
    @Email
    @Column(nullable = false, name = "from_email")
    private String fromEmail;

    @NotNull
    @Column(nullable = false)
    @Size(max = 1000)
    private String toEmails;

    @NotNull
    @Column(nullable = false)
    private String subject;

    @Size(max = 10000)
    @Column(length = 10000)
    private String body;

    @Builder
    public Mail(String fromEmail, String toEmails, String ccEmails, String bccEmails, @NotNull String subject, @Size(max = 10000) String body) {
        this.fromEmail = fromEmail;
        this.toEmails = toEmails;
        this.subject = subject;
        this.body = body;
    }
}
