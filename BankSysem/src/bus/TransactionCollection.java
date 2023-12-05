package bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TransactionCollection {
	private ArrayList<Transaction> listOfTransactions = new ArrayList<Transaction>();
	
	public void add(Transaction transaction)
	{
		listOfTransactions.add(transaction);
	}
	
	public ArrayList<Transaction> searchBytype (EnumTypeTransaction type) 
	{
		ArrayList<Transaction> listOfTransactionsByType = new ArrayList<Transaction>();
		
		for (Transaction element : listOfTransactions)
		{
			if (element.getType().equals(type))
			{
				listOfTransactionsByType.add(element);
			}
		}
		return listOfTransactionsByType;
	}
	
	public ArrayList<Transaction> searchByDate (Date date) 
	{
		ArrayList<Transaction> listOfTransactionsByDate = new ArrayList<Transaction>();
		
		for (Transaction element : listOfTransactions)
		{
			if (element.getTransactionDate().equals(date))
			{
				listOfTransactionsByDate.add(element);
			}
		}
		return listOfTransactionsByDate;
	}
	
	public void sortByDate(PredicateDate datePredicate)
	{
		Collections.sort(this.listOfTransactions,datePredicate);
	}
	
	public void sortByAmount(PredicateAmount amountPredicate)
	{
		Collections.sort(this.listOfTransactions,amountPredicate);
	}
}
