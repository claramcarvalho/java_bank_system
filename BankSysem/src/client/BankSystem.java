package client;

import java.io.IOException;
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
		
		while(auth) {
			System.out.println("\nChoose how do you want to login: ");
			System.out.println("1- Manager");
			System.out.println("2- Customer");
			System.out.println("3- Exit");
			
			try {
				typeUser = Integer.parseInt(scan.nextLine());

				switch(typeUser) {
				case 1:
					loginAsManager();
					break;
				case 2:
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
		while(auth) {
			System.out.println("\nChoose the operation you want to do: ");
			System.out.println("1- Open an account");
			System.out.println("2- Close an account");
			System.out.println("3- Create a customer");
			System.out.println("4- Remove a customer");
			System.out.println("5- Find all customers");
			System.out.println("6- Find all accounts");
			System.out.println("99- Exit");
			
			try {
				option = Integer.parseInt(scan.nextLine());

				switch(option) {
				case 1:
					openAccount();
					break;
				case 2:
					closeAccount();
					break;
				case 3:
					createCustomer(manager);
					break;
				case 4:
					removeCustomer();
					break;
				case 5:
					displayAllCustomers();
					break;
					
				case 6:
					displayAllAccounts();
					break;
				case 99:
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
		String username; int password, monthlyLimit; double salary, balance, transactionFees; 
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

	private static void openAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void closeAccount() {
		// TODO Auto-generated method stub
		
	}

	private static void removeCustomer() {
		// TODO Auto-generated method stub
		
	}



}





































