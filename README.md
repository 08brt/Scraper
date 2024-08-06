# Scraper Application

## Overview

The Scraper Application is a Spring Boot-based project designed to automate the process of scraping business details from Google Maps, extracting contact emails from business websites, and sending promotional emails to these businesses. It utilizes the Google Places API for data extraction and processes the information to manage and execute email communications. The application is designed to run as a scheduled task, allowing for regular updates and communications with newly scraped businesses.

## Features

- **Automated Data Scraping**: Retrieves business information from Google Maps based on specified locations and keywords.
- **Email Extraction**: Scrapes emails from the websites of the scraped businesses.
- **Email Communication**: Sends promotional emails to businesses using predefined email templates.
- **Status Management**: Tracks and updates the status of businesses throughout the scraping and email-sending processes.

## Application Workflow

- **Data Scraping**: The `ScrapedBusinessProcessor` retrieves unprocessed locations and fetches business details using the Google Maps API. These details are stored in the database as `ScrapedBusiness` entities.

- **Email Extraction**: The `ScrapedEmailProcessor` processes businesses with websites, extracting emails using the `Jsoup` library and updates the `ScrapedBusiness` entities.

- **Email Sending**: The `SendEmailProcessor` retrieves businesses with email addresses and sends promotional emails using predefined templates. The status of each business is updated accordingly.

- **Error Handling**: Throughout the process, any errors encountered are logged, and the status of the affected business is updated to reflect the issue.

## Setup and Usage

### Prerequisites

- Java 17
- Maven
- Docker (for running PostgreSQL)

### Configuration

1. Clone the repository.

2. Configure the application properties in `src/main/resources/application.properties` to include your Google API key, database connection details, and other necessary configurations.

3. Execute the SQL scripts provided in the **Database Schema** section to set up the required database tables.

### Running the Application

1. Build the project using Maven:

   ```bash
   mvn clean install

2. Starting docker

   ```bash
   docker-compose up

3. Run the application (CORE):

    ```bash
   mvn spring-boot:run -pl :core

4. Run the application (SCHEDULED):

    ```bash
   mvn spring-boot:run -pl :scheduled