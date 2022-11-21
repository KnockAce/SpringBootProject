package com.rioc.ws.services.account;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.mappers.IAccountMapper;
import com.rioc.ws.mappers.IAddressMapper;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.repositories.IAccountRepository;
import com.rioc.ws.services.address.IAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository repository;
    private final IAccountMapper mapper;
    private final IAddressService addressService;
    private final IAddressMapper addressMapper;

    public AccountService(IAccountRepository repository, IAccountMapper mapper,
                          IAddressService addressService, IAddressMapper addressMapper) {
        super();
        this.repository = repository;
        this.mapper = mapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    public AccountDto postAccount(AccountDto account){
        boolean is_existing = checkExistingAccount(account.getFirstName(),account.getLastName());
        System.out.println("exist : " + is_existing);
        if (is_existing) {
            System.out.println("existe déjà");
            throw new ApiException("The account arleady exist.", HttpStatus.NOT_ACCEPTABLE);
        }
        // Validation de l'address via l'api
        if (!addressService.isValidAddress(account.getAddress()))
            throw new ApiException("The address is not valid", HttpStatus.NOT_ACCEPTABLE);
        repository.save(mapper.accountDtoToAccount(account));
        return account;
    }

    public Account getAccountById(int idAccount){
        return repository.findById(idAccount).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
    }

    public Account deleteAccountById(int idAccount) {
        Account account = repository.findById(idAccount).orElse(null);
        if (account == null)
            throw new ApiException("The account doesn't exist.", HttpStatus.NOT_FOUND);
        repository.deleteById(idAccount);
        return account;
    }

    public List<AccountDto> getAllAccounts(){
        return repository.findAll().stream().map(mapper::accountToDtoAccount).collect(Collectors.toList());
    }

    public Account updateAccount(AccountDto account, int idAccount){
        Account search = repository.findById(idAccount).orElse(null);
        if (search == null)
            throw new ApiException("The account doesn't exist.", HttpStatus.BAD_REQUEST);
        boolean is_existing = checkExistingAccount(account.getFirstName(),account.getLastName());
        System.out.println("exist : " + is_existing);
        if (is_existing) {
            System.out.println("existe déjà");
            throw new ApiException("The account arleady exist.", HttpStatus.NOT_ACCEPTABLE);
        }
        // Validation de l'address via l'api
        if (!addressService.isValidAddress(account.getAddress()))
            throw new ApiException("The address is not valid", HttpStatus.NOT_ACCEPTABLE);
        if(mapper.accountDtoToAccount(account).equals(search)) {
            System.out.println("No data has been modified.");
            return search;
        }
        search.setFirstName(account.getFirstName());
        search.setLastName(account.getLastName());
        // Update de l'addresse
        search.setAddress(addressMapper.addressDtoToAddress(account.getAddress()));
        return repository.save(search);
    }

    public List<AccountDto> addManyAccounts(List<AccountDto> accounts){
        accounts.forEach(account -> repository.save(mapper.accountDtoToAccount(account)));
        return accounts;
    }

    private boolean checkExistingAccount (String accountFirstName, String accountLastName){
        // test if the combo of first and last name arleady exist in the db
        List<Account> accounts = repository.findByFirstNameAndLastName(accountFirstName, accountLastName);
        System.out.println(accounts);
        return !accounts.isEmpty();
    }

    public void deleteAllAccounts(){
        repository.deleteAll();
    }
}
