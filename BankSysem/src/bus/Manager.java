package bus;

import java.util.ArrayList;

public class Manager extends User {
	
	private ArrayList<Customer> listOfCustomers;
<<<<<<< HEAD
	
=======

	public Manager() {
		super();
		this.listOfCustomers = null;
	}
	
	public Manager(Integer identificationNumber, String userName, Integer password, ArrayList<Customer> listOfCustomers) {
		super(identificationNumber, userName, password);
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
			/*
			 * COMPLETAR COM OUTRAS ACCOUNTS
			 * 
			 * ADICIONAR PARAMETROS NAS ACCOUNTS
			 */
			
			
			customer.addNewAccount(newAccount);		//ALTERADO CLASSE CUSTOMER MÉTODO ADDNEWACCOUNT
		}
		
	}
	
	public void closeAccount(Customer customer, EnumTypeAccount type) {
		if (customer != null && type != null) {
			
			 ArrayList<Account> customerAccounts = customer.getListOfAccounts();
			 
			 for (Account account : customerAccounts) {
	                if (account.getType() == type) {
	                    customerAccounts.remove(account);
	                    break;
	                }
	            }
			 
		}
	}
	
	public void createCustomer(int idNumber, String username, int password, double salary, int mgrId) {
		Customer newCustomer = new Customer(idNumber, username, password, salary, mgrId, null);
		listOfCustomers.add(newCustomer);
		
	}
	
	public void removeCustomer(Customer customer) {
        if (customer != null) {
            listOfCustomers.remove(customer);
            // REMOVER TAMBEM DO BANCO DE DADOS
        }
	}

	@Override
	public String toString() {
		return super.toString() + "\nManager List of customers: \n" + listOfCustomers;
	}

>>>>>>> 86314dd123e80284c74340e6bd907fa9b1d1db20
	
	
}
