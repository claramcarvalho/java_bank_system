package bus;
import java.util.Date;

public class CreditAccount extends Account{

	protected Date dueDate;
	
	protected Double limit;
	
	
	public CreditAccount() {
		
		super();
		
		this.dueDate = null;
		this.limit = (double) 0;
	}

	public CreditAccount(Integer accountNumber, EnumTypeAccount type, Double balance, Date openingDate,
			TransactionCollection transactions, Date dueDate, Double limit) {
		
		super(accountNumber, type, balance, openingDate, transactions);
		
		this.dueDate = dueDate;
		this.limit = limit;
	}
	
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) { //day of the month
		this.dueDate = dueDate;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	
	
	@Override
	public String toString() {
		return "CreditAccount " +
			   "\n\tDue Date =" + this.dueDate + 
			   "\n\tLimit =" + this.limit + 
			   "\n\tBalance=" + this.balance;
	}

	@Override
	public void deposit(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {
		
		Date currentDate = new Date();
		
		Double debtValue = getLimit() - getBalance();
		
		if (currentDate.before(getDueDate())) {
				
				this.balance += amount;
				 
				Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
		            this.transactions.add(transaction);
		}
		else 
		{
			
			long daysLate = (currentDate.getTime() - getDueDate().getTime())/(24*60*60*1000);
			Double taxLate = 0.01;
			Double lateFee = taxLate*daysLate*debtValue;			
		 		
		 		this.balance -= lateFee;
				
		 		this.balance += amount;
		 		
		 		Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
	            this.transactions.add(transaction);
		}

		
	}

	@Override
	public void withdraw(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {
		
		if (amount <= getBalance()) {
			
			this.balance -= amount;
			
			Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
            this.transactions.add(transaction);
			
		}
		
	}
	
	
	
	
	
	
}
