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
    private IAccountService service;

    public AccountController(IAccountService service)
    {
        super();
        this.service = service;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDto> postAccount ( @Valid @RequestBody AccountDto account, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/accounts")
    public List<AccountDto> getAccounts(){
        return service.getAllAccounts();
    }

    @GetMapping("/account/{idAccount}")
    public AccountDto getAccount(@PathVariable int idAccount){
        return service.getAccountById(idAccount);
    }

    @DeleteMapping("/account/{idAccount}")
    public String deleteAccountFromId(@PathVariable int idAccount){
        service.deleteAccountById(idAccount);
        return "Deletion OK";
    }

    @PutMapping("/account")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        return new ResponseEntity<>(service.updateAccount(account), HttpStatus.ACCEPTED);
    }
}
