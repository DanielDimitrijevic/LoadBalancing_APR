package Server;

import Impl.Calculator;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Balancer {
	private UI in;
	private CalculatorBalancer b;
	private PrintStream o;
	private Thread t;
	
	public static void main(String [] args){
//		UI i = new UI();
//		Thread t = new Thread(i);
//		t.start();
//		try {
//            PrintStream o = new PrintStream (System.out);
//            o.println ("Server Balancing startet");
//            if(System.getSecurityManager() == null)
//				System.setSecurityManager(new SecurityManager());
//            
//            Calculator ci = new CalculatorBalancer(5);
//            o.println("Servers startet");
//            Calculator stup = (Calculator) UnicastRemoteObject.exportObject(ci,1234);
//            Registry reg = LocateRegistry.createRegistry(1099);
//            
//            String name = "Calculator";
//            if (args.length > 0)
//                name += args[0];
//            Naming.rebind(name, stup);
//            o.println("Gebunden!!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
	}
	private Balancer(int regport,int bindport,int serverport, String name) throws RemoteException, MalformedURLException{
		o = new PrintStream (System.out);
		o.println("Balancer startet....");
		o.println("Regestry wird erstellt....");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		Registry reg = LocateRegistry.createRegistry(regport);
		Registry server = LocateRegistry.createRegistry(serverport);
		o.println("Registry erstellt!");
		o.println("Balancer algoritmus wird geladen....");
		Calculator ci = new CalculatorBalancer(5);
		Calculator stup = (Calculator) UnicastRemoteObject.exportObject(ci,bindport);
		o.println("Balancer algoritmus wurde geladen!");
		o.println("Service wird angeboten.....");
		reg.rebind(name, stup);
		o.println("Service wurde gebunden!");
		o.println("Balancer gestartet!");
		in = new UI(this);
		t = new Thread(in);
		t.start();
		o.println("Bereit für eingaben!");
	}
}
