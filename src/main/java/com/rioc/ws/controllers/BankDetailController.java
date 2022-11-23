package com.rioc.ws.controllers;

import com.rioc.ws.models.dao.BankDetail;
import com.rioc.ws.models.dto.BankDetailDto;
import com.rioc.ws.services.bankdetail.IBankDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankDetailController {

    private final IBankDetailService service;

    public BankDetailController(IBankDetailService service) {
        super();
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a bank detail for the given account."),
            @ApiResponse(code = 403, message = "Input data is not correct.")
    })
    @ApiOperation(value = "Create a bank detail.", notes = "Created a bank detail for the given account.")
    @PostMapping("/bankdetail")
    public ResponseEntity<BankDetailDto> postBankDetail (@RequestBody BankDetailDto bankDetailDto)
    {
        return new ResponseEntity<>(service.postBankDetail(bankDetailDto), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully obtained all bank details fot the account."),
            @ApiResponse(code = 404, message = "The account doesn't exist.")
    })
    @ApiOperation(value="Get all bank details for an account.")
    @GetMapping("/bankdetails/{accountId}")
    public ResponseEntity<List<BankDetailDto>> getBankDetails(@PathVariable int accountId)
    {
        return new ResponseEntity<>(service.getBankDetails(accountId), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully obtained all bank details.")
    })
    @ApiOperation(value="Get all bank details.")
    @GetMapping("/bankdetails")
    public ResponseEntity<List<BankDetail>> getAllBankDetails(){
        return new ResponseEntity<>(service.getAllBankDetail(), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully deleted the account."),
            @ApiResponse(code = 404, message = "The account doesn't exist.")
    })
    @ApiOperation(value="Delete the given bank detail.")
    @DeleteMapping("/bankdetail/{bankDetailId}")
    public ResponseEntity<BankDetail> deleteBankDetail(@PathVariable int bankDetailId)
    {
        return new ResponseEntity<>(service.deleteBankDetail(bankDetailId), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully deleted the account."),
            @ApiResponse(code = 404, message = "The account doesn't exist.")
    })
    @ApiOperation(value="Get a bank detail by id.")
    @GetMapping("/bankdetail/{bankDetailId}")
    public ResponseEntity<BankDetail> getBankDetailById(@PathVariable int bankDetailId)
    {
        return new ResponseEntity<>(service.getBankDetailById(bankDetailId), HttpStatus.ACCEPTED);
    }
}
