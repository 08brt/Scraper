package com.scraper.mapper;

import com.scraper.model.Communication;
import com.scraper.model.Mail;
import com.scraper.model.ScrapedBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommunicationMapper {

    public Communication map(ScrapedBusiness scrapedBusiness, Mail mail) {
        return Communication.builder()
                .scrapedBusiness(scrapedBusiness)
                .mail(mail)
                .contacted(true)
                .errorMessage(null)
                .build();
    }
}
