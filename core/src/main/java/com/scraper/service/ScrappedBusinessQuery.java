package com.scraper.service;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrappedBusiness;
import com.scraper.repository.ScrappedBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrappedBusinessQuery {

    private final ScrappedBusinessRepository scrappedBusinessRepository;

    @Transactional
    public List<ScrappedBusiness> getScrappedBusinessByPendingAndWebsiteNotNull(int recordsSize) {
        return scrappedBusinessRepository
                .findAllByStatusTypeAndWebsiteIsNotNullOrderById(StatusType.PENDING, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }

    @Transactional
    public List<ScrappedBusiness> getScrappedBusinessByEmailNotNull(int recordsSize) {
        return scrappedBusinessRepository
                .findAllByStatusTypeAndEmailAddressIsNotNull(StatusType.PROCESSED, PageRequest.of(0, recordsSize))
                .stream()
                .toList();
    }
}
