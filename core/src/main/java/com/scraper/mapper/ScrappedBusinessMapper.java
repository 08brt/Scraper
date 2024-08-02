package com.scraper.mapper;

import com.scraper.enums.StatusType;
import com.scraper.model.GooglePlaceDetails;
import com.scraper.model.ScrappedBusiness;
import org.springframework.stereotype.Component;

@Component
public class ScrappedBusinessMapper {

    public ScrappedBusiness map(GooglePlaceDetails googlePlaceDetails, String keyword, String location) {
        ScrappedBusiness scrappedBusiness = new ScrappedBusiness();
        scrappedBusiness.setPlaceId(googlePlaceDetails.getId());
        scrappedBusiness.setKeyword(keyword);
        scrappedBusiness.setLocation(location);
        scrappedBusiness.setName(googlePlaceDetails.getName());
        scrappedBusiness.setAddress(googlePlaceDetails.getFormattedAddress());
        scrappedBusiness.setPhoneNumber(googlePlaceDetails.getInternationalPhoneNumber());
        scrappedBusiness.setWebsite(googlePlaceDetails.getWebsiteUri());
        scrappedBusiness.setEmailAddress(null);
        scrappedBusiness.setStatusType(StatusType.PENDING);

        return scrappedBusiness;
    }
}
