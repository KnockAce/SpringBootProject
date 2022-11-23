package com.rioc.ws.mappers;

import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountCreationDto;
import com.rioc.ws.models.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IAccountCreationMapper {
    AccountCreationDto toDtoAccount (Account account);
    Account toAccount (AccountCreationDto accountDto);
}
