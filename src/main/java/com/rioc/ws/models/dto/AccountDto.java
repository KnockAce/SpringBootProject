package com.rioc.ws.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountDto {

    @ApiModelProperty(notes = "Firstname", example = "James", required = true)
    @NotBlank(message = "Account name cannot be blank")
    @Size(min = 2, max = 40)
    private String firstName;

    @ApiModelProperty(notes = "Lastname", example = "Bond", required = true)
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 40)
    private String lastName;

    private AddressDto address;
}
