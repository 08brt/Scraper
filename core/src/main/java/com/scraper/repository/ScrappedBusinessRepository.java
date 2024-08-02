package com.scraper.repository;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrappedBusiness;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrappedBusinessRepository extends JpaRepository<ScrappedBusiness, Long>, JpaSpecificationExecutor<ScrappedBusiness> {

    List<ScrappedBusiness> findAllByStatusTypeAndWebsiteIsNotNullOrderById(StatusType statusType, Pageable pageable);

    List<ScrappedBusiness> findAllByStatusTypeAndEmailAddressIsNotNull(StatusType statusType, Pageable pageable);
}
