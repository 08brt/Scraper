package com.scraper.service;

import com.scraper.enums.StatusType;
import com.scraper.model.ScrapedBusiness;
import com.scraper.repository.ScrapedBusinessRepository;
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
public class ScrapedBusinessService {

    private final ScrapedBusinessRepository scrapedBusinessRepository;

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
    public void markProcessed(ScrapedBusiness scrapedBusiness) {
        scrapedBusiness.setStatusType(StatusType.PROCESSED);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    // SET EMAIL
    // MARK AS PROCESSED
    public void setEmailAddressAndMarkProcessed(ScrapedBusiness scrapedBusiness, String emails) {
        scrapedBusiness.setEmailAddress(emails);
        scrapedBusiness.setStatusType(StatusType.PENDING);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    // SET ERROR MESSAGE
    // MARK AS ERRORED
    public void setErrorMessageAndMarkError(ScrapedBusiness scrapedBusiness, String errorMessage) {
        scrapedBusiness.setErrorMessage(errorMessage);
        scrapedBusiness.setStatusType(StatusType.ERROR);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    // MARK AS EMAILED
    public void setAsEmailed(ScrapedBusiness scrapedBusiness) {
        scrapedBusiness.setStatusType(StatusType.EMAILED);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    public void saveBusiness(ScrapedBusiness scrapedBusiness) {
        scrapedBusinessRepository.save(scrapedBusiness);
    }
}
