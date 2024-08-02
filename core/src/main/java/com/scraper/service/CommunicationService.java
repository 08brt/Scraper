package com.scraper.service;

import com.scraper.mapper.CommunicationMapper;
import com.scraper.model.Communication;
import com.scraper.model.Mail;
import com.scraper.model.ScrappedBusiness;
import com.scraper.repository.CommunicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private final CommunicationRepository communicationRepository;
    private final CommunicationMapper communicationMapper;

    public void saveCommunication(ScrappedBusiness scrappedBusiness, Mail mail) {
        Communication communication = communicationMapper.map(scrappedBusiness, mail);

        communicationRepository.save(communication);
    }
}
