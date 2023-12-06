package bus;

import java.time.LocalDate;

public class SavingAccount extends Account {
	
	private double interestRate;	// in percentage
	private double gain;
	private LocalDate dueDate;
	
	public SavingAccount() {
		super();
		this.interestRate = 0.00;
		this.gain = 0.00;
		this.dueDate = null;
	}

	public SavingAccount(EnumTypeAccount type, Customer customer, Double balance, LocalDate openingDate,
			TransactionCollection transactions, double annualInterestRate, LocalDate dueDate) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate {
		super(type, customer, balance, openingDate, transactions);
		setInterestRate(annualInterestRate);
		setDueDate(dueDate);		
		setGain();
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) throws ExceptionIsNotANumber, ExceptionIsNull {
		if (!Validator.isDouble(interestRate)) {
			throw new ExceptionIsNotANumber();
		}
		if (Validator.isNull(interestRate)) {
			throw new ExceptionIsNull();
		}
		this.interestRate = interestRate/100;
	}

	public double getGain() {
		return gain;
	}
	
	public void setGain() {
		this.gain = this.balance * this.interestRate;
	}

	public void calcGain() {
		this.balance += getGain();
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


	@Override
	public void deposit(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionIsNotANumber, ExceptionIsNull {
		
		//if (amount > 0) {
		Transaction transaction = new Transaction("Deposit", transactionDate, amount, 
        		EnumTypeTransaction.Credit);    
        
		this.balance += amount;
		setGain();
		this.transactions.add(transaction);
        //}
		
	}

	@Override
	public void withdraw(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionNotEnoughBalance, ExceptionIsNotANumber, ExceptionIsNull {

		if (transactionDate.isBefore(this.dueDate)) {
			
			if (amount <= this.balance) {
				
			Transaction transaction = new Transaction("Withdraw", transactionDate, amount,
            		EnumTypeTransaction.Debit);
			
			this.balance -= amount;
			setGain();
            this.transactions.add(transaction);
          
			}
			else {
				throw new ExceptionNotEnoughBalance();
			}
		}
		else {
			
			calcGain();
			
			if (amount == this.balance) {
				Transaction transactionInterest = new Transaction("Withdraw", transactionDate, amount,
	            		EnumTypeTransaction.Debit);
				
				this.balance -= amount;
				setGain();
				this.transactions.add(transactionInterest);
			}
		}
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tAnnual Interest Rate: " + interestRate + 
								  "\n\tAnnual Gain: " + gain;
	}

}
