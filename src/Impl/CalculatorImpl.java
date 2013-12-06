package Impl;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import Server.Main;
import Server.UI;
/**
 * Implementierung des Servers welcher die Pi methode bereitstellt
 * @author Dominik Baclhausen Daniel Dimitrijevic
 *
 */
public class CalculatorImpl implements Calculator, Main {
	private String name;
	private UI in;
	private Registry reg;
	/**
	 * Startet den Server
	 * @param regport port auf dem die Registry leigt
	 * @param bindport port an dem der Service gebunden werden soll
	 * @param name name des Serivces 
	 * @param reghost IP auf welcher die registry leigt
	 * @param gui gibt an ob ein Userinterface gestartet werden soll oder nicht
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public CalculatorImpl(int regport, int bindport, String name,String reghost, boolean gui) throws RemoteException,AlreadyBoundException {
		this.name = name;
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		reg = LocateRegistry.getRegistry(reghost, regport);
		Calculator stup = (Calculator) UnicastRemoteObject.exportObject(this,
				bindport);
		reg.bind(name, stup);
		System.out.println("Verbunden");
		if (gui) {
			in = new UI(this);
			Thread t = new Thread(in);
			t.start();
		}
	}
	@Override
	public String pi(int iterations, Long id) throws RemoteException {
		double res = 0;
		for (int i = 1; i < iterations; i += 4) {
			res += 1.0 / i - 1.0 / (i + 2);
		}
		return id.toString() + " " + name + " berechnete pi: " + 4 * res;
	}

	public static void main(String[] args) {
		int regport = 0;
		int bindport = 0;
		String host = "";
		String name = "";
		boolean a = true;
		if (args.length > 0)
			if (args[0].charAt(0) == 'd') {
				a = false;
				regport = 4567;
				bindport = 7895;
				host = "127.0.0.1";
				name = "Pi";
			} else if (args.length > 1) {
				try {
					a = false;
					name = args[0];
					host = args[1];
					regport = Integer.parseInt(args[2]);
					bindport = Integer.parseInt(args[3]);
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
			System.out
					.println("<name> <loadbalancer IP> <Regestry PORT> <Service Port>");
		} else {

			try {
				if (System.getSecurityManager() == null)
					System.setSecurityManager(new SecurityManager());
				new CalculatorImpl(regport, bindport, name, host, true);
			} catch (Exception e) {
				// System.err.println("Bereits in Registry vorhanden");

				System.err
						.println("Das Programm wurde aufgrund eines Verbindungsfehlers beendet");
				System.exit(0);
			}
		}
	}
	@Override
	public void handleinput(String inp) throws AccessException, RemoteException {
		String[] ar = inp.split(" ");
		if (ar[0].equals("help") || ar[0].equals("?")) {
			this.outhelp();
		} else if (ar[0].equals("stop")) {
			this.stop();
		} else {
			System.out.println("Befehl nicht vorhanden");
		}
	}
	/**
	 * Gibt die Hilfe zurück
	 */
	public void outhelp() {
		System.out.println("Befehle:");
		System.out.println("help | ?                        Listet alle verfügbaren befehle auf");
		System.out.println("stop                                 Beendet das Programm");
	}
	/**
	 * Beendet den Server
	 * @throws AccessException
	 * @throws RemoteException
	 */
	public void stop() throws AccessException, RemoteException {
		try {
			reg.unbind(this.name);
		} catch (NotBoundException e) {
			System.err.println("Nicht in Registry vorhanden");
		}
		System.exit(0);
	}
}