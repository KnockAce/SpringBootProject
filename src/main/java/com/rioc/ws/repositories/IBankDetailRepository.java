package com.rioc.ws.repositories;

import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dao.BankDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBankDetailRepository extends JpaRepository<BankDetail,Integer> {
    List<BankDetail> findByIBAN(String IBAN);


    @Query("select a from BankDetail a where a.account.accountId = ?1")
    List<BankDetail> findByAccountId(int accountId);
}
