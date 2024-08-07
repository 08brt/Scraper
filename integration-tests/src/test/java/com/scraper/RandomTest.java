package com.scraper;

import com.scraper.model.Mail;
import com.scraper.repository.MailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RandomTest extends AbstractTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PostgreSQLContainer<?> postgresContainer;

    @Autowired
    private MailRepository mailRepository;

    @Test
    void testDatabaseConnection() throws Exception {
        assertThat(postgresContainer.isRunning()).isTrue();
        assertThat(dataSource.getConnection()).isNotNull();

        System.out.println("BOBOBOB");

        Mail mail = new Mail();
        mail.setSubject("123");
        mail.setBody("123");
        mail.setToEmails("123@gmail.com");
        mail.setFromEmail("asdf@gmail.com");
        mailRepository.save(mail);

        List<Mail> mails = mailRepository.findAll();

        System.out.println(mails.size());

        System.out.println();
    }

    @Test
    void printTest() {
        System.out.println("HELLO THIS IS TEST");
    }
}
