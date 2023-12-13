package bus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Manager extends User {

	private static final long serialVersionUID = 1549592684555251124L;
	private ArrayList<Customer> listOfCustomers;

	public Manager() {
		super();
		this.listOfCustomers = null;
	}
	
	public Manager(String userName, Integer password, ArrayList<Customer> listOfCustomers) {
		super(userName, password);
		this.listOfCustomers = listOfCustomers;
	}

	public ArrayList<Customer> getListOfCustomers() {
		return listOfCustomers;
	}
	
	public Account openSavingAccount(Customer customer, Double balance, Double interestRate, LocalDate dueDate) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ClassNotFoundException, IOException {
		Account newAccount = new SavingAccount(EnumTypeAccount.SavingAccount, customer, balance, LocalDate.now(), new ArrayList<Transaction>(), interestRate, dueDate);
		
		customer.addNewAccount(newAccount);
		FileManagerCustomers.saveNewCustomer(customer);
		
		return newAccount;
	}
	
	public Account openCheckingAccount(Customer customer, Double balance, Integer monthlyTransactionLimit, Double transactionFees) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ClassNotFoundException, IOException {
		Account newAccount = new CheckingAccount(EnumTypeAccount.CheckingAccount, customer, balance, LocalDate.now(), 
				new ArrayList<Transaction>(), monthlyTransactionLimit, transactionFees);
		
		customer.addNewAccount(newAccount);
		FileManagerCustomers.saveNewCustomer(customer);
		
		return newAccount;
	}
	
	public Account openCurrencyAccount(Customer customer, Double balance, EnumTypeCurrency currency, Double currencyRate, Double conversionFees) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ClassNotFoundException, IOException {
		Account newAccount = new CurrencyAccount(EnumTypeAccount.CurrencyAccount, customer, balance, LocalDate.now(), 
				new ArrayList<Transaction>(), currency, currencyRate, conversionFees);
		
		customer.addNewAccount(newAccount);
		FileManagerCustomers.saveNewCustomer(customer);
		
		return newAccount;
	}
	
	public Account openCreditAccount(Customer customer, Double balance, LocalDate dueDate, Double limit) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ClassNotFoundException, IOException {
		Account newAccount = new CreditAccount(EnumTypeAccount.CreditAccount, customer, balance, LocalDate.now(), 
				new ArrayList<Transaction>(), dueDate, limit);
		
		customer.addNewAccount(newAccount);
		FileManagerCustomers.saveNewCustomer(customer);
		
		return newAccount;
	}
	
	public Account openLineOfCreditAccount(Customer customer, LocalDate dueDate, Double limit, Double interestRate) throws ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, ExceptionNegativeAmount, ClassNotFoundException, IOException {
		Account newAccount = new LineOfCreditAccount(EnumTypeAccount.LineOfCreditAccount, customer, LocalDate.now(), 
				new ArrayList<Transaction>(), dueDate, limit, interestRate);
		
		customer.addNewAccount(newAccount);
		FileManagerCustomers.saveNewCustomer(customer);
		
		return newAccount;
	}


	public Boolean closeAccount(Customer customer, Integer accountNumber) throws ClassNotFoundException, IOException {
		if (customer != null && accountNumber != null) {
			
			customer.removeAccount(accountNumber);			
			FileManagerCustomers.saveNewCustomer(customer);
			return true;
		}
		return false;
	}
	
	public Customer createCustomer(String username, int password, double salary, Manager mgr) throws ExceptionIsNotANumber, ExceptionIsNull, ClassNotFoundException, IOException {
		Customer newCustomer = new Customer(username, password, salary, mgr, new ArrayList<Account>());
		this.listOfCustomers.add(newCustomer);
		
		return newCustomer;
		
	}
	
	public Boolean removeCustomer(Customer customer) throws IOException, ClassNotFoundException {
        if (customer != null) {
        	
        	for (Customer customerList : this.listOfCustomers) {
        		if (customerList.getIdentificationNumber().equals(customer.getIdentificationNumber())) {
        			this.listOfCustomers.remove(customerList);
        			break;
        		}
        	}
    		
    		FileManagerCustomers.serialize(this.listOfCustomers);
            return true;
        }
        return false;
	}

	@Override
	public String toString() {
		return super.toString() + "\nManager List of customers: \n" + listOfCustomers;
	}
	
}
