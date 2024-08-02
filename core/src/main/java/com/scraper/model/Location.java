package com.scraper.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Location extends AbstractEntity {

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String city;

    @NotNull
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @Column(nullable = false)
    private Double longitude;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String country;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String iso2;

    @NotNull
    @Column(nullable = false)
    private boolean processed;
}
