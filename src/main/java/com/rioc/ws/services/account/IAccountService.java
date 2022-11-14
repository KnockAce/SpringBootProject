package com.rioc.ws.services.account;

import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountDto;

import java.util.List;

public interface IAccountService {
    public AccountDto postAccount(AccountDto account);
    public List<AccountDto> getAllAccounts();
    public AccountDto getAccountById(int idAccount);
    public void deleteAccountById(int idAccount);
    public Account updateAccount(Account account);

    /*
    public default AccountDto convertToDto(Account account){
        return new AccountDto(account.getFirstName(), account.getLastName());
    };

    public default Account convertToDao(AccountDto accountDto){
        return new Account(0, accountDto.getFirstName(), accountDto.getLastName());
    }*/
}
