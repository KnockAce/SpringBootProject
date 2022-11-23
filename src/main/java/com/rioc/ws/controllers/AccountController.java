package com.rioc.ws.controllers;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dto.AccountCreationDto;
import com.rioc.ws.models.dto.AccountDto;
import com.rioc.ws.services.account.IAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created account"),
            @ApiResponse(code = 400, message = "Error while creating account"),
            @ApiResponse(code = 406, message = "Wrong data. Account already exist or address is not valid"),
    })
    @ApiOperation(value = "Create an account", notes = "Create an account with the given information")
    @PostMapping("/account")
    public ResponseEntity<AccountCreationDto> postAccount (@Valid @RequestBody AccountCreationDto account, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postAccount(account), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully retrieved accounts."),
            @ApiResponse(code = 404, message = "Account not found"),
    })
    @ApiOperation(value = "Get all accounts", notes = "Get all accounts.")
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){
        return new ResponseEntity<>(service.getAllAccounts(), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully retrieved account"),
            @ApiResponse(code = 404, message = "Account not found"),
    })
    @ApiOperation(value = "Get an account", notes = "Get an account with the given id.")
    @GetMapping("/account/{idAccount}")
    public ResponseEntity<Account> getAccount(@PathVariable int idAccount){
        return new ResponseEntity<>(service.getAccountById(idAccount), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully deleted account"),
            @ApiResponse(code = 404, message = "Account not found"),
    })
    @ApiOperation(value = "Delete an account", notes = "Delete an account with the given id.")
    @DeleteMapping("/account/{idAccount}")
    public ResponseEntity<Account> deleteAccountFromId(@PathVariable int idAccount){
        return new ResponseEntity<>(service.deleteAccountById(idAccount), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully updated account."),
            @ApiResponse(code = 404, message = "Account not found"),
            @ApiResponse(code = 403, message = "The input data is not correct."),
    })
    @ApiOperation(value = "Update an account", notes = "Update an account with the given id.")
    @PutMapping("/account/{idAccount}")
    public ResponseEntity<AccountCreationDto> updateAccount(@Valid @RequestBody AccountCreationDto account,
                                                    @PathVariable int idAccount, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.updateAccount(account, idAccount), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully created accounts."),
            @ApiResponse(code = 403, message = "The input data is not correct."),
            @ApiResponse(code = 406, message = "Wrong data. Account already exist or address is not valid."),
    })
    @ApiOperation(value = "Create many accounts", notes = "Create many accounts with the given information.")
    @PostMapping("/accounts")
    public ResponseEntity<List<AccountCreationDto>> addManyAccounts(@Valid @RequestBody List<AccountCreationDto> accounts, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("La data n'est pas valide.");
            throw new ApiException("The input data is not correct.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addManyAccounts(accounts), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully deleted all accounts."),
            @ApiResponse(code = 404, message = "Account not found"),
    })
    @ApiOperation(value = "Delete all accounts", notes = "Delete all accounts.")
    @DeleteMapping("/accounts")
    public ResponseEntity<String> deleteAllAccounts(){
        service.deleteAllAccounts();
        return new ResponseEntity<>("Deletion OK.", HttpStatus.ACCEPTED);
    }
}
