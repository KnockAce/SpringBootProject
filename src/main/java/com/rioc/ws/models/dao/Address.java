package com.rioc.ws.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    private int addressId;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "zip_code", nullable = false)
    private int zipCode;

    @Column(name = "country", nullable = false)
    private String country;

    public Address(int addressId, String cityName, String streetAddress, int zipCode, String country) {
        this.addressId = addressId;
        this.cityName = cityName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address(){

    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
