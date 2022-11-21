package com.rioc.ws.services.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.models.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@Service
public class AddressService implements IAddressService {
    private final String baseUrl = "https://api-adresse.data.gouv.fr/search/";


    public boolean isValidAddress(AddressDto addressDto){
        // Minimum
        int minScore = 65;
        // Get data from api
        JsonNode data = getData(addressDto.getCityName(), addressDto.getZipCode(), addressDto.getStreetAddress());
        System.out.println(data.get("features").size());
        JsonNode features = data.get("features");
        // check zip code
        if (!isZipCodeValid(addressDto.getZipCode())){
            System.out.println("Zip code is not valid.");
            return false;
        }
        // No match found
        if (features.size() == 0) {
            System.out.println("No match found.");
            return false;
        }
        // Getting score of results and compare it to 65
        for (   JsonNode feature : features) {
            System.out.println("Address : " + feature.get("properties").get("label"));
            System.out.println(feature.get("properties").get("score"));
            float score = feature.get("properties").get("score").floatValue();
            if (score * 100 >= minScore){
                System.out.println("Sufisant match found.");
                return true;
            }
        }
        System.out.println("No sufisant match found.");
        return false;
    }

    private JsonNode getData(String city, int zipCode, String street_address){
        System.out.println("city = " + city + ", zipCode = " + zipCode + ", street_address = " + street_address);
        // Fetch of the data in the API
        HttpResponse<JsonNode> response = null; // To delete
        // Url
        URL url = null;
        // connector for http
        HttpURLConnection con = null;
        try {
            StringBuilder apiUrl = new StringBuilder(this.baseUrl);
            apiUrl.append("?q="); // Query parameter for the street address
            // We need to encode the street address to avoid special characters and spaces
            apiUrl.append(URLEncoder.encode(street_address + " " + city, StandardCharsets.UTF_8));
            apiUrl.append("&postcode=").append(zipCode); // Add the zip code to the query
            apiUrl.append("&type=street"); // We only want street
            System.out.println("apiUrl = " + apiUrl);
            url = new URL(apiUrl.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Issue when validating the address please come back later.", HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Has to be a good response
        try {
            int status = con.getResponseCode();
            if (status > 300){
                System.out.println("Erreur dans la requete status = " + status);
                throw new ApiException("Issue when validating the address please come back later.", HttpStatus.SERVICE_UNAVAILABLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Issue when validating the address please come back later.", HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Checking content
        StringBuilder raw_response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                raw_response.append(responseLine.trim());
            }
            System.out.println(raw_response.toString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode obj = mapper.readTree(raw_response.toString());
            System.out.println("actualObj = " + obj);
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Issue when validating the address please come back later.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private boolean isZipCodeValid(int zipCode){
        String zipcode_regex = "^(?:0[1-9]|[1-8]\\d|9[0-8])\\d{3}$";
        return String.valueOf(zipCode).matches(zipcode_regex);
    }
}
