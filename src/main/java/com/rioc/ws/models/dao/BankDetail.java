package com.rioc.ws.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rioc.ws.models.dto.AccountDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bank_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BankDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_detail_id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Account account;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_iban_number")
    private String IBAN;

    public BankDetail(int id, Account account, String bankName, String IBAN) {
        this.id = id;
        this.account = account;
        this.bankName = bankName;
        this.IBAN = IBAN;
    }

    public BankDetail(Account account, String bankName, String IBAN) {
        this.account = account;
        this.bankName = bankName;
        this.IBAN = IBAN;
    }

    public BankDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}
