package main.github.ervinas45.banksystem;

public interface Bank{
	
	public int createAccount(String accountName, String password);
	public void destroyAccount(int accountID);
	public void addFunds(int accountID, int sum);
	public void withdrawFunds(int accountID, int sum);
	
}
