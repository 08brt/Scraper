package com.scraper.model;

import com.scraper.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "scrapped_business")
@Getter
@Setter
public class ScrappedBusiness extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String placeId;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String keyword;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String location;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String address;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 2048)
    private String website;

    @Size(max = 1000)
    private String emailAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private StatusType statusType;

    @Size(max = 10000)
    @Column(length = 10000)
    private String errorMessage;
}
