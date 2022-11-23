package com.rioc.ws.services.account;

import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountCreationDto;
import com.rioc.ws.models.dto.AccountDto;

import java.util.List;

public interface IAccountService {
    public AccountCreationDto postAccount(AccountCreationDto account);
    public List<AccountDto> getAllAccounts();
    public Account getAccountById(int idAccount);
    public Account deleteAccountById(int idAccount);
    public AccountCreationDto updateAccount(AccountCreationDto account, int idAccount);
    public List<AccountCreationDto> addManyAccounts(List<AccountCreationDto> accounts);
    public void deleteAllAccounts();
}
