package com.scraper.data;

import com.scraper.enums.StatusType;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;

public class TestData {

    public static ScrapedBusiness createScrapedBusiness() {
        ScrapedBusiness scrapedBusiness = new ScrapedBusiness();
        scrapedBusiness.setPlaceId("place123");
        scrapedBusiness.setKeyword("restaurant");
        scrapedBusiness.setLocation("New York");
        scrapedBusiness.setName("Best Restaurant");
        scrapedBusiness.setAddress("123 Main St");
        scrapedBusiness.setPhoneNumber("123-456-7890");
        scrapedBusiness.setWebsite("https://www.bestrestaurant.com");
        scrapedBusiness.setEmailAddress("contact@bestrestaurant.com");
        scrapedBusiness.setStatusType(StatusType.PENDING);
        scrapedBusiness.setErrorMessage("No errors");
        return scrapedBusiness;
    }

    public static Mail createMail() {
        return Mail.builder()
                .fromEmail("sender@example.com")
                .toEmails("receiver@example.com")
                .subject("Hello World")
                .body("This is a test email.")
                .build();
    }
}
