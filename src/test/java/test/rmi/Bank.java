package test.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Bank extends UnicastRemoteObject implements BankInterface {

	private static final long serialVersionUID = 1L;

	protected Bank(int port) throws RemoteException {
		super(port);
	}

	public static void main(String args[]) throws Exception {

		try {
			System.setSecurityManager(new SecurityManager());
			System.out.println("Security Manager set.");

			Bank myBank = new Bank(7777);
			System.out.println("Bank instance created");

			Naming.rebind("Bank", myBank);
			System.out.println("Name rebind completed.");
			System.out.println("Server ready for requests!");

		} catch (Exception e) {
			System.out.println("Error in main - " + e.toString());
		}
	}
}