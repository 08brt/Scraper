package com.scraper.service;

import com.scraper.model.Location;
import com.scraper.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public void markLocationAsProcessed(Location location) {
        location.setProcessed(true);
        saveLocation(location);
    }
}
