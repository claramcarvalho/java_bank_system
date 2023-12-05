package bus;

import java.util.Date;

public class CurrencyAccount extends Account {
	
	private EnumTypeCurrency currency;
	private double currencyRate;
	private double conversionFees;
	
	
	public CurrencyAccount() {
		super();
		this.currency = EnumTypeCurrency.Undefined;
		this.currencyRate = 0.00;
		this.conversionFees = 0.00;
	}
	
	public CurrencyAccount(Integer accountNumber, EnumTypeAccount type, Double balance, Date openingDate,
			TransactionCollection transactions, EnumTypeCurrency currency, double currencyRate, double conversionFees) {
		super(accountNumber, type, balance, openingDate, transactions);
		this.currency = currency;
		this.currencyRate = currencyRate;
		this.conversionFees = conversionFees;
	}


	public EnumTypeCurrency getCurrency() {
		return currency;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public double getConversionFees() {
		return conversionFees;
	}

	public void setConversionFees(double conversionFees) {
		this.conversionFees = conversionFees;
	}

	@Override
	public void deposit(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {

		if (amount > 0) {
            double convertedAmount = amount * this.currencyRate;

            this.balance += convertedAmount;

            this.balance -= conversionFees;

            Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
            this.transactions.add(transaction);
        }
		
	}

	@Override
	public void withdraw(Integer transactionNumber, String description, Date transactionDate, Double amount,
			EnumTypeTransaction type) {

		if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;

            this.balance -= conversionFees;

            Transaction transaction = new Transaction(transactionNumber, description, transactionDate, amount, type);
            this.transactions.add(transaction);
        }
		
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tCurrency: " + currency + "\n\tCurrency Rate: " + currencyRate + 
								  "\n\tConversion Fees: " + conversionFees;
	}

}