package com.NaggroTask.dto;

import java.time.LocalDate;

public class StatementDTO {
	
	private LocalDate date;
	private Double amount;

	public StatementDTO(LocalDate date, Double amount) {
		super();
		this.date = date;
		this.amount = amount;
	}

	public StatementDTO() {
		super();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}