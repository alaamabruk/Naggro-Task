package com.NaggroTask.service;

import java.time.LocalDate;

import com.NaggroTask.dto.AccountStatementDTO;

public interface StatementService {

	AccountStatementDTO getAccountStatement(Integer accountId,
			LocalDate fromDate, LocalDate toDate, Double fromAmount,
			Double toAmount);

}
