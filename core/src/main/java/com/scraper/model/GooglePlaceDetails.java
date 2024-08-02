package com.scraper.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GooglePlaceDetails {

    private final String id;
    private final String name;
    private final String internationalPhoneNumber;
    private final String formattedAddress;
    private final String websiteUri;

}