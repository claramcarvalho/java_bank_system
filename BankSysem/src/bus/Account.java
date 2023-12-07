package bus;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Account implements ITransaction, Serializable {
	private static final long serialVersionUID = 5787217863065268367L;
	protected static Integer counter = 1;
	protected Integer accountNumber;
	protected EnumTypeAccount type;
	protected Customer customer;
	protected Double balance;
	protected LocalDate openingDate;
	protected TransactionCollection transactions;
	
	public Account() {
		super();
		this.accountNumber = counter++;
		this.type = EnumTypeAccount.Undefined;
		this.customer = null;
		this.balance = (double) 0;
		this.openingDate = null;
		this.transactions = new TransactionCollection();
	}
	
	public Account(EnumTypeAccount type, Customer customer, Double balance, LocalDate openingDate,
			TransactionCollection transactions) throws ExceptionIsNull, ExceptionIsNotANumber {
		super();
		this.accountNumber = counter++;
		this.type = type;
		setCustomer(customer);
		setBalance(balance);
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

	public void setBalance(Double balance) throws ExceptionIsNull, ExceptionIsNotANumber {
		if(Validator.isNull(balance)) {
			throw new ExceptionIsNull();
		}
		if(!Validator.isDouble(balance)) {
			throw new ExceptionIsNotANumber();
		}
		this.balance = balance;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) throws ExceptionIsNull, ExceptionIsNotANumber {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Account Number: " + this.accountNumber +
				"\n\tType: " + this.type + 
				"\n\tCustomer: " + this.customer.getUserName() + 
				"\n\tDate of Opening : " + this.openingDate +
				"\n\tBalance : " + this.balance;
	}
	
	public static Account searchById(Integer id) throws ClassNotFoundException, IOException {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = FileManagerAccounts.deserialize();

		for(Account item : accounts) {
			if (item.getAccountNumber().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	public static Account searchById(Integer id, EnumTypeAccount type) throws ClassNotFoundException, IOException {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = FileManagerAccounts.deserialize();
		
		for(Account item : accounts) {
			if (item.getAccountNumber().equals(id) && item.getType().equals(type)) {
				return item;
			}
		}
		return null;
	}
	
	
	
	//ABSTRACT METHODS - NOT IMPLEMENTED IN PARENT CLASS	
	public abstract void deposit (LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionWrongAmount, ExceptionLatePayment, ExceptionIsPassedDate, ExceptionIsNotANumber, ExceptionIsNull;
	public abstract void withdraw (LocalDate transactionDate, Double amount) throws ExceptionNotEnoughBalance, ExceptionNegativeAmount, ExceptionIsNull, ExceptionIsNotANumber;
	
}
