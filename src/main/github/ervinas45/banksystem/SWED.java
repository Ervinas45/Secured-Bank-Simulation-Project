/**
 * 
 */
package main.github.ervinas45.banksystem;

import java.util.HashMap;

/**
 * @author Ervinas Marocka
 *
 */
public class SWED implements Bank{
	private String city;
	private HashMap<Integer, Account> hmap = new HashMap<Integer, Account>();

	public SWED(String city){
		this.city = city;
	}
	
	@Override
	public int createAccount(String accountName, String password) {
		Account account = new Account(accountName, password);
		hmap.put(account.getAccountID(), account);
		return account.getAccountID();
	}

	@Override
	public void destroyAccount(int accountID) {
			if(hmap.containsKey(accountID)){
				hmap.remove(accountID);
			}
			else{
				System.out.println("Sorry, no account based on this name was not found!");
			}
	}
	
	public void addFunds(int accountID, int sum){
			if(hmap.containsKey(accountID)){
				if(sum > 1000){
					System.out.println("Sorry, limit exceeded, Maximum add is 1000$.");
				}
				else{
					System.out.println("Operation Done! Old Balance : " + hmap.get(accountID).getBalance() + ".");
					hmap.get(accountID).addBalance(sum);
					System.out.println("New Balance : " + hmap.get(accountID).getBalance() + ".");
				}
			}
			else{
				System.out.println("Sorry, no account based on this name was not found!");
			}
	}
	
	public void withdrawFunds(int accountID, int sum){
			if(hmap.containsKey(accountID)){
				if(hmap.get(accountID).getBalance() == 0 || hmap.get(accountID).getBalance() - sum < 0){
					System.out.println("Sorry, you don't have enough money to withdraw, your balance is: " + hmap.get(accountID).getBalance() + ".");
				}
				else{
					System.out.println("Operation Done! Old Balance : " + hmap.get(accountID).getBalance() + ".");
					hmap.get(accountID).minusBalance(sum);
					System.out.println("New Balance : " + hmap.get(accountID).getBalance() + ".");
				}
			}
			else{
				System.out.println("Sorry, no account based on this name was not found!");
			}
	}
	
	public String getCity(){
		return this.city;
	}
	
	public String toString(){
		return "" + this.hmap.size();
	}
}
