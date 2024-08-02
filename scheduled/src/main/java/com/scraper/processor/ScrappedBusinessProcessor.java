package com.scraper.processor;

import com.scraper.mapper.ScrappedBusinessMapper;
import com.scraper.model.Location;
import com.scraper.service.GoogleMapsService;
import com.scraper.service.LocationQuery;
import com.scraper.service.LocationService;
import com.scraper.service.ScrappedBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrappedBusinessProcessor {

    private final GoogleMapsService googleMapsService;
    private final LocationQuery locationQuery;
    private final ScrappedBusinessMapper scrappedBusinessMapper;
    private final ScrappedBusinessService scrappedBusinessService;
    private final LocationService locationService;

    @Value("${google.keyword}")
    private String keyword;

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
                .map(googlePlaceDetails -> scrappedBusinessMapper.map(googlePlaceDetails, keyword, location.getCity()))
                .forEach(scrappedBusiness -> {
                    try {
                        log.info("Scraped business called {} ", scrappedBusiness.getName());
                        scrappedBusinessService.saveBusiness(scrappedBusiness);
                    } catch (Exception e) {
                        log.error("Error saving scrapped business. Business name: {}, Business PlaceId: {}", scrappedBusiness.getName(), scrappedBusiness.getPlaceId(), e);
                    }
                });

        locationService.markLocationAsProcessed(location);
    }

}
