package Client;

import Impl.Calculator;
import Impl.CalculatorImpl;
import Server.Main;
import Server.UI;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import sun.rmi.transport.LiveRef;
/**
 * Das ClientProgramm was ein Userinterface erstellt und auf befehl des Users pi mit einer eingegebenen genauchichkeit berechnet.
 * @author Dominik Backhausen
 */
public class Client implements Main {
	private Calculator c;
	private UI in;
	private long id;
	
	public static void main(String[] args) {
		int regport = 0;
		String host = "";
		String name = "";
		boolean a = true;
		if (args.length > 0)
			if (args[0].charAt(0) == 'd') {
				a = false;
				regport = 1099;
				host = "127.0.0.1";
				name = "Pi";
			} else if (args.length > 1) {
				try {
					a = false;
					name = args[0];
					host = args[1];
					regport = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					System.out
							.println("Bitte beachten das die Ports Zahlen sein müssen!");
					System.exit(0);
				}
			}
		if (a) {
			System.out.println("Bitte folgende Syntax verwenden:");
			System.out.println("d für die default werte!");
			System.out.println("oder");
			System.out.println("<Name> <Host> <PORT>");
		} else {
			try {
				if (System.getSecurityManager() == null)
					System.setSecurityManager(new SecurityManager());
				new Client(name, host, regport);
			} catch (Exception e) {
				// e.printStackTrace();

				System.err
						.println("Das Programm wurde aufgrund eines Verbindungsfehlers beendet");
				System.exit(0);
			}
		}

	}
	/**
	 * Konstruktor zum Starten des Programs
	 * @param name name unter dem Der Service in der Registry eingetragen ist
	 * @param host IP auf welcher die Registry liegt
	 * @param regport port auf welchem die Registry leigt
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client(String name, String host, int regport)throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry(host, regport);
		c = (Calculator) reg.lookup(name);
		in = new UI(this);
		Thread t = new Thread(in);
		t.start();
		id = 0;
		System.out.println("Bereit für eingbe!");
	}

	@Override
	public void handleinput(String inp) throws AccessException, RemoteException {
		String[] ar = inp.split(" ");
		if (ar[0].equals("help") || ar[0].equals("?")) {
			this.outhelp();
		} else if (ar[0].equals("pi")) {
			try {
				String get = c.pi(Integer.parseInt(ar[1]), id);
				String[] aa = get.split(" ", 2);
				System.out.println(aa[0]);
				id = Long.parseLong(aa[0]);
				System.out.println(aa[1]);
			} catch (NumberFormatException e) {
				System.err.println("Bitte eine Zahl eingeben!");
			}
		} else if (ar[0].equals("stop")) {
			in.stop();
			System.exit(0);
		} else {
			System.out.println("Befehl nicht vorhanden");
		}
	}
	/**
	 * Diese Mthode gibt die Hilfe auf
	 */
	public void outhelp() {
		System.out.println("Befehle:");
		System.out
				.println("help | ?                        Listet alle verfügbaren befehle auf");
		System.out
				.println("pi (anzahl)                                berechnet pi");
		System.out
				.println("stop                                 Beendet das Programm");
	}
}