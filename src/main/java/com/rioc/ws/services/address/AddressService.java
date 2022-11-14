package com.rioc.ws.services.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.models.dto.AddressDto;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressService {
    private final String baseUrl = "https://api-adresse.data.gouv.fr/search/";

    boolean isValidAddress(AddressDto addressDto){
        JsonNode data = getData(addressDto.getCityName(), addressDto.getZipCode(), addressDto.getStreetAddress());
        System.out.println(data.get("features").size());
        return data.get("features").size() > 0;
    }

    private JsonNode getData(String city, int zipCode, String street_address){
        System.out.println("city = " + city + ", zipCode = " + zipCode + ", street_address = " + street_address);
        // Fetch of the data in the API
        HttpResponse<JsonNode> response = null; // To delete
        // Url
        URL url = null;
        // conn
        HttpURLConnection con = null;
        try {
            StringBuilder apiUrl = new StringBuilder(this.baseUrl);
            apiUrl.append("?q=");
            apiUrl.append(URLEncoder.encode(street_address + " " + city, StandardCharsets.UTF_8));
            apiUrl.append("&postcode=").append(zipCode);
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
            System.out.println("status = " + status);
            if (status > 300){
                System.out.println("Erreur dans la requete.");
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
}
