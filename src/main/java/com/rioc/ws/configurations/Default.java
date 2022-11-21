package com.rioc.ws.configurations;

import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.models.dto.AddressDto;

public class Default {
    public static final AddressDto DEFAULT_ADDRESS = AddressDto.builder()
            .cityName("Paris")
            .streetAddress("07 rue de la paix")
            .zipCode(75000)
            .country("France")
            .build();
    public static final AccountDto DEFAULT_ACCOUNT = AccountDto.builder()
            .firstName("John")
            .lastName("Doe")
            .address(DEFAULT_ADDRESS)
            .build();
}
