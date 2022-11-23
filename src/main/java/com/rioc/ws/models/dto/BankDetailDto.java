package com.rioc.ws.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class BankDetailDto {

    @ApiModelProperty(notes = "The id of the account", example = "1", required = true)
    int accountId;

    @ApiModelProperty(notes = "Bank name", example = "Crédit Agricole", required = true)
    private String bankName;

    @ApiModelProperty(notes = "IBAN Number", example = "FR7612548029989876543210917", required = true, position = 1)
    private String iban;
}
