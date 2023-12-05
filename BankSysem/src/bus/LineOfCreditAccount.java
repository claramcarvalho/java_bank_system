package bus;

import java.util.Date;

public class LineOfCreditAccount extends CreditAccount{

	protected Double interestRate;
	protected int installments;
	
	public LineOfCreditAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LineOfCreditAccount(Integer accountNumber, EnumTypeAccount type, Integer customerNumber, Double balance, Date openingDate,
			TransactionCollection transactions, Date dueDate, Double limit, Double interestRate, int installments) {
		
		super(accountNumber, type, customerNumber, balance, openingDate, transactions, dueDate, limit);
		this.interestRate = interestRate;
		this.installments = installments;
		
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public int getInstallments() {
		return installments;
	}

	
	
	@Override
	public void withdraw(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {
		
		if (amount <= getLimit()) {
			
			Double finalDebt = amount*(1+ getInterestRate());
			
			this.setBalance(finalDebt*-1);
			
			Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
	        this.transactions.add(transaction);
		}
		else {
			
			Double finalDebt = getLimit()*(1+ getInterestRate());
			
			this.setBalance(finalDebt*-1);
			
			Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
	        this.transactions.add(transaction);

		}
		
	}
	
	
	@Override
	public void deposit(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {
		
		this.balance += amount;
		
		this.installments--;
		
		Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
        this.transactions.add(transaction);
	}

	@Override
	public String toString() {
		return "LineOfCreditAccount "+
			   "InterestRate = " + interestRate + 
			   "Installments = " + installments + 
			   "Limit = " + limit + 
			   "Balance = " + balance;
	}
	
	
	
	
	
	
}
