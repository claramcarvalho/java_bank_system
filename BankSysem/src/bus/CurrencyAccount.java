package bus;

import java.util.Date;
import java.time.LocalDate;

public class CurrencyAccount extends Account {
	
	private EnumTypeCurrency currency;
	private double currencyRate;
	private double conversionFees; //percentage fee for each
	
	
	public CurrencyAccount() {
		super();
		this.currency = EnumTypeCurrency.Undefined;
		this.currencyRate = 0.00;
		this.conversionFees = 0.00;
	}
	
	public CurrencyAccount(Integer accountNumber, EnumTypeAccount type, Integer customerNumber, Double balance, LocalDate openingDate,
			TransactionCollection transactions, EnumTypeCurrency currency, double currencyRate, double conversionFees) {
		super(accountNumber, type, customerNumber, balance, openingDate, transactions);
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
	public void deposit(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount {

		//if (amount > 0) {
		
            double convertedAmount = amount * this.currencyRate;
            double conversionFee = convertedAmount * this.conversionFees / 100;

            Transaction transactionDep = new Transaction(null, "Deposit", transactionDate, convertedAmount, EnumTypeTransaction.Credit);
            Transaction transactionFee = new Transaction(null, "Fee for transaction", transactionDate, conversionFee, EnumTypeTransaction.Debit);
            
            this.balance += convertedAmount;
            this.transactions.add(transactionDep);
              
            this.balance -= conversionFee;
            this.transactions.add(transactionFee);
       // }
		
	}

	@Override
	public void withdraw(LocalDate transactionDate, Double amount) throws ExceptionNegativeAmount, ExceptionNotEnoughBalance {

		if (amount <= this.balance) {
			Transaction transaction = new Transaction(null, "Withdraw", transactionDate, amount, EnumTypeTransaction.Debit);
			
			this.balance -= amount;
            this.transactions.add(transaction);
        }
		else {
			throw new ExceptionNotEnoughBalance();
		}
		
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tCurrency: " + currency + "\n\tCurrency Rate: " + currencyRate + 
								  "\n\tConversion Fees: " + conversionFees;
	}

}