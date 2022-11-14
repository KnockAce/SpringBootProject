package com.rioc.ws.services.address;

import com.rioc.ws.models.dto.AddressDto;

public class test {
    public static void main(String[] args){
        AddressService addressService = new AddressService();
        AddressDto test = new AddressDto("Abbeville", "07 rue de la briolerie", 80100, "France");
        boolean t = addressService.isValidAddress(test);
        System.out.println("boolean = " + t);
    }
}
