package com.rioc.ws.services.bankdetail;

import com.rioc.ws.exceptions.ApiException;
import com.rioc.ws.mappers.IBankDetailMapper;
import com.rioc.ws.models.dao.Account;
import com.rioc.ws.models.dao.BankDetail;
import com.rioc.ws.models.dto.BankDetailDto;
import com.rioc.ws.repositories.IAccountRepository;
import com.rioc.ws.repositories.IBankDetailRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankDetailService implements IBankDetailService {

    private final IBankDetailRepository repo;
    private final IBankDetailMapper mapper;
    private final IAccountRepository accountRepo;

    public BankDetailService(IBankDetailRepository bankDetailRepository, IBankDetailMapper bankDetailMapper, IAccountRepository accountRepository) {
        this.repo = bankDetailRepository;
        this.mapper = bankDetailMapper;
        this.accountRepo = accountRepository;
    }
    // TODO aes 128 cipher pour IBAN number
    // TODO hash du password par la suite
    public BankDetailDto postBankDetail(BankDetailDto bankDetailDto) {
        // Check if IBAN number already exists
        if (repo.findByIBAN(bankDetailDto.getIBAN()).size() > 0) {
            throw new ApiException("IBAN number already exist.", HttpStatus.BAD_REQUEST);
        }
        // Validate the IBAN number
        if (!validateIBAN(bankDetailDto.getIBAN())) {
            throw new ApiException("Invalid IBAN number.", HttpStatus.BAD_REQUEST);
        }

        BankDetail bankDetail = mapper.bankDetailDtoToBankDetail(bankDetailDto);
        // Check if account exists
        Account account = accountRepo.findById(bankDetailDto.getAccountId()).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
        // Inject account into bank detail
        bankDetail.setAccount(account);
        repo.save(bankDetail);
        return bankDetailDto;
    }

    public List<BankDetailDto> getBankDetails(int accountId) {
        // Check if account exists
        accountRepo.findById(accountId).orElseThrow(() -> new ApiException("Account not found", HttpStatus.NOT_FOUND));
        List<BankDetail> bankDetails = repo.findByAccountId(accountId);
        return bankDetails.stream().map(mapper::bankDetailToDtoBankDetail).toList();
    }

    public BankDetail deleteBankDetail(int bankDetailId) {
        BankDetail bankDetail = repo.findById(bankDetailId).orElseThrow(() -> new ApiException("Bank detail not found", HttpStatus.NOT_FOUND));
        repo.delete(bankDetail);
        return bankDetail;
    }

    public List<BankDetail> getAllBankDetail(){
        return repo.findAll();
    }

    private boolean validateIBAN(String iban) {
        String pattern = "^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$";
        System.out.println(iban.matches(pattern));
        // check if iban is valid
        return iban.matches(pattern);
    }
}

