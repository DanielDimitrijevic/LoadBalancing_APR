package Server;

import Impl.Calculator;

import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Balancer {
	public static void main(String [] args){
		try {
            PrintStream o = new PrintStream (System.out);
            o.println ("Server Balancing startet");
            if(System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
            
            Calculator ci = new CalculatorBalancer(5);
            o.println("Servers startet");
            Calculator stup = (Calculator) UnicastRemoteObject.exportObject(ci,1234);
            Registry reg = LocateRegistry.createRegistry(1099);

            String name = "Calculator";
            if (args.length > 0)
                name += args[0];
            Naming.rebind(name, stup);
            o.println("Gebunden!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
