package com.example.models;

import java.util.List;

public class AccountModel extends Response {
	private String name;
	private String accountType;
	private String account;
	private String balance;
	private String dateOfOpen;
	List<Trans> transactions;
	private String result;
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDateOfOpen() {
		return dateOfOpen;
	}
	public void setDateOfOpen(String dateOfOpen) {
		this.dateOfOpen = dateOfOpen;
	}
	public List<Trans> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Trans> transactions) {
		this.transactions = transactions;
	}
	

}
