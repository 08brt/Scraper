package com.scraper;

import com.scraper.model.Location;
import com.scraper.repository.LocationRepository;
import com.scraper.service.LocationQuery;
import com.scraper.service.LocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class LocationTest extends AbstractTests {

    @Autowired
    private LocationQuery locationQuery;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    private Location location;

    @BeforeEach
    void setUp() {
        // Delete all as BASELINE SCRIPT populates LOCATIONS
        locationRepository.deleteAll();

        location = new Location();
        location.setCity("New York");
        location.setLatitude(10.0);
        location.setLongitude(20.0);
        location.setCountry("United States");
        location.setIso2("US");
        location.setProcessed(false);
    }

    @AfterEach
    void cleanUp() {
        locationRepository.deleteAll();
    }

    @Test
    void getUnprocessedLocationTest() {
        locationService.saveLocation(location);

        Optional<Location> optionalLocation = locationQuery.getUnprocessedLocation();

        Assertions.assertTrue(optionalLocation.isPresent(), "Expected an unprocessed location to be returned.");
        Assertions.assertEquals(location.getCity(), optionalLocation.get().getCity(), "The city of the returned location does not match the expected value.");
        Assertions.assertEquals(location.getLatitude(), optionalLocation.get().getLatitude(), "The latitude of the returned location does not match the expected value.");
        Assertions.assertEquals(location.getLongitude(), optionalLocation.get().getLongitude(), "The longitude of the returned location does not match the expected value.");
        Assertions.assertEquals(location.getCountry(), optionalLocation.get().getCountry(), "The country of the returned location does not match the expected value.");
        Assertions.assertEquals(location.getIso2(), optionalLocation.get().getIso2(), "The ISO2 code of the returned location does not match the expected value.");
        Assertions.assertFalse(optionalLocation.get().isProcessed(), "The location should be marked as unprocessed.");
    }

    @Test
    void markLocationAsProcessed() {
        location = locationService.saveLocation(location);

        locationService.markLocationAsProcessed(location);

        Optional<Location> optionalLocation = locationRepository.findById(location.getId());

        Assertions.assertTrue(optionalLocation.isPresent(), "Expected an unprocessed location to be returned.");
        Assertions.assertTrue(optionalLocation.get().isProcessed(), "The location should be marked as unprocessed.");
    }
}
