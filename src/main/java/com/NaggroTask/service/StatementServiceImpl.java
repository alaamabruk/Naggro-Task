package com.NaggroTask.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NaggroTask.dto.AccountStatementDTO;
import com.NaggroTask.dto.StatementDTO;
import com.NaggroTask.entity.Account;
import com.NaggroTask.entity.Statement;
import com.NaggroTask.exception.InvalidAmountRangeException;
import com.NaggroTask.exception.InvalidDateRangeException;
import com.NaggroTask.exception.MissingParamException;
import com.NaggroTask.repo.AccountRepository;
import com.NaggroTask.repo.StatementRepository;
import com.NaggroTask.util.Utils;


@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private StatementRepository statementRepository;
	
	
	@Autowired 
	Utils util;

	@Override
	public AccountStatementDTO getAccountStatement(Integer accountId,
			LocalDate fromDate, LocalDate toDate, Double fromAmount,
			Double toAmount) {
		
		boolean isDatePresent = fromDate != null;
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (!account.isPresent()) 
			throw new EntityNotFoundException("Account not found with id:" + accountId);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		List<Statement> statements;
		
		validateRequestParam(fromDate, toDate, fromAmount, toAmount);
		
		AccountStatementDTO accountStatementDTO = new AccountStatementDTO();
		accountStatementDTO.setAccountNumber(util.sha256(account.get().getAccountNumber()));
		accountStatementDTO.setAccountType(account.get().getAccountType());
		List<StatementDTO> statementDTOList = new ArrayList<StatementDTO>();
	
		List<Statement> accountStatements = statementRepository.findByAccountId(accountId);

		if (isDatePresent)
               statements = getStatements(toDate, fromAmount, toAmount, formatter, accountStatements, fromDate);
		else {
			LocalDate userAllowedDate = LocalDate.now().minusMonths(3);
			statements = getStatements(null, fromAmount, toAmount, formatter, accountStatements, userAllowedDate);
             }
		
		statements.forEach(statement -> {
			statementDTOList.add(new StatementDTO(LocalDate.parse(statement.getDateField(), formatter), statement.getAmount()));
		});
				accountStatementDTO.setStatements(statementDTOList);
		return accountStatementDTO;
	}

	private List<Statement> getStatements(LocalDate toDate, Double fromAmount, Double toAmount, DateTimeFormatter formatter, List<Statement> accountStatements, LocalDate userfromDate) {
		List<Statement> statements;
		statements = accountStatements.stream()
		.filter(st -> LocalDate.parse(st.getDateField(), formatter).compareTo(userfromDate) >= 0
				&& LocalDate.parse(st.getDateField(), formatter).compareTo(toDate) <= 0
				&& ( toAmount == null ? true
						: (st.getAmount().compareTo(fromAmount) >= 0
						&& st.getAmount().compareTo(toAmount) <= 0)))
								.collect(Collectors.toList());
		return statements;
	}


	private void validateRequestParam(LocalDate fromDate, LocalDate toDate,
			Double fromAmount, Double toAmount) {
		if(fromDate != null && toDate == null || fromDate == null && toDate != null ) 
			 throw new MissingParamException("fromDate and toDate are required!");
		if(fromDate != null && toDate != null) 	
		       if((fromDate.compareTo(toDate) > 0))
	                throw new InvalidDateRangeException("Invalid date range. 'From Date' is after the 'To Date'.");

		if(fromAmount != null && toAmount == null || fromAmount == null && toAmount != null) 
			 throw new MissingParamException("fromAmount and toAmount are required!!");
			
		if(fromAmount != null && toAmount != null)
		      if (fromAmount.compareTo(toAmount) > 0) 
            throw new InvalidAmountRangeException("Invalid date range. 'From Amount' is greater than 'To Amount'.");
        
	}
	
}