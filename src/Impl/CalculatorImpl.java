package Impl;

import java.lang.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

<<<<<<< HEAD
<<<<<<< HEAD
import Server.Main;
import Server.UI;


public class CalculatorImpl implements Calculator ,Main{
	private String name;
	private UI in;
	private Registry reg;
    public CalculatorImpl (int regport,int bindport, String name, String reghost,boolean gui)throws RemoteException, AlreadyBoundException, NotBoundException{
    	this.name = name;
    	if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
    	reg = LocateRegistry.getRegistry(reghost, regport);
    	Binder c =
                (Binder)reg.lookup ("Binder");
    	c.bind(name,bindport,this);
    	System.out.println("Verbunden");
    	if(gui){
    		in = new UI(this);
    		Thread t = new Thread(in);
    		t.start();
    	}
=======
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
public class CalculatorImpl implements Calculator {
    public CalculatorImpl ()
        throws RemoteException
    {
<<<<<<< HEAD
>>>>>>> parent of fcf0fd2... Server-Balancer
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
    }

    public String pi (int iterations)
        throws RemoteException
    {
        double res = 0;
        for (int i = 1; i < iterations; i += 4) {
            res += 1.0/i - 1.0/(i+2);
        }
        /*
          try {
          PrintStream o = new PrintStream (
          new FileOutputStream ("/dev/pts/1"));
          o.println ("pi");
          Thread.sleep (iterations*1000);
          } catch (Exception e) {
          }
        */
<<<<<<< HEAD
<<<<<<< HEAD
        return name + " berechnete: " +4*res;
    }
    public static void main(String[] args){
    	int regport = 0;
		int bindport = 0;
		int binderport = 0;
		String host = "";
		String name = "";
		boolean a = true;
		if(args.length > 0)
            if(args[0].charAt(0) == 'd'){
            	a=false;
                regport = 4567;
                bindport = 7895;
                binderport = 2345;
                host = "127.0.0.1";
                name = "Pi";
            }else if ( args.length> 1){
            	try{
	        		a = false;
	        		name = args[0];
	        		host = args[1];
	        		regport = Integer.parseInt(args[2]);
	        		bindport = Integer.parseInt(args[3]);
            	}catch(NumberFormatException e){
            		System.out.println("Bitte beachten das die Ports Zahlen sein müssen!");
            		System.exit(0);
            	}catch(IndexOutOfBoundsException e){
            		System.out.println("Bitte beachten sie die richtige Anzahl con Parametern!");
            		System.exit(0);
            	}
            }
		if(a){
			System.out.println("Bitte folgende Syntax verwenden:");
			System.out.println("d für die default werte!");
			System.out.println("oder");
			System.out.println("<name> <loadbalancer IP> <Regestry PORT> <Service Port>");
		}else{
			
				try {
					if(System.getSecurityManager() == null)
						System.setSecurityManager(new SecurityManager());
					new CalculatorImpl(regport, bindport, name, host,true);
				} catch (Exception e) {
					e.printStackTrace();
			
					System.err.println("Das Programm wurde aufgrund eines Verbindungsfehlers beendet");
					System.exit(0);
				}
		}
=======
        return "" +4*res;
>>>>>>> parent of fcf0fd2... Server-Balancer
=======
        return "" +4*res;
>>>>>>> parent of fcf0fd2... Server-Balancer
    }
};
