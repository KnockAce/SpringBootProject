package com.rioc.ws.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "accounts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    private int accountId;

    // One to one mapping
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    // One to many mapping
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BankDetail> bankDetailList;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ColumnTransformer(
            read = "cast(AES_DECRYPT(password, 'asecretkey') as char(255))",
            write = "AES_ENCRYPT(?, 'asecretkey')"
    )
    @Column(name = "password")
    private String password;

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

    public Account(int accountId, Address address, String firstName, String lastName, String password) {
        this.accountId = accountId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<BankDetail> getBankDetailList() {
        return bankDetailList;
    }

    public void setBankDetailList(List<BankDetail> bankDetailList) {
        this.bankDetailList = bankDetailList;
    }
}