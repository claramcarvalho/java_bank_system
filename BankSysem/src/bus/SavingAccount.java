package bus;

import java.util.Date;

public class SavingAccount extends Account {
	
	private double annualInterestRate;
	private double annualGain;
	
	public SavingAccount() {
		super();
		this.annualInterestRate = 0.00;
		this.annualGain = 0.00;
	}

	public SavingAccount(Integer accountNumber, EnumTypeAccount type, Double balance, Date openingDate,
			TransactionCollection transactions, double annualInterestRate, double annualGain) {
		super(accountNumber, type, balance, openingDate, transactions);
		this.annualInterestRate = annualInterestRate;
		this.annualGain = annualGain;
	}

	
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public double getAnnualGain() {
		return annualGain;
	}

	public void calcAnnualGain(double annualGain) {
		this.annualGain = this.balance * this.annualInterestRate;
		this.balance += annualGain;
	}

	@Override
	public void deposit(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {			// ALTERATE ITRANSACTION TO SATISFY CONSTRUCTOR OF TRANSACTION
		
		if (amount > 0) {
            
            this.balance += amount;

            Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, 
            		type);
            this.transactions.add(transaction);
        }
		
	}

	@Override
	public void withdraw(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {

		if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;

            Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount,
            		type);
            this.transactions.add(transaction);
        }
		
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tAnnual Interest Rate: " + annualInterestRate + 
								  "\n\tAnnual Gain: " + annualGain;
	}

}
