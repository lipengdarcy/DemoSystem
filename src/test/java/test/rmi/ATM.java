package test.rmi;

import java.rmi.Naming;

public class ATM {

    public static void main (String args[]) throws Exception {
    	//localhost 7777 testMethod
        String URL = "//localhost:7777/Bank";
        System.out.println("Connecting to: " + URL);
        BankInterface bank = (BankInterface)Naming.lookup(URL);
        System.out.println("Connected!");
    }
}
