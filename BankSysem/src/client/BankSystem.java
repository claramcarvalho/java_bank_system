package client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import bus.*;

public class BankSystem {
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, ExceptionIsNotANumber, ExceptionIsNull {
		
		
		System.out.println("\t\t\n WELCOME TO FORTIS BANK\n");
		
		ArrayList<Manager> listOfManagerFromConsole = new ArrayList<Manager>();
		ArrayList<Manager> listOfManagerFromFile = new ArrayList<Manager>();
		
		Manager manager1 = new Manager("John", 1234, new ArrayList<Customer>());
		Manager manager2 = new Manager("Maria", 1234, new ArrayList<Customer>());
		
		listOfManagerFromConsole.add(manager1);
		listOfManagerFromConsole.add(manager2);
		
		FileManagerManagers.serialize(listOfManagerFromConsole);
		listOfManagerFromFile = FileManagerManagers.deserialize();
		
		for(Manager item : listOfManagerFromFile) {
			System.out.println(item.toString());
		}
		

//		ArrayList<Customer> listOfCustomerFromConsole = new ArrayList<Customer>();
//		ArrayList<Customer> listOfCustomerFromFile = new ArrayList<Customer>();
//		
//		Customer customer1 = new Customer("John", 1234, 12.3, manager1, new ArrayList<Account>());
//		Customer customer2 = new Customer("Maria", 1234, 14.3, manager2, new ArrayList<Account>());
//		
//		listOfCustomerFromConsole.add(customer1);
//		listOfCustomerFromConsole.add(customer2);
//		
//		FileManagerCustomers.serialize(listOfCustomerFromConsole);
//		listOfCustomerFromFile = FileManagerCustomers.deserialize();
//		
//		for(Customer item : listOfCustomerFromFile) {
//			System.out.println(item.toString());
//		}
//		
		
		Boolean auth = true;
		int typeUser;
		
		do {
			System.out.println("\nChoose how do you want to login: ");
			System.out.println("1 - Manager");
			System.out.println("2 - Customer");
			System.out.println("3 - Exit");
			
			try {
				typeUser = Integer.parseInt(scan.nextLine());

				switch(typeUser) {
				case 1:
					loginAsManager();
					break;
				case 2:
					loginAsCustomer();
					break;
				case 3:
					auth = false;
					break;
				default:
					System.out.println("Main menu: Please enter a valid option");
					break;
				}
			}
			catch (Exception e){
				System.out.println("Main menu: Please enter a valid option");
			}
			
		
		}
		while(auth);
	}

	private static void loginAsManager() {
		String username; int password; Boolean logged = false;
		Manager manager = null;
		System.out.println("\nEnter your username: ");
		username = scan.nextLine();
		
		System.out.println("\nEnter your password: "); 
		try {
			password = Integer.parseInt(scan.nextLine());
			ArrayList<Manager> listOfManagerFromFile = FileManagerManagers.deserialize();
			
			for(Manager item : listOfManagerFromFile) {
				if (item.getUserName().equals(username) && item.getPassword() == password) {
					logged = true;
					manager = item;
					System.out.println("\nWelcome " + item.getUserName());
					break;
				} 
				
			}
			
			if (logged) {
				menuManager(manager);
			}
		}
		catch (Exception e) {
			System.out.println("Invalid credencials");
		}
	}

	private static void menuManager(Manager manager) {
		Boolean auth = true;
		int option;
		do {
			System.out.println("\nChoose the operation you want to do: ");
			System.out.println("1 - Open an account");
			System.out.println("2 - Close an account");
			System.out.println("3 - Create a customer");
			System.out.println("4 - Remove a customer");
			System.out.println("5 - View all customers");
			System.out.println("6 - View all accounts");
			System.out.println("7 - Exit");
			
			try {
				option = Integer.parseInt(scan.nextLine());

				switch(option) {
				case 1:
					openAccount(manager);
					break;
				case 2:
					closeAccount(manager);
					break;
				case 3:
					createCustomer(manager);
					break;
				case 4:
					removeCustomer(manager);
					break;
				case 5:
					displayAllCustomers();
					break;
					
				case 6:
					displayAllAccounts();
					break;
				case 7:
					auth = false;
					break;
				default:
					System.out.println("Default Manager menu: Please enter a valid option");
					break;
				}
			}
			catch (Exception e){
				System.out.println("Manager menu: Please enter a valid option");
			}
		}
		while(auth);
		
	}
	
	private static void displayAllCustomers() throws ClassNotFoundException, IOException {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers = FileManagerCustomers.deserialize();
		
		for(Customer item : customers) {
			System.out.println(item.toString());
		}
		
	}	
	
	private static void displayAllAccounts() throws ClassNotFoundException, IOException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = FileManagerAccounts.deserialize();
		
		for(Account item : accounts) {
			System.out.println(item.toString());
		}
		
	}

	private static void createCustomer(Manager manager) throws ExceptionIsNotANumber, ExceptionIsNull, ExceptionIsPassedDate, ClassNotFoundException, IOException {
		String username, createCustomer; int password, monthlyLimit; double salary, balance, transactionFees; 
		
		do {
			System.out.println("\nDo you want to enter a new customer? (Y/N) ");
			createCustomer = scan.nextLine();
			
			if (createCustomer.equals("Y")) {
				System.out.println("\nEnter the informations of the customer: ");
				System.out.println("\nUsername: ");
				username = scan.nextLine();
				System.out.println("\nPassword: ");
				password = Integer.parseInt(scan.nextLine());
				System.out.println("\nSalary: ");
				salary = Double.parseDouble(scan.nextLine());
				
				Customer newCustomer = manager.createCustomer(username, password, salary, manager);
				
				System.out.println("\nEnter the informations to open a checking account: ");
				System.out.println("\nBalance: ");
				balance = Double.parseDouble(scan.nextLine());
				System.out.println("\nMonthly Transaction Limit: ");
				monthlyLimit = Integer.parseInt(scan.nextLine());
				System.out.println("\nTransaction Fees: ");
				transactionFees = Double.parseDouble(scan.nextLine());
				
				manager.openCheckingAccount(newCustomer, balance, monthlyLimit, transactionFees);
			}
			
		}
		while(createCustomer.equals("Y"));
		
		
	}

	private static void openAccount(Manager manager) throws ClassNotFoundException, ExceptionIsNull, ExceptionIsNotANumber, ExceptionIsPassedDate, IOException, ExceptionNegativeAmount {
		String chooseType, createAccount; 
		
		do {
			System.out.println("\nDo you want to open a new account? (Y/N) ");
			createAccount = scan.nextLine();
			
			if (createAccount.equals("Y")) {
				System.out.println("What is the type of the account? ");
				System.out.println("1 - Checking Account");
				System.out.println("2 - Saving Account");
				System.out.println("3 - Credit Account");
				System.out.println("4 - Currency Account");
				System.out.println("5 - Line of Credit Account");
				chooseType = scan.nextLine();
				Integer id = null, monthlyLimit = null, day = null, month = null, year = null; 
				Customer customer = null;
				Double balance = null, transactionFees = null, interestRate = null, limit = null,
						currencyRate = null, conversionFees = null;
				LocalDate dueDate = null;
				EnumTypeCurrency currency = EnumTypeCurrency.Undefined;
				String chooseCurrency = null;
				
				switch (chooseType) {
				 case "1":
					 System.out.println("Enter the identification number of the customer: ");
					 id = Integer.parseInt(scan.nextLine());
					 customer = Customer.searchById(id);
					 if (customer != null) {
						 System.out.println("\nBalance: ");
						 balance = Double.parseDouble(scan.nextLine());
						 System.out.println("\nMonthly Transaction Limit: ");
						 monthlyLimit = Integer.parseInt(scan.nextLine());
						 System.out.println("\nTransaction Fees: ");
						 transactionFees = Double.parseDouble(scan.nextLine());
						 
						 manager.openCheckingAccount(customer, balance, monthlyLimit, transactionFees);

					 }
					 else {
						 System.out.println("Invalid customer ID");
					 }
					 
					 break;
					 
				 case "2":
					 System.out.println("Enter the identification number of the customer: ");
					 id = Integer.parseInt(scan.nextLine());
					 customer = Customer.searchById(id);
					 if (customer != null) {
						 System.out.println("\nBalance: ");
						 balance = Double.parseDouble(scan.nextLine());
						 System.out.println("\nEnter the interest Rate: ");
						 interestRate = Double.parseDouble(scan.nextLine());
						 System.out.println("\nEnter the date to withdraw with interest income: ");
						 System.out.println("\nDay: ");
						 day = Integer.parseInt(scan.nextLine());
						 System.out.println("\nMonth: ");
						 month = Integer.parseInt(scan.nextLine());
						 System.out.println("\nYear: ");
						 year = Integer.parseInt(scan.nextLine());
						 dueDate = LocalDate.of(year, month, day);
						 
						 manager.openSavingAccount(customer, balance, interestRate, dueDate);

					 }
					 else {
						 System.out.println("Invalid customer ID");
					 }
					 break;
					 
				 case "3":
					 System.out.println("Enter the identification number of the customer: ");
					 id = Integer.parseInt(scan.nextLine());
					 customer = Customer.searchById(id);
					 if (customer != null) {
						 System.out.println("\nBalance: ");
						 balance = Double.parseDouble(scan.nextLine());
						 System.out.println("\nEnter the due date: ");
						 System.out.println("\nDay: ");
						 day = Integer.parseInt(scan.nextLine());
						 System.out.println("\nMonth: ");
						 month = Integer.parseInt(scan.nextLine());
						 System.out.println("\nYear: ");
						 year = Integer.parseInt(scan.nextLine());
						 dueDate = LocalDate.of(year, month, day);
						 System.out.println("\nEnter the limit of credit: ");
						 limit = Double.parseDouble(scan.nextLine());
						 
						 manager.openCreditAccount(customer, balance, dueDate, limit);

					 }
					 else {
						 System.out.println("Invalid customer ID");
					 }
					 break;
					 
				 case "4":
					 System.out.println("Enter the identification number of the customer: ");
					 id = Integer.parseInt(scan.nextLine());
					 customer = Customer.searchById(id);
					 if (customer != null) {
						 System.out.println("\nBalance: ");
						 balance = Double.parseDouble(scan.nextLine());
						 System.out.println("\nChoose the currency: ");
						 System.out.println("\n1 - Real ");
						 System.out.println("\n2 - USA Dolar ");
						 System.out.println("\n3 - Euro ");
						 chooseCurrency = scan.nextLine();
						 switch(chooseCurrency) {
						 case "1":
							 currency = EnumTypeCurrency.Real;
							 break;
						 case "2":
							 currency = EnumTypeCurrency.USADollar;
							 break;
						 case "3":
							 currency = EnumTypeCurrency.Euro;
							 break;
						 default:
							 System.out.println("Invalid currency option");
							 break;
						 }
						 System.out.println("\nEnter the currency rate: ");
						 currencyRate = Double.parseDouble(scan.nextLine());
						 System.out.println("\nEnter the conversion fees: ");
						 conversionFees = Double.parseDouble(scan.nextLine());
						 
						 manager.openCurrencyAccount(customer, balance, currency, currencyRate, conversionFees);
					 }
					 else {
						 System.out.println("Invalid customer ID");
					 }
					 break;
					 
				 case "5":
					 System.out.println("Enter the identification number of the customer: ");
					 id = Integer.parseInt(scan.nextLine());
					 customer = Customer.searchById(id);
					 if (customer != null) {
						 System.out.println("\nEnter the due date: ");
						 System.out.println("\nDay: ");
						 day = Integer.parseInt(scan.nextLine());
						 System.out.println("\nMonth: ");
						 month = Integer.parseInt(scan.nextLine());
						 System.out.println("\nYear: ");
						 year = Integer.parseInt(scan.nextLine());
						 dueDate = LocalDate.of(year, month, day);
						 System.out.println("\nEnter the limit: ");
						 limit = Double.parseDouble(scan.nextLine());
						 System.out.println("\nEnter the interest rate: ");
						 interestRate = Double.parseDouble(scan.nextLine());
						 
						 manager.openLineOfCreditAccount(customer, dueDate, limit, interestRate);

					 }
					 else {
						 System.out.println("Invalid customer ID");
					 }
					 break;
					 
				 default:
					 System.out.println("Invalid account type option");
			
				}
				
			}
			
		}
		while(createAccount.equals("Y"));
		
	}

	private static void closeAccount(Manager manager) throws ClassNotFoundException, IOException {
		String removeAccount; 
		do {
			System.out.println("\nDo you want to remove an account? (Y/N) ");
			removeAccount = scan.nextLine();
			
			Integer idCustomer = null, idAccount = null; Customer customer = null;
			
			if (removeAccount.equals("Y")) {
				System.out.println("\nEnter the customer identification number: ");
				idCustomer = Integer.parseInt(scan.nextLine());
				customer = Customer.searchById(idCustomer);
				if (customer != null) {
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					Boolean closedAccount = manager.closeAccount(customer, idAccount);
					
					if (closedAccount) {
						System.out.println("Account removed");
					}
					else {
						System.out.println("Account can not be removed, try again");
					}
				} else {
					System.out.println("\nInvalid customer ID");
				}
			}
		}
		while(removeAccount.equals("Y"));
		
	}

	private static void removeCustomer(Manager manager) throws ClassNotFoundException, IOException {
		String removeCustomer; 
		do {
			System.out.println("\nDo you want to remove a customer? (Y/N) ");
			removeCustomer = scan.nextLine();
			
			Integer idCustomer = null; Customer customer = null;
			
			if (removeCustomer.equals("Y")) {
				System.out.println("\nEnter the customer identification number: ");
				idCustomer = Integer.parseInt(scan.nextLine());
				customer = Customer.searchById(idCustomer);
				if (customer != null) {
						
					Boolean removedCustomer = manager.removeCustomer(customer);
					
					if (removedCustomer) {
						System.out.println("Customer removed");
					}
					else {
						System.out.println("Customer can not be removed, try again");
					}
				} else {
					System.out.println("\nInvalid customer ID");
				}
			}
		}
		while(removeCustomer.equals("Y"));
		
	}
	
	
	private static void loginAsCustomer() {
		String username; int password; Boolean logged = false;
		Customer customer = null;
		System.out.println("\nEnter your username: ");
		username = scan.nextLine();
		
		System.out.println("\nEnter your password: "); 
		try {
			password = Integer.parseInt(scan.nextLine());
			ArrayList<Customer> listOfCustomerFromFile = FileManagerCustomers.deserialize();
			
			for(Customer item : listOfCustomerFromFile) {
				if (item.getUserName().equals(username) && item.getPassword() == password) {
					logged = true;
					customer = item;
					System.out.println("\nWelcome " + item.getUserName());
					break;
				} 
				
			}
			
			if (logged) {
				menuCustomer(customer);
			}
		}
		catch (Exception e) {
			System.out.println("Invalid credencials");
		}
		
	}

	private static void menuCustomer(Customer customer) {
		Boolean auth = true;
		int option;
		do {
			System.out.println("\nChoose the operation you want to do: ");
			System.out.println("1 - View account balance");
			System.out.println("2 - Withdraw");
			System.out.println("3 - Deposit");
			System.out.println("4 - Transfer between accounts");
			System.out.println("5 - View Transactions");
			System.out.println("6 - Exit");
			
			try {
				option = Integer.parseInt(scan.nextLine());

				switch(option) {
				case 1:
					viewBalance(customer);
					break;
				case 2:
					withdraw(customer);
					break;
				case 3:
					deposit(customer);
					break;
				case 4:
					transfer(customer);
					break;
				case 5:
					displayTransactions(customer);
					break;
				case 6:
					auth = false;
					break;
				default:
					System.out.println("Please enter a valid option");
					break;
				}
			}
			catch (Exception e){
				System.out.println("Please enter a valid option");
			}
		}
		while(auth);
	}



	private static void viewBalance(Customer customer) throws ClassNotFoundException, IOException {
		String viewBalance; 
		do {
			System.out.println("\nDo you want to check your balance? (Y/N) ");
			viewBalance = scan.nextLine();
			
			Integer idAccount = null; Account account = null;
			
			if (viewBalance.equals("Y")) {
				System.out.println("\nEnter the account number: ");
				idAccount = Integer.parseInt(scan.nextLine());
				account = Account.searchById(idAccount);
				if (account != null) {
					
					System.out.println(account.getBalance());
					
				} else {
					System.out.println("\nInvalid account ID");
				}
			}
		}
		while(viewBalance.equals("Y"));
		
	}

	private static void withdraw(Customer customer) throws ClassNotFoundException, IOException, ExceptionNotEnoughBalance, ExceptionNegativeAmount, ExceptionIsNotANumber, ExceptionIsNull {
		String withdraw; 
		do {
			System.out.println("\nDo you want to make a withdraw? (Y/N) ");
			withdraw = scan.nextLine();
			
			Integer idAccount = null; EnumTypeAccount typeAccount = null;
			String chooseType = null;
			
			if (withdraw.equals("Y")) {
				System.out.println("\nEnter the type of the account: ");
				System.out.println("1 - Checking Account");
				System.out.println("2 - Saving Account");
				System.out.println("3 - Credit Account");
				System.out.println("4 - Currency Account");
				System.out.println("5 - Line of Credit Account");
				chooseType = scan.nextLine();
				
				switch (chooseType) {
				case "1":
					typeAccount = EnumTypeAccount.CheckingAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CheckingAccount checkingAccount = (CheckingAccount) Account.searchById(idAccount, typeAccount);
					if (checkingAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + checkingAccount.getBalance());
						System.out.println("Enter an amount to withdraw: ");
						amount = Double.parseDouble(scan.nextLine());
						checkingAccount.withdraw(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + checkingAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					
					break;
				case "2":
					typeAccount = EnumTypeAccount.SavingAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					SavingAccount savingAccount = (SavingAccount) Account.searchById(idAccount, typeAccount);
					if (savingAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + savingAccount.getBalance());
						System.out.println("Enter an amount to withdraw: ");
						amount = Double.parseDouble(scan.nextLine());
						savingAccount.withdraw(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + savingAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "3":
					typeAccount = EnumTypeAccount.CreditAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CreditAccount creditAccount = (CreditAccount) Account.searchById(idAccount, typeAccount);
					if (creditAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + creditAccount.getBalance());
						System.out.println("Enter an amount to withdraw: ");
						amount = Double.parseDouble(scan.nextLine());
						creditAccount.withdraw(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + creditAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "4":
					typeAccount = EnumTypeAccount.CurrencyAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CurrencyAccount currencyAccount = (CurrencyAccount) Account.searchById(idAccount, typeAccount);
					if (currencyAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + currencyAccount.getBalance());
						System.out.println("Enter an amount to withdraw: ");
						amount = Double.parseDouble(scan.nextLine());
						currencyAccount.withdraw(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + currencyAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "5":
					typeAccount = EnumTypeAccount.LineOfCreditAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					LineOfCreditAccount lineOfCreditAccount = (LineOfCreditAccount) Account.searchById(idAccount, typeAccount);
					if (lineOfCreditAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + lineOfCreditAccount.getBalance());
						System.out.println("Enter an amount to withdraw: ");
						amount = Double.parseDouble(scan.nextLine());
						lineOfCreditAccount.withdraw(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + lineOfCreditAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				default:
					System.out.println("Invalid account type");
				}
			}
		}
		while(withdraw.equals("Y"));
		
	}

	private static void deposit(Customer customer) throws ClassNotFoundException, IOException, ExceptionNotEnoughBalance, ExceptionNegativeAmount, ExceptionIsNotANumber, ExceptionIsNull, ExceptionWrongAmount, ExceptionLatePayment, ExceptionIsPassedDate {
		String deposit; 
		do {
			System.out.println("\nDo you want to make a deposit? (Y/N) ");
			deposit = scan.nextLine();
			
			Integer idAccount = null; EnumTypeAccount typeAccount = null;
			String chooseType = null;
			
			if (deposit.equals("Y")) {
				System.out.println("\nEnter the type of the account: ");
				System.out.println("1 - Checking Account");
				System.out.println("2 - Saving Account");
				System.out.println("3 - Credit Account");
				System.out.println("4 - Currency Account");
				System.out.println("5 - Line of Credit Account");
				chooseType = scan.nextLine();
				
				switch (chooseType) {
				case "1":
					typeAccount = EnumTypeAccount.CheckingAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CheckingAccount checkingAccount = (CheckingAccount) Account.searchById(idAccount, typeAccount);
					if (checkingAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + checkingAccount.getBalance());
						System.out.println("Enter an amount to deposit: ");
						amount = Double.parseDouble(scan.nextLine());
						checkingAccount.deposit(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + checkingAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					
					break;
				case "2":
					typeAccount = EnumTypeAccount.SavingAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					SavingAccount savingAccount = (SavingAccount) Account.searchById(idAccount, typeAccount);
					if (savingAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + savingAccount.getBalance());
						System.out.println("Enter an amount to deposit: ");
						amount = Double.parseDouble(scan.nextLine());
						savingAccount.deposit(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + savingAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "3":
					typeAccount = EnumTypeAccount.CreditAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CreditAccount creditAccount = (CreditAccount) Account.searchById(idAccount, typeAccount);
					if (creditAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + creditAccount.getBalance());
						System.out.println("Enter an amount to deposit: ");
						amount = Double.parseDouble(scan.nextLine());
						creditAccount.deposit(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + creditAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "4":
					typeAccount = EnumTypeAccount.CurrencyAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					CurrencyAccount currencyAccount = (CurrencyAccount) Account.searchById(idAccount, typeAccount);
					if (currencyAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + currencyAccount.getBalance());
						System.out.println("Enter an amount to deposit: ");
						amount = Double.parseDouble(scan.nextLine());
						currencyAccount.deposit(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + currencyAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				case "5":
					typeAccount = EnumTypeAccount.LineOfCreditAccount;
					System.out.println("\nEnter the account number: ");
					idAccount = Integer.parseInt(scan.nextLine());
					
					LineOfCreditAccount lineOfCreditAccount = (LineOfCreditAccount) Account.searchById(idAccount, typeAccount);
					if (lineOfCreditAccount != null) {
						Double amount = null;
						System.out.println("Balance: " + lineOfCreditAccount.getBalance());
						System.out.println("Enter an amount to deposit: ");
						amount = Double.parseDouble(scan.nextLine());
						lineOfCreditAccount.deposit(LocalDate.now(), amount);
						
						System.out.println("Updated balance: " + lineOfCreditAccount.getBalance());
					}
					else {
						System.out.println("Invalid account number");
					}
					break;
				default:
					System.out.println("Invalid account type");
				}
			}
		}
		while(deposit.equals("Y"));
		
	}

	private static void transfer(Customer customer) {
		// TODO Auto-generated method stub
		
	}
	
	private static void displayTransactions(Customer customer) {
		// TODO Auto-generated method stub
		
	}
}





































