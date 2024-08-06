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

    /**
     * Scrapes email addresses from the specified URL using regular expression pattern matching.
     *
     * @param url the URL of the web page to scrape
     * @return an Optional containing a comma-separated string of found email addresses, or empty if no emails are found
     * @throws IOException if an I/O error occurs while connecting to the URL
     */
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

    /**
     * Marks a ScrapedBusiness entity as processed by updating its status to PROCESSED and saving it in the repository.
     *
     * @param scrapedBusiness the ScrapedBusiness entity to mark as processed
     */
    public void markProcessed(ScrapedBusiness scrapedBusiness) {
        scrapedBusiness.setStatusType(StatusType.PROCESSED);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    /**
     * Sets the email address for a ScrapedBusiness entity, marks it as pending, and saves it in the repository.
     *
     * @param scrapedBusiness the ScrapedBusiness entity to update
     * @param emails the email addresses to set for the business
     */
    public void setEmailAddressAndMarkProcessed(ScrapedBusiness scrapedBusiness, String emails) {
        scrapedBusiness.setEmailAddress(emails);
        scrapedBusiness.setStatusType(StatusType.PENDING);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    /**
     * Sets an error message for a ScrapedBusiness entity, marks it as errored, and saves it in the repository.
     *
     * @param scrapedBusiness the ScrapedBusiness entity to update
     * @param errorMessage the error message to set for the business
     */
    public void setErrorMessageAndMarkError(ScrapedBusiness scrapedBusiness, String errorMessage) {
        scrapedBusiness.setErrorMessage(errorMessage);
        scrapedBusiness.setStatusType(StatusType.ERROR);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    /**
     * Marks a ScrapedBusiness entity as emailed by updating its status to EMAILED and saving it in the repository.
     *
     * @param scrapedBusiness the ScrapedBusiness entity to mark as emailed
     */
    public void setAsEmailed(ScrapedBusiness scrapedBusiness) {
        scrapedBusiness.setStatusType(StatusType.EMAILED);
        scrapedBusinessRepository.save(scrapedBusiness);
    }

    public void saveBusiness(ScrapedBusiness scrapedBusiness) {
        scrapedBusinessRepository.save(scrapedBusiness);
    }
}
