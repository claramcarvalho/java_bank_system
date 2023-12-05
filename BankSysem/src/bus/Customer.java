package bus;
import java.util.ArrayList;

public class Customer extends User{
	
	private Double salary;
	private Integer mgrId;
	private ArrayList<Account> listOfAccounts = new ArrayList<Account>();
	
	public Customer() {
		super();
		this.salary = 0.00;
		this.mgrId = null;
		this.listOfAccounts = null;
	}

	public Customer(Integer identificationNumber, String userName, Integer password, Double salary, Integer mgrId, ArrayList<Account> listOfAccounts) {
		super(identificationNumber, userName, password);
		this.salary = salary;
		this.mgrId = mgrId;
		this.listOfAccounts = listOfAccounts;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getMgrId() {
		return mgrId;
	}

	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}

	public ArrayList<Account> getListOfAccounts() {
		return listOfAccounts;
	}
	
	public void addNewAccount(Account newAccount) {
		this.listOfAccounts.add(newAccount);
	}

	@Override
	public String toString() {
		return super.toString() + "\nCustomer salary: " + salary + 
								  "\nManager ID: " + mgrId + "\n List of accounts: \n" + listOfAccounts;
	}
	
}
