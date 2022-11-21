package com.rioc.ws.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AddressDto {

    @ApiModelProperty(notes = "City Name", example = "Abbeville", required = true)
    @NotNull(message = "Street name cannot be null")
    private String cityName;

    @ApiModelProperty(notes = "Street ", example = "07 rue Ledien", required = true)
    @NotNull(message = "Street name cannot be null")
    private String streetAddress;

    @ApiModelProperty(notes = "Zip Code", example = "80100", required = true)
    @NotNull(message = "Zip code cannot be null")
    private int zipCode;

    @ApiModelProperty(notes = "Country", example = "France", required = true)
    @NotNull(message = "Country cannot be null")
    private String country;
}
