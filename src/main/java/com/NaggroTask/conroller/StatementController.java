package com.NaggroTask.conroller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.NaggroTask.dto.AccountStatementDTO;
import com.NaggroTask.exception.UserCanNotFilterParametersException;
import com.NaggroTask.security.authentication.UserPrinciple;
import com.NaggroTask.service.StatementService;


@RestController
@RequestMapping("/api/findStatement")
public class StatementController {

	@Autowired
	private StatementService statementService;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String findOne() {
        return "Welcome!";
    }

	
	@PreAuthorize("hasRole('ROLE_ADMIN') or " + "hasRole('ROLE_USER')")
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public AccountStatementDTO findStatement(
			@PathVariable("accountId") Integer accountId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @Param("fromDate") LocalDate fromDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @Param("toDate") LocalDate toDate,
			@Param("fromAmount") Double fromAmount,
			@Param("toAmount") Double toAmount) {
		
		UserPrinciple user = (UserPrinciple) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		if (fromDate != null || toDate != null || fromAmount != null
				|| toAmount != null)
		if (!user.getAuthorities().toString().contains("ROLE_ADMIN")) 
			throw new UserCanNotFilterParametersException(user.getUsername());
		return statementService.getAccountStatement(accountId, fromDate,
				toDate, fromAmount, toAmount);
	}
	
}
