package com.rioc.ws.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreationDto {
    // This is used when we create a new account.

    @ApiModelProperty(notes = "Firstname", example = "James", required = true)
    @NotBlank(message = "Account name cannot be blank")
    @Size(min = 2, max = 40)
    private String firstName;

    @ApiModelProperty(notes = "Lastname", example = "Bond", required = true)
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 40)
    private String lastName;

    @ApiModelProperty(notes = "Password", example = "my_password", required = true)
    @Size(min = 4, max = 40)
    @NotBlank(message = "Last name cannot be blank")
    private String password;

    private AddressDto address;
}
