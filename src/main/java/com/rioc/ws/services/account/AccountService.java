package com.rioc.ws.services.account;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.mappers.IAccountMapper;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.repositories.IAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository repository;
    private final IAccountMapper mapper;

    public AccountService(IAccountRepository repository, IAccountMapper mapper) {
        super();
        this.repository = repository;
        this.mapper = mapper;
    }

    public AccountDto postAccount(AccountDto account){
        boolean is_existing = checkExistingAccount(account.getFirstName(),account.getLastName());
        System.out.println("exist : " + is_existing);
        if (is_existing) {
            System.out.println("existe déjà");
            throw new ApiException("The account arleady exist.", HttpStatus.NOT_ACCEPTABLE);
        }
        repository.save(mapper.accountDtoToAccount(account));
        return account;
    }

    public AccountDto getAccountById(int idAccount){
        return mapper.accountToDtoAccount(repository.getReferenceById(idAccount));
    }

    @Override
    public void deleteAccountById(int idAccount) {
        Account account = repository.findById(idAccount).orElse(null);
        if (account == null)
            return;
        repository.deleteById(idAccount);
    }

    public List<AccountDto> getAllAccounts(){
        return repository.findAll().stream().map(mapper::accountToDtoAccount).collect(Collectors.toList());
    }

    public Account updateAccount(Account account){
        repository.save(account);
        return account;
    }

    private boolean checkExistingAccount (String accountFirstName, String accountLastName){
        // test if the combo of first and last name arleady exist in the db
        List<Account> accounts = repository.findByFirstNameAndLastName(accountFirstName, accountLastName);
        System.out.println(accounts);
        return !accounts.isEmpty();
    }

}
