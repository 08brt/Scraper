package com.scraper.processor;

import com.scraper.mapper.ScrapedBusinessMapper;
import com.scraper.model.Location;
import com.scraper.service.GoogleMapsService;
import com.scraper.service.LocationQuery;
import com.scraper.service.LocationService;
import com.scraper.service.ScrapedBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapedBusinessProcessor {

    private final GoogleMapsService googleMapsService;
    private final LocationQuery locationQuery;
    private final ScrapedBusinessMapper scrapedBusinessMapper;
    private final ScrapedBusinessService scrapedBusinessService;
    private final LocationService locationService;

    @Value("${google.keyword}")
    private String keyword;

    /**
     * Scheduled task that fetches business data from Google Maps API every hour.
     * It processes unprocessed locations, maps them to ScrapedBusiness entities, and saves them to the database.
     *
     * @throws InterruptedException if the thread is interrupted during the process
     */
    @Scheduled(fixedRate = 3600000) // 1 Hour
    public void fetchGoogleData() throws InterruptedException {

        // Fetch an unprocessed location from the database
        Location location = locationQuery.getUnprocessedLocation().orElse(null);

        if (location == null) {
            return;
        }

        log.info("Scraping {} from {}", keyword, location.getCity());

        // Get fetched businesses from Google Maps API
        // Save them as a ScrapedBusiness entity to database
        googleMapsService.query(location.getCity(), keyword).stream()
                .map(googlePlaceDetails -> scrapedBusinessMapper.map(googlePlaceDetails, keyword, location.getCity()))
                .forEach(scrapedBusiness -> {
                    try {
                        log.info("Scraped business called {} ", scrapedBusiness.getName());
                        scrapedBusinessService.saveBusiness(scrapedBusiness);
                    } catch (Exception e) {
                        log.error("Error saving scraped business. Business name: {}, Business PlaceId: {}", scrapedBusiness.getName(), scrapedBusiness.getPlaceId(), e);
                    }
                });

        locationService.markLocationAsProcessed(location);
    }

}
