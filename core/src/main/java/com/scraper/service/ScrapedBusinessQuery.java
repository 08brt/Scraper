package com.scraper.service;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.ScrapedBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapedBusinessQuery {

    private final ScrapedBusinessRepository scrapedBusinessRepository;

    @Transactional
    public List<ScrapedBusiness> getScrapedBusinessByPendingAndWebsiteNotNull(int recordsSize) {
        return scrapedBusinessRepository
                .findAllByStatusTypeAndWebsiteIsNotNullOrderById(StatusType.PENDING, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }

    @Transactional
    public List<ScrapedBusiness> getScrapedBusinessByEmailNotNull(int recordsSize) {
        return scrapedBusinessRepository
                .findAllByStatusTypeAndEmailAddressIsNotNull(StatusType.PROCESSED, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }
}
