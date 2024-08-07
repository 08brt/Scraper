package com.scraper;

import com.scraper.config.TestContainersConfig;
import com.scraper.processor.ScrapedBusinessProcessor;
import com.scraper.processor.ScrapedEmailProcessor;
import com.scraper.processor.SendEmailProcessor;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = CoreApplication.class)
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractTests {

    @MockBean
    private ScrapedBusinessProcessor scrapedBusinessProcessor;

    @MockBean
    private ScrapedEmailProcessor scrapedEmailProcessor;

    @MockBean
    private SendEmailProcessor sendEmailProcessor;
}