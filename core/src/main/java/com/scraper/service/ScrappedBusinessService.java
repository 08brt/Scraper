package com.scraper.service;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrappedBusiness;
import com.scraper.repository.ScrappedBusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrappedBusinessService {

    private final ScrappedBusinessRepository scrappedBusinessRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}"
    );

    public Optional<String> scrapeEmails(String url) throws IOException {
        Set<String> emails = new HashSet<>();
        Document doc = Jsoup.connect(url).get();
        String text = doc.text();
        Matcher matcher = EMAIL_PATTERN.matcher(text);

        while (matcher.find()) {
            emails.add(matcher.group());
        }

        return emails.isEmpty() ? Optional.empty() : Optional.of(String.join(", ", emails));
    }

    // MARK AS PROCESSED
    public void markProcessed(ScrappedBusiness scrappedBusiness) {
        scrappedBusiness.setStatusType(StatusType.PROCESSED);
        scrappedBusinessRepository.save(scrappedBusiness);
    }

    // SET EMAIL
    // MARK AS PROCESSED
    public void setEmailAddressAndMarkProcessed(ScrappedBusiness scrappedBusiness, String emails) {
        scrappedBusiness.setEmailAddress(emails);
        scrappedBusiness.setStatusType(StatusType.PENDING);
        scrappedBusinessRepository.save(scrappedBusiness);
    }

    // SET ERROR MESSAGE
    // MARK AS ERRORED
    public void setErrorMessageAndMarkError(ScrappedBusiness scrappedBusiness, String errorMessage) {
        scrappedBusiness.setErrorMessage(errorMessage);
        scrappedBusiness.setStatusType(StatusType.ERROR);
        scrappedBusinessRepository.save(scrappedBusiness);
    }

    // MARK AS EMAILED
    public void setAsEmailed(ScrappedBusiness scrappedBusiness) {
        scrappedBusiness.setStatusType(StatusType.EMAILED);
        scrappedBusinessRepository.save(scrappedBusiness);
    }

    public void saveBusiness(ScrappedBusiness scrappedBusiness) {
        scrappedBusinessRepository.save(scrappedBusiness);
    }
}
