package com.scraper.mapper;

import com.scraper.enums.StatusType;
import com.scraper.model.GooglePlaceDetails;
import com.scraper.model.ScrapedBusiness;
import org.springframework.stereotype.Component;

@Component
public class ScrapedBusinessMapper {

    public ScrapedBusiness map(GooglePlaceDetails googlePlaceDetails, String keyword, String location) {
        ScrapedBusiness scrapedBusiness = new ScrapedBusiness();
        scrapedBusiness.setPlaceId(googlePlaceDetails.getId());
        scrapedBusiness.setKeyword(keyword);
        scrapedBusiness.setLocation(location);
        scrapedBusiness.setName(googlePlaceDetails.getName());
        scrapedBusiness.setAddress(googlePlaceDetails.getFormattedAddress());
        scrapedBusiness.setPhoneNumber(googlePlaceDetails.getInternationalPhoneNumber());
        scrapedBusiness.setWebsite(googlePlaceDetails.getWebsiteUri());
        scrapedBusiness.setEmailAddress(null);
        scrapedBusiness.setStatusType(StatusType.PENDING);

        return scrapedBusiness;
    }
}
