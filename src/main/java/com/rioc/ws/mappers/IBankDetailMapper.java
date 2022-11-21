package com.rioc.ws.mappers;

import com.rioc.ws.models.dao.BankDetail;
import com.rioc.ws.models.dto.BankDetailDto;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IBankDetailMapper {
    BankDetailDto bankDetailToDtoBankDetail (BankDetail bankDetail);
    BankDetail bankDetailDtoToBankDetail(BankDetailDto bankDetailDto);
}
