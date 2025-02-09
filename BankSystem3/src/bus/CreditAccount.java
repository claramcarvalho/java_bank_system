package bus;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import data.AccountDB;
import data.CreditAccountDB;

public class CreditAccount extends Account{

	private Integer accountNumber;
	protected LocalDate dueDate;
	protected Double limit;
	
	
	public CreditAccount() {
		super();
		this.accountNumber = null;
		this.dueDate = null;
		this.limit = 0.00;
	}

	public CreditAccount(Integer accountNumber, EnumTypeAccount type, Integer customer, Double balance, LocalDate openingDate,
			LocalDate dueDate, Double limit) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate {
		
		super(accountNumber, type, customer, balance, openingDate);
		setAccountNumber(accountNumber);
		setDueDate(dueDate);
		setLimit(limit);
	}
	
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getAccountNumber() {
		return accountNumber;
	}
	
	public void setDueDate(LocalDate dueDate) throws ExceptionIsPassedDate {
//		LocalDate now = LocalDate.now();
//		if (dueDate.isBefore(now)) {
//			throw new ExceptionIsPassedDate();
//		}
		this.dueDate = dueDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setLimit(Double limit) throws ExceptionIsNull, ExceptionIsNotANumber {
		if (Validator.isNull(limit)) {
			throw new ExceptionIsNull();
		}
		
		if (!Validator.isDouble(limit)) {
			throw new ExceptionIsNotANumber();
		}
		this.limit = limit;
	}
	public Double getLimit() {
		return limit;
	}

	@Override
	public void deposit(Double amount) throws ExceptionNegativeAmount, ExceptionWrongAmount, ExceptionLatePayment, ExceptionIsPassedDate, ExceptionIsNotANumber, ExceptionIsNull, SQLException {
				
		Double debtValue = this.getBalance();
		
		if (LocalDate.now().isBefore(this.getDueDate()) || LocalDate.now().isEqual(this.getDueDate())) {
				
			Transaction transaction = new Transaction(null, "Deposit", LocalDate.now(), amount, this.accountNumber, EnumTypeTransaction.Credit);	
			
			this.balance += amount;
			Account.update(this);
			
			if (this.balance==0)
			{
				//calculating the new due date - same day of month ALWAYS
				// add one month based on payment day.
				LocalDate originalDueDate = this.getDueDate();
				int originalDueDateDay = originalDueDate.getDayOfMonth();
				
				LocalDate paymentDay = LocalDate.now();
				LocalDate paymentDayPlusOneMonth = paymentDay.plusMonths(1);
				
				LocalDate newDueDate = LocalDate.of(paymentDayPlusOneMonth.getYear(),
						paymentDayPlusOneMonth.getMonthValue(),
						originalDueDateDay);
				
				setDueDate(newDueDate);
				CreditAccount.updateDueDate(this);
			}
			
			Transaction.add(transaction);
			this.setTransactions();
		}
		else 
		{
			Double taxLate = 0.05;
			Double lateFee = taxLate*debtValue*(-1);			
		 		
			Transaction transactionDep = new Transaction(null, "Deposit", LocalDate.now(), amount, this.accountNumber, EnumTypeTransaction.Credit);
			Transaction transactionFees = new Transaction(null, "Fee for late payment", LocalDate.now(), lateFee, this.accountNumber, EnumTypeTransaction.Debit);
			
		 	this.balance += amount;
		 	Account.update(this);
	        Transaction.add(transactionDep);
	        this.setTransactions();
	       
	        if (this.balance == 0)
	        {
	        	setDueDate(this.getDueDate().plusMonths(1));
				CreditAccount.updateDueDate(this);
	        }
			
	        this.balance -= lateFee;
	        Account.update(this);
	        Transaction.add(transactionFees);
	        this.setTransactions();
		}
	}

	@Override
	public void withdraw(Double amount) throws ExceptionNegativeAmount, ExceptionNotEnoughBalance, ExceptionIsNull, ExceptionIsNotANumber, SQLException {
		
		if (amount <= (this.getBalance() - this.getLimit())) {
			
			Transaction transaction = new Transaction(null, "Withdraw", LocalDate.now(), amount, this.accountNumber, EnumTypeTransaction.Debit);
			
			this.balance -= amount;
			Account.update(this);
			Transaction.add(transaction);
			this.setTransactions();
		}
		else {
			throw new ExceptionNotEnoughBalance("You don't have enough limit!");
		}
	}
	
	@Override
	public String toString() {
		return "\n\tDue Date: " + this.dueDate + 
			   "\n\tLimit: " + this.limit + 
			   "\n\tBalance: " + this.balance;
	}
	
	//////////////////////////////
	//   public static services //
	//////////////////////////////
	public static void add(CreditAccount element) throws SQLException {
		CreditAccountDB.insert(element);
	}
	
	public static void updateLimit(CreditAccount element) throws SQLException {
		CreditAccountDB.updateLimit(element);
	}
	
	public static void updateDueDate(CreditAccount element) throws SQLException {
		CreditAccountDB.updateDueDate(element);
	}
	
	public static void remove(Integer id) throws SQLException {
		AccountDB.delete(id);
	}
	
	public static CreditAccount search(Integer id) throws SQLException, ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ExceptionNegativeAmount, ExceptionNotEnoughBalance {
		return CreditAccountDB.search(id);
	}
	
	public static ArrayList<CreditAccount> searchByCustomer(Integer customer) throws SQLException, ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ExceptionNegativeAmount, ExceptionNotEnoughBalance {
		return CreditAccountDB.searchByCustomer(customer);
	}
	
	public static CreditAccount searchByIdAndCustomer(Integer id, Integer customer) throws SQLException, ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ExceptionNegativeAmount, ExceptionNotEnoughBalance {
		return CreditAccountDB.searchByIdAndCustomer(id, customer);
	}
	
	public static ArrayList<CreditAccount> getData() throws SQLException, ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate {
		return CreditAccountDB.select();
	}
}
