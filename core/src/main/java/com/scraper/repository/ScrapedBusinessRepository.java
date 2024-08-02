package com.scraper.repository;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrapedBusiness;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapedBusinessRepository extends JpaRepository<ScrapedBusiness, Long>, JpaSpecificationExecutor<ScrapedBusiness> {

    List<ScrapedBusiness> findAllByStatusTypeAndWebsiteIsNotNullOrderById(StatusType statusType, Pageable pageable);

    List<ScrapedBusiness> findAllByStatusTypeAndEmailAddressIsNotNull(StatusType statusType, Pageable pageable);
}
