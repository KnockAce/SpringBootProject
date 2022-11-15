package com.rioc.ws.controllers;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.services.account.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController
{
    private final IAccountService service;

    public AccountController(IAccountService service)
    {
        super();
        this.service = service;
    }

    @PostMapping("/account")
    public ResponseEntity<AccountDto> postAccount (@Valid @RequestBody AccountDto account, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){
        return new ResponseEntity<>(service.getAllAccounts(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/account/{idAccount}")
    public ResponseEntity<Account> getAccount(@PathVariable int idAccount){
        return new ResponseEntity<>(service.getAccountById(idAccount), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/account/{idAccount}")
    public ResponseEntity<Account> deleteAccountFromId(@PathVariable int idAccount){
        return new ResponseEntity<>(service.deleteAccountById(idAccount), HttpStatus.ACCEPTED);
    }

    @PutMapping("/account/{idAccount}")
    public ResponseEntity<Account> updateAccount(@Valid @RequestBody AccountDto account,
                                                    @PathVariable int idAccount, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.updateAccount(account, idAccount), HttpStatus.ACCEPTED);
    }

    @PostMapping("/accounts")
    public ResponseEntity<List<AccountDto>> addManyAccounts(@Valid @RequestBody List<AccountDto> accounts, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addManyAccounts(accounts), HttpStatus.CREATED);
    }

    @DeleteMapping("/accounts")
    public String deleteAllAccounts(){
        service.deleteAllAccounts();
        return "Deletion OK.";
    }
}
