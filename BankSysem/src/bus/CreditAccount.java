package bus;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreditAccount extends Account{

	private static final long serialVersionUID = 8320058496241157911L;
	protected LocalDate dueDate;
	protected Double limit;
	
	
	public CreditAccount() {
		
		super();
		this.dueDate = null;
		this.limit = 0.00;
	}

	public CreditAccount(EnumTypeAccount type, Customer customer, Double balance, LocalDate openingDate,
			ArrayList<Transaction> transactions, LocalDate dueDate, Double limit) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate {
		
		super(type, customer, balance, openingDate, transactions);
		setDueDate(dueDate);
		setLimit(limit);
	}
	
	
	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) throws ExceptionIsPassedDate {
		LocalDate now = LocalDate.now();
		if (dueDate.isBefore(now)) {
			throw new ExceptionIsPassedDate();
		}
		this.dueDate = dueDate;
	}

	public Double getLimit() {
		return limit;
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

	@Override
	public void deposit(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionWrongAmount, ExceptionLatePayment, ExceptionIsPassedDate, ExceptionIsNotANumber, ExceptionIsNull {
		
		Double debtValue = this.getBalance();
		
		if (LocalDate.now().isBefore(this.getDueDate()) || LocalDate.now().isEqual(this.getDueDate())) {
				
			Transaction transaction = new Transaction("Deposit", LocalDate.now(), amount, this, EnumTypeTransaction.Credit);	
			
			this.balance += amount;
			
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
			}
			
			this.transactions.add(transaction);
		}
		else 
		{
			Double taxLate = 0.05;
			Double lateFee = taxLate*debtValue*(-1);			
		 		
			Transaction transactionDep = new Transaction("Deposit", LocalDate.now(), amount, this, EnumTypeTransaction.Credit);
			Transaction transactionFees = new Transaction("Fee for late payment", LocalDate.now(), lateFee, this, EnumTypeTransaction.Debit);
			
		 	this.balance += amount;
		 	this.transactions.add(transactionDep);
	       
	        if (this.balance == 0)
	        {
	        	setDueDate(this.getDueDate().plusMonths(1));
	        }
			
	        this.balance -= lateFee;
	        this.transactions.add(transactionFees);
		}
	}

	@Override
	public void withdraw(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionNotEnoughBalance, ExceptionIsNull, ExceptionIsNotANumber {
		
		if (amount <= (this.getBalance() - this.getLimit())) {
			
			Transaction transaction = new Transaction("Withdraw", LocalDate.now(), amount, this, EnumTypeTransaction.Debit);
			
			this.balance -= amount;
			this.transactions.add(transaction);
		}
		else {
			throw new ExceptionNotEnoughBalance("You don't have enough limit!");
		}
	}
	
	@Override
	public String toString() {
		return "CreditAccount " +
			   "\n\tDue Date =" + this.dueDate + 
			   "\n\tLimit =" + this.limit + 
			   "\n\tBalance=" + this.balance;
	}
}
