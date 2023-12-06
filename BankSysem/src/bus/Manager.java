package bus;

import java.util.ArrayList;

public class Manager extends User {
	
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

	public void openAccount(Customer customer, EnumTypeAccount type) {
		
		if (customer != null && type != null) {
			
			Account newAccount = null;
			
			if (type == EnumTypeAccount.SavingAccount) {
				newAccount = new SavingAccount();
			}
			else if (type == EnumTypeAccount.CheckingAccount) {
				newAccount = new CheckingAccount();
			}
			else if (type == EnumTypeAccount.CurrencyAccount) {
				newAccount = new CurrencyAccount();
			}
			else if (type == EnumTypeAccount.CreditAccount) {
				
			}
			else if (type == EnumTypeAccount.LineOfCreditAccount) {
				
			}

			customer.addNewAccount(newAccount);
		}
		
	}
	
	public void closeAccount(Customer customer, Integer accountNumber) {
		if (customer != null && accountNumber != null) {
			
			 ArrayList<Account> customerAccounts = customer.getListOfAccounts();
			 
			 for (Account account : customerAccounts) {
	                if (account.getAccountNumber() == accountNumber) {
	                    customerAccounts.remove(account);
	                    break;
	                }
	            }
			 
		}
	}
	
	public void createCustomer(String username, int password, double salary, Manager mgr) throws ExceptionIsNotANumber, ExceptionIsNull {
		Customer newCustomer = new Customer(username, password, salary, mgr, new ArrayList<Account>());
		this.listOfCustomers.add(newCustomer);
		
	}
	
	public void removeCustomer(Customer customer) {
        if (customer != null) {
            this.listOfCustomers.remove(customer);
            // REMOVER TAMBEM DO BANCO DE DADOS
        }
	}

	@Override
	public String toString() {
		return super.toString() + "\nManager List of customers: \n" + listOfCustomers;
	}
	
}
