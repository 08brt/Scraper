package com.scraper.service;

import com.scraper.model.Location;
import com.scraper.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationQuery {

    private final LocationRepository locationRepository;

    public Optional<Location> getUnprocessedLocation() {
        return locationRepository.findFirstByProcessedIsFalse();
    }
}