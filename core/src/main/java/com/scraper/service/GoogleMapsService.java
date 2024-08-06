package com.scraper.service;

import com.scraper.model.GooglePlaceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoogleMapsService {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.api.url}")
    private String apiUrl;

    /**
     * Queries Google Places API for place details based on location and keyword.
     *
     * @param location the location to search in
     * @param keyword the keyword to search for
     * @return a list of GooglePlaceDetails containing the place details
     * @throws InterruptedException if the thread is interrupted while sleeping between requests
     */
    public List<GooglePlaceDetails> query(String location, String keyword) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", apiKey);
        headers.set("X-Goog-FieldMask", "places.id,places.displayName,places.internationalPhoneNumber,places.websiteUri,places.formattedAddress");

        String query = keyword + " in " + location;
        String requestBody = "{ \"textQuery\" : \"" + query + "\" }";

        List<GooglePlaceDetails> allDetails = new ArrayList<>();
        fetchPlaces(restTemplate, headers, requestBody, allDetails, null);

        return allDetails;
    }

    /**
     * Fetches places from Google Places API and populates the provided list with the results.
     *
     * @param restTemplate the RestTemplate for making HTTP requests
     * @param headers the HTTP headers to include in the request
     * @param requestBody the JSON body to send with the request
     * @param allDetails the list to populate with place details
     * @param pageToken the token for fetching the next page of results, or null for the first page
     * @throws InterruptedException if the thread is interrupted while sleeping between requests
     */
    private void fetchPlaces(RestTemplate restTemplate, HttpHeaders headers, String requestBody, List<GooglePlaceDetails> allDetails, String pageToken) throws InterruptedException {
        if (pageToken != null) {
            requestBody = requestBody.substring(0, requestBody.length() - 1) + ", \"pageToken\" : \"" + pageToken + "\" }";
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

        List<GooglePlaceDetails> newDetails = parseResponse(response.getBody());
        allDetails.addAll(newDetails);

        if (response.getBody().containsKey("nextPageToken") && allDetails.size() < 60) {
            // Google requires a short delay before requesting the next page
            Thread.sleep(2000);
            String nextPageToken = (String) response.getBody().get("nextPageToken");
            fetchPlaces(restTemplate, headers, requestBody, allDetails, nextPageToken);
        }
    }

    /**
     * Parses the response from Google Places API and returns a list of GooglePlaceDetails.
     *
     * @param response the response body from the API
     * @return a list of GooglePlaceDetails extracted from the response
     */
    private List<GooglePlaceDetails> parseResponse(Map<String, Object> response) {
        List<GooglePlaceDetails> placeDetailsList = new ArrayList<>();

        List<Map<String, Object>> places = (List<Map<String, Object>>) response.get("places");
        if (places != null) {
            for (Map<String, Object> place : places) {
                String id = (String) place.get("id");
                Map<String, String> displayName = (Map<String, String>) place.get("displayName");
                String name = displayName != null ? displayName.get("text") : null;
                String internationalPhoneNumber = (String) place.get("internationalPhoneNumber");
                String formattedAddress = (String) place.get("formattedAddress");
                String websiteUri = (String) place.get("websiteUri");

                GooglePlaceDetails details = new GooglePlaceDetails(id, name, internationalPhoneNumber, formattedAddress, websiteUri);
                placeDetailsList.add(details);
            }
        }

        return placeDetailsList;
    }

}