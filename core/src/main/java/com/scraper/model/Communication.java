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
    @JoinColumn(name = "scrapped_store_id", nullable = false)
    private ScrappedBusiness scrappedBusiness;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mail_id", nullable = false)
    private Mail mail;

    @NotNull
    @Column(nullable = false)
    private boolean contacted;

    @Builder
    public Communication(ScrappedBusiness scrappedBusiness, Mail mail, boolean contacted, String errorMessage) {
        this.scrappedBusiness = scrappedBusiness;
        this.mail = mail;
        this.contacted = contacted;
    }
}
