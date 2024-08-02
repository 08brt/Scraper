package com.scraper.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "communication")
@Getter
@Setter
@NoArgsConstructor
public class Communication extends AbstractEntity {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "scraped_store_id", nullable = false)
    private ScrapedBusiness scrapedBusiness;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mail_id", nullable = false)
    private Mail mail;

    @NotNull
    @Column(nullable = false)
    private boolean contacted;

    @Builder
    public Communication(ScrapedBusiness scrapedBusiness, Mail mail, boolean contacted, String errorMessage) {
        this.scrapedBusiness = scrapedBusiness;
        this.mail = mail;
        this.contacted = contacted;
    }
}
