package com.rioc.ws.services.account;

import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountDto;

import java.util.List;

public interface IAccountService {
    public AccountDto postAccount(AccountDto account);
    public List<AccountDto> getAllAccounts();
    public Account getAccountById(int idAccount);
    public Account deleteAccountById(int idAccount);
    public Account updateAccount(AccountDto account, int idAccount);
    public List<AccountDto> addManyAccounts(List<AccountDto> accounts);
    public void deleteAllAccounts();
}
