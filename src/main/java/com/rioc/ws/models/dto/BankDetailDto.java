package com.rioc.ws.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class BankDetailDto {

    int accountId;

    @ApiModelProperty(notes = "Bank name", example = "Crédit Agricole", required = true)
    private String bankName;

    @ApiModelProperty(notes = "IBAN Number", example = "FR4210096000406235518938V55", required = true)
    private String IBAN;
}
