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
            @ApiResponse(code = 400, message = "Error while creating bank detail.")
    })
    @ApiOperation(value = "Create a bank detail.", notes = "Created a bank detail for the given account.")
    @PostMapping("/bankdetail")
    public ResponseEntity<BankDetailDto> postBankDetail (@RequestBody BankDetailDto bankDetailDto)
    {
        return new ResponseEntity<>(service.postBankDetail(bankDetailDto), HttpStatus.CREATED);
    }

    @GetMapping("/bankdetails/{accountId}")
    public ResponseEntity<List<BankDetailDto>> getBankDetails(@PathVariable int accountId)
    {
        return new ResponseEntity<>(service.getBankDetails(accountId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/bankdetails")
    public ResponseEntity<List<BankDetail>> getAllBankDetails(){
        return new ResponseEntity<>(service.getAllBankDetail(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/bankdetail/{bankDetailId}")
    public ResponseEntity<BankDetail> deleteBankDetail(@PathVariable int bankDetailId)
    {
        return new ResponseEntity<>(service.deleteBankDetail(bankDetailId), HttpStatus.ACCEPTED);
    }
}
