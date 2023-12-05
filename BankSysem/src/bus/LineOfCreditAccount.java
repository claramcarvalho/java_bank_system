package bus;

import java.util.Date;

public class LineOfCreditAccount extends CreditAccount{

	protected Double interestRate;
	protected int installments;
	
	public LineOfCreditAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LineOfCreditAccount(Integer accountNumber, EnumTypeAccount type, Double balance, Date openingDate,
			TransactionCollection transactions, Date dueDate, Double limit, Double interestRate, int installments) {
		
		super(accountNumber, type, balance, openingDate, transactions, dueDate, limit);
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
	public void withdraw(Double amount) { //amount to withdraw
		
		if (amount <= getLimit()) {
			
			Double finalDebt = amount*(1+ getInterestRate());
			
			this.setBalance(finalDebt*-1);
		}
		else {
			
			Double finalDebt = getLimit()*(1+ getInterestRate());
			
			this.setBalance(finalDebt*-1);

		}
		
	}
	
	
	@Override
	public void deposit(Double amount) {
		
		this.balance += amount;
		
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
