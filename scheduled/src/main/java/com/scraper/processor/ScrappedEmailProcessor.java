package com.scraper.processor;

import com.scraper.model.ScrappedBusiness;
import com.scraper.service.ScrappedBusinessQuery;
import com.scraper.service.ScrappedBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrappedEmailProcessor {

    private final ScrappedBusinessQuery scrappedBusinessQuery;
    private final ScrappedBusinessService scrappedBusinessService;

//    @Scheduled(fixedRate = 3600000) // 1 Hour
//    public void processScrappedBusinessesForEmails() {
//        processScrappedBusiness();
//    }

    public void processScrappedBusiness() {
        scrappedBusinessQuery
                .getScrappedBusinessByPendingAndWebsiteNotNull(20)
                .forEach(this::scrapeEmail);
    }

    public void scrapeEmail(ScrappedBusiness scrappedBusiness) {
    log.info("Currently scraping emails from website: {}", scrappedBusiness.getWebsite());

        try {
            Optional<String> emails = scrappedBusinessService.scrapeEmails(scrappedBusiness.getWebsite());

            if (emails.isEmpty()) {
                log.info("Could not find any email in {}", scrappedBusiness.getWebsite());
                scrappedBusinessService.markProcessed(scrappedBusiness);
                return;
            }

            log.info("Email {} scraped from website {}", emails.get(), scrappedBusiness.getWebsite());
            scrappedBusinessService.setEmailAddressAndMarkProcessed(scrappedBusiness, emails.get());


        } catch (Exception e) {
            log.error("Error occurred while processing ScrappedBusinessId {}", scrappedBusiness.getId(), e);
            scrappedBusinessService.setErrorMessageAndMarkError(scrappedBusiness, e.getMessage());
        }
    }

}
