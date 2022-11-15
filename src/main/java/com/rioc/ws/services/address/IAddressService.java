package com.rioc.ws.services.address;

import com.rioc.ws.models.dto.AddressDto;

public interface IAddressService {

    public boolean isValidAddress(AddressDto addressDto);

}
