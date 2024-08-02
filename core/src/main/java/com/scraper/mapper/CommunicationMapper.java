package com.scraper.mapper;

import com.scraper.model.Communication;
import com.scraper.model.Mail;
import com.scraper.model.ScrappedBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommunicationMapper {

    public Communication map(ScrappedBusiness scrappedBusiness, Mail mail) {
        return Communication.builder()
                .scrappedBusiness(scrappedBusiness)
                .mail(mail)
                .contacted(true)
                .errorMessage(null)
                .build();
    }
}
