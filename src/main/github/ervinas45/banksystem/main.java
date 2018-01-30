package main.github.ervinas45.banksystem;

import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		ArrayList<Integer> accounts = new ArrayList<Integer>();
		DNB dnb = new DNB("Vilnius");
		SWED swed = new SWED("Kaunas");
		accounts.add(dnb.createAccount("Ervinas", "test"));
		accounts.add(dnb.createAccount("Karolis", "test"));
		accounts.add(swed.createAccount("Tomas", "test"));
		
		System.out.println(dnb);
		System.out.println(swed);
		
		dnb.destroyAccount(2);
		swed.destroyAccount(3);
		
		System.out.println(dnb);
		System.out.println(swed);
		
		System.out.println(accounts.size());
//		dnb.addFunds(1, 200);
//		dnb.addFunds(2, 100);
//		dnb.withdrawFunds(1, 100);
//		dnb.withdrawFunds(2, 50);
//		System.out.println(dnb);
//		System.out.println(accounts.size());
//		
//		dnb.destroyAccount(1);
//		
//		if(accounts.contains(1)){
//			for(int accountID : accounts){
//				if(accountID == 1){
//					accounts.remove(1);
//				}
//			}
//		}
//		


	}

}
