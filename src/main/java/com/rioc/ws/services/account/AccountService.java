package com.rioc.ws.services.account;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.mappers.IAccountCreationMapper;
import com.rioc.ws.mappers.IAccountMapper;
import com.rioc.ws.mappers.IAddressMapper;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountCreationDto;
import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.repositories.IAccountRepository;
import com.rioc.ws.services.address.IAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository repository;
    private final IAccountMapper mapper;
    private final IAddressService addressService;
    private final IAddressMapper addressMapper;
    private final IAccountCreationMapper AccountCreationMapper;

    public AccountService(IAccountRepository repository, IAccountMapper mapper,
                          IAddressService addressService, IAddressMapper addressMapper,
                          IAccountCreationMapper AccountCreationMapper) {
        super();
        this.repository = repository;
        this.mapper = mapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.AccountCreationMapper = AccountCreationMapper;
    }

    public AccountCreationDto postAccount(AccountCreationDto account){
        boolean is_existing = checkExistingAccount(account.getFirstName(),account.getLastName());
        System.out.println("exist : " + is_existing);
        if (is_existing) {
            System.out.println("existe déjà");
            throw new ApiException("The account arleady exist.", HttpStatus.NOT_ACCEPTABLE);
        }
        // Validation de l'address via l'api
        if (!addressService.isValidAddress(account.getAddress()))
            throw new ApiException("The address is not valid", HttpStatus.NOT_ACCEPTABLE);
        repository.save(AccountCreationMapper.toAccount(account));
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

    public AccountCreationDto updateAccount(AccountCreationDto account, int idAccount){
        Account search = repository.findById(idAccount).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
        if(AccountCreationMapper.toAccount(account).equals(search)) {
            System.out.println("No data has been modified.");
            return account;
        }
        // Check si firstname et lastname sont modifiés
        if (!Objects.equals(search.getFirstName(), account.getFirstName()) || !Objects.equals(search.getLastName(), account.getLastName())) {
            System.out.println("First name or last name is different");
            if (checkExistingAccount(account.getFirstName(),account.getLastName())) {
                System.out.println("Le combo nom/prenom existe déjà");
                throw new ApiException("The account with this firstname and lastname arleady exist.", HttpStatus.NOT_ACCEPTABLE);
            }
            search.setFirstName(account.getFirstName());
            search.setLastName(account.getLastName());
        }
        // check si l'address est modifiée
        if (!Objects.equals(search.getAddress().getStreetAddress(), account.getAddress().getStreetAddress()) ||
                !Objects.equals(search.getAddress().getCityName(), account.getAddress().getCityName()) ||
                !Objects.equals(search.getAddress().getCountry(), account.getAddress().getCountry()) ||
                search.getAddress().getZipCode() != account.getAddress().getZipCode()) {
            System.out.println("L'adresse est différente");
            // Validation de l'address via l'api
            if (!addressService.isValidAddress(account.getAddress()))
                throw new ApiException("The address is not valid", HttpStatus.NOT_ACCEPTABLE);
            search.setAddress(addressMapper.addressDtoToAddress(account.getAddress()));
        }
        if(search.getPassword() != null && !Objects.equals(search.getPassword(), account.getPassword())){
            System.out.println("Le mot de passe est différent");
            search.setPassword(account.getPassword());
        }
        // update de l'account
        repository.save(search);
        return account;
    }

    public List<AccountCreationDto> addManyAccounts(List<AccountCreationDto> accounts){
        accounts.forEach(account -> repository.save(AccountCreationMapper.toAccount(account)));
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

    public String hashPassword(String password){
//        int strength = 10; // work factor of bcrypt
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
//        return bCryptPasswordEncoder.encode(password);
        return password;
    }
}
