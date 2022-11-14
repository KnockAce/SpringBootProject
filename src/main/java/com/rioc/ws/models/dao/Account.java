package com.rioc.ws.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "accounts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    private int accountId;

    // One to one mapping
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Account() {

    }

    public Account(int accountId, String firstName, String lastName) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account(int accountId, Address address, String firstName, String lastName) {
        this.accountId = accountId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}