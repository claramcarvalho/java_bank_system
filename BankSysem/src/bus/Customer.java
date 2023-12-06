package bus;
import java.util.ArrayList;

public class Customer extends User{
	
	private static final long serialVersionUID = -3112856540751151482L;
	private Double salary;
	private Manager mgr;
	private ArrayList<Account> listOfAccounts = new ArrayList<Account>();
	
	public Customer() {
		super();
		this.salary = 0.00;
		this.mgr = null;
		this.listOfAccounts = null;
	}

	public Customer(String userName, Integer password, Double salary, Manager mgr, ArrayList<Account> listOfAccounts) throws ExceptionIsNotANumber, ExceptionIsNull {
		super(userName, password);
		setSalary(salary);
		this.mgr = mgr;
		this.listOfAccounts = listOfAccounts;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) throws ExceptionIsNotANumber, ExceptionIsNull {
		if (!Validator.isDouble(salary)) {
			throw new ExceptionIsNotANumber();
		}
		
		if (Validator.isNull(salary)) {
			throw new ExceptionIsNull();
		}
		this.salary = salary;
	}

	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
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
								  "\nManager ID: " + mgr.getUserName() + "\n List of accounts: \n" + listOfAccounts;
	}
	
}
