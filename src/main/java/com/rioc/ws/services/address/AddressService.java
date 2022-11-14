package com.rioc.ws.services.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rioc.ws.models.dto.AddressDto;

import java.io.*;
import java.net.*;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AddressService {
    // TODO validation de l'address avec l'api
    // https://adresse.data.gouv.fr/api-doc/adresse
    private String baseUrl = "https://api-adresse.data.gouv.fr/search/";

    boolean isValidAddress(AddressDto addressDto){
        // todo
        getData(addressDto.getCityName(), addressDto.getZipCode(), addressDto.getStreetAddress());
        return false;
    }

    private JsonNode getData(String city, int zipCode, String street_address){
        System.out.println("city = " + city + ", zipCode = " + zipCode + ", street_address = " + street_address);
        // Fetch of the data in the API
        HttpResponse<JsonNode> response = null; // To delete
        // Url
        URL url = null;
        try {
            StringBuilder apiUrl = new StringBuilder(this.baseUrl);
            apiUrl.append("?q=");
            apiUrl.append(URLEncoder.encode(street_address + " " + city, StandardCharsets.UTF_8));
            apiUrl.append("&postcode=").append(zipCode);
            System.out.println("apiUrl = " + apiUrl);
            url = new URL(apiUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return (JsonNode) response;
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
            return (JsonNode) response;
        }


        try {
            int status = con.getResponseCode();
            System.out.println("status = " + status);
            if (status > 300){
                System.out.println("Erreur dans la requete.");
                return (JsonNode) response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Get content
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
            obj.get("features");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (JsonNode) response;
    }
}
