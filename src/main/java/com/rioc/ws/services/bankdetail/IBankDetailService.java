package com.rioc.ws.services.bankdetail;

import com.rioc.ws.models.dao.BankDetail;
import com.rioc.ws.models.dto.BankDetailDto;

import java.util.List;

public interface IBankDetailService {
    public BankDetailDto postBankDetail(BankDetailDto bankDetailDto);
    public List<BankDetailDto> getBankDetails(int accountId);
    public BankDetail deleteBankDetail(int bankDetailId);
    List<BankDetail> getAllBankDetail();
    public BankDetail getBankDetailById(int bankDetailId);
}
