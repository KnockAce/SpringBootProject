package com.rioc.ws.mappers;

import com.rioc.ws.models.dao.Address;
import com.rioc.ws.models.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IAddressMapper {
    AddressDto addressToDtoAddress (Address address);
    Address addressDtoToAddress(AddressDto addressDto);
}
