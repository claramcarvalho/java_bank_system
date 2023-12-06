package bus;

import java.util.Date;
import java.time.LocalDate;

public abstract class Account implements ITransaction {
	protected Integer accountNumber;
	protected EnumTypeAccount type;
	protected Integer customerNumber;
	protected Double balance;
	protected LocalDate openingDate;
	protected TransactionCollection transactions;
	
	public Account() {
		super();
		this.accountNumber = 0;
		this.type = EnumTypeAccount.Undefined;
		this.customerNumber = null;
		this.balance = (double) 0;
		this.openingDate = null;
		this.transactions = new TransactionCollection();
	}
	
	public Account(Integer accountNumber, EnumTypeAccount type, Integer customerNumber, Double balance, LocalDate openingDate,
			TransactionCollection transactions) {
		super();
		this.accountNumber = accountNumber;
		this.type = type;
		this.customerNumber = customerNumber;
		this.balance = balance;
		this.openingDate = openingDate;
		this.transactions = transactions;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public EnumTypeAccount getType() {
		return type;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public Integer getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Integer customerNumber) {
		this.customerNumber = customerNumber;
	}

	@Override
	public String toString() {
		return "Account Number: " + this.accountNumber +
				"\n\tType: " + this.type + 
				"\n\tDate of Opening : " + this.openingDate +
				"\n\tBalance : " + this.balance;
	}
	
	//ABSTRACT METHODS - NOT IMPLEMENTED IN PARENT CLASS	
	public abstract void deposit (LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionWrongAmount, ExceptionLatePayment;
	public abstract void withdraw (LocalDate transactionDate, Double amount) throws ExceptionNotEnoughBalance, ExceptionNegativeAmount;
	
}
