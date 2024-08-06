package com.scraper.processor;

import com.scraper.model.ScrapedBusiness;
import com.scraper.service.ScrapedBusinessQuery;
import com.scraper.service.ScrapedBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapedEmailProcessor {

    private final ScrapedBusinessQuery scrapedBusinessQuery;
    private final ScrapedBusinessService scrapedBusinessService;

    @Scheduled(fixedRate = 3600000) // 1 Hour
    public void processScrapedBusinessesForEmails() {
        processScrapedBusiness();
    }

    public void processScrapedBusiness() {
        scrapedBusinessQuery
                .getScrapedBusinessByPendingAndWebsiteNotNull(20)
                .forEach(this::scrapeEmail);
    }

    /**
     * Scrapes emails from the website of the given ScrapedBusiness.
     * Updates the business status and email address if emails are found, otherwise marks it as processed.
     *
     * @param scrapedBusiness the ScrapedBusiness entity whose website will be scraped for emails
     */
    public void scrapeEmail(ScrapedBusiness scrapedBusiness) {
    log.info("Currently scraping emails from website: {}", scrapedBusiness.getWebsite());

        try {
            Optional<String> emails = scrapedBusinessService.scrapeEmails(scrapedBusiness.getWebsite());

            if (emails.isEmpty()) {
                log.info("Could not find any email in {}", scrapedBusiness.getWebsite());
                scrapedBusinessService.markProcessed(scrapedBusiness);
                return;
            }

            log.info("Email {} scraped from website {}", emails.get(), scrapedBusiness.getWebsite());
            scrapedBusinessService.setEmailAddressAndMarkProcessed(scrapedBusiness, emails.get());


        } catch (Exception e) {
            log.error("Error occurred while processing ScrapedBusinessId {}", scrapedBusiness.getId(), e);
            scrapedBusinessService.setErrorMessageAndMarkError(scrapedBusiness, e.getMessage());
        }
    }

}
