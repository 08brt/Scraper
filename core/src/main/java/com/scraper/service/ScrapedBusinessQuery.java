package com.scraper.service;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.ScrapedBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapedBusinessQuery {

    private final ScrapedBusinessRepository scrapedBusinessRepository;

    /**
     * Retrieves a list of ScrapedBusiness entities with a status of PENDING and a non-null website.
     *
     * @param recordsSize the maximum number of records to retrieve
     * @return a list of ScrapedBusiness entities with a PENDING status and non-null website
     */
    public List<ScrapedBusiness> getScrapedBusinessByPendingAndWebsiteNotNull(int recordsSize) {
        return scrapedBusinessRepository
                .findAllByStatusTypeAndWebsiteIsNotNullOrderById(StatusType.PENDING, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }

    /**
     * Retrieves a list of ScrapedBusiness entities with a status of PROCESSED and a non-null email address.
     *
     * @param recordsSize the maximum number of records to retrieve
     * @return a list of ScrapedBusiness entities with a PROCESSED status and non-null email address
     */
    public List<ScrapedBusiness> getScrapedBusinessByEmailNotNull(int recordsSize) {
        return scrapedBusinessRepository
                .findAllByStatusTypeAndEmailAddressIsNotNull(StatusType.PROCESSED, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }
}
