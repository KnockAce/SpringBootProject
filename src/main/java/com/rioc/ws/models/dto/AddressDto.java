package com.rioc.ws.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AddressDto {

    private String cityName;
    private String streetAddress;
    private int zipCode;
    private String country;
}
