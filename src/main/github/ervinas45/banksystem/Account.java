package main.github.ervinas45.banksystem;

public class Account{
	
	private static int accountID;
	private String accountName;
	private String password;
	private int balance = 0;
	
	public Account(String accountName, String password){
		this.accountName = accountName;
		this.password = password;
		accountID++;
	}
	
	public int getAccountID(){
		return accountID;
	}
	
	public String getAccountName(){
		return this.accountName;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void changeUsername(String newName){
		this.accountName = newName;
	}
	
	public void changePassword(String newPassword){
		this.password = newPassword;
	}
	
	public int getBalance(){
//		System.out.println("Your current balance is: " + this.balance);
		return this.balance;
	}
	
	public void addBalance(int sum){
		this.balance += sum;
	}
	
	public void minusBalance(int sum){
		this.balance = balance - sum;
	}
	
}
