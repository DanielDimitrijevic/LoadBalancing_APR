package Server;

import Impl.Binder;
import Impl.BinderImpl;
import Impl.Calculator;

import java.io.PrintStream;
import java.net.MalformedURLException;
<<<<<<< HEAD
<<<<<<< HEAD
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
=======
import java.rmi.Naming;
>>>>>>> parent of fcf0fd2... Server-Balancer
=======
import java.rmi.Naming;
>>>>>>> parent of fcf0fd2... Server-Balancer
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Balancer {
	private UI in;
	private CalculatorBalancer b;
	private PrintStream o;
	private Thread t;
<<<<<<< HEAD
<<<<<<< HEAD
	private Registry server, reg;
	private ArrayList<CalculatorImpl> localserver;
	private int regport;
	private int bindport;
	private int binderport;
	private int serverport;
	private String name;
	private int createport;
	public static void main(String [] args){
		int regport = 0;
		int binderport = 0;
		int serverport = 0;
		int bindport = 0;
		String name = "";
		boolean a = true;
		if(args.length > 0)
            if(args[0].charAt(0) == 'd'){
            	a=false;
                regport = 1099;
                bindport = 1234;
                serverport = 4567;
                binderport = 2345;
                name = "Pi";
            }else if ( args.length> 1){
            	try{
	        		a = false;
	        		name = args[0];
	        		regport = Integer.parseInt(args[1]);
	        		binderport = Integer.parseInt(args[2]);
	        		bindport =Integer.parseInt(args[3]);
	        		serverport = Integer.parseInt(args[4]);
            	}catch(NumberFormatException e){
            		System.out.println("Bitte beachten das die Ports Zahlen sein m�ssen!");
            		System.exit(0);
            	}catch(IndexOutOfBoundsException e){
            		System.out.println("Bitte beachten sie die richtige Anzahl con Parametern!");
            		System.exit(0);
            	}
            }
		if(a){
			System.out.println("Bitte folgende Syntax verwenden:");
			System.out.println("d f�r die default werte!");
			System.out.println("oder");
			System.out.println("<name> <Registry Port f�r Service> <Service Port> <port zum einbinden von externen Servern> <Registry Ports f�r Server>");
		}else{
			try {
				new Balancer(regport,bindport, binderport, serverport, name);
			} catch (RemoteException | MalformedURLException e) {
				System.err.println("Das Programm wurde aufgrund eines Verbindungsfehlers beendet");
				System.exit(0);
			}
		}
	}
	private Balancer(int regport,int bindport,int binderport,int serverport, String name) throws RemoteException, MalformedURLException{
		this.regport = regport;
		this.binderport = binderport;
		this.bindport = bindport;
		this.serverport = serverport;
		this.name = name;
		this.createport =1234;
		localserver = new ArrayList<CalculatorImpl>();
=======
	
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
>>>>>>> parent of fcf0fd2... Server-Balancer
=======
	
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
>>>>>>> parent of fcf0fd2... Server-Balancer
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
		Binder bb = new BinderImpl(this);
		Binder bstup = (Binder) UnicastRemoteObject.exportObject(bb,binderport);
		o.println("Balancer algoritmus wurde geladen!");
		o.println("Service wird angeboten.....");
		reg.rebind("Binder",bstup);
		reg.rebind(name, stup);
		o.println("Service wurde gebunden!");
<<<<<<< HEAD
<<<<<<< HEAD
		try {
			creatsr("1");
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
		o.println("Balancer gestartet!");
		in = new UI(this);
		t = new Thread(in);
		t.start();
		o.println("Bereit f�r eingaben!");
<<<<<<< HEAD
<<<<<<< HEAD
		
	}
	@Override
	public void handleinput(String inp) throws AccessException, RemoteException{
		//o.println("sadsadas");
		String [] ar = inp.split(" ");
		if(ar[0].equals("help") || ar[0].equals("?")){
			this.outhelp();
		}else if(ar[0].equals("list")){
			this.listser();
		}else if(ar[0].equals("create")){
			if(ar.length >= 2)
				try {
					this.creatsr(ar[1]);
				} catch (AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				o.println("Bitte die anzahl zu erstellenden Server angeben!");
		}else if(ar[0].equals("stop")){
			this.stop();
		}
		else{
			o.println("Befehl nicht vorhanden");
		}
	}
	public void outhelp(){
		o.println("Befehle:");
		o.println("help | ?				Listet alle verf�gbaren befehle auf");
		o.println("list					Listet alle server auf die am loadbalancer verf�gbar sind");
		o.println("create (anzahl)		erstellt neue Server");
		o.println("stop 				Beendet das Programm");
	}
	public void listser() throws AccessException, RemoteException{
		String[] ar = server.list();
		o.println("Es sind derzeit: " + ar.length + " Server registriert");
		for(int i = 0; i < ar.length;i++)
			o.println(ar[i]);
	}
	public void creatsr(String anz) throws AccessException, RemoteException, AlreadyBoundException, NotBoundException{
		int an=0;
		ArrayList<String> ar = new ArrayList<String>(Arrays.asList(server.list()));  
		//String[] ar = server.list();
		try{
			an = Integer.parseInt(anz);
			for(int i = 0; i < an; i++){
				o.println(i);
				String s = "Server";
				for(int ii = 0; ar.contains(s);ii++)
					s = "Server " + ii;
				localserver.add(new CalculatorImpl(regport,createport,s,"127.0.0.1",false));
				createport ++;
				ar = new ArrayList<String>(Arrays.asList(server.list()));
			}
			o.println("Es wurden " + an + " Server erstellt!");
		}catch(NumberFormatException e){
			o.println("Bitte eine Zahl eingeben!");
		}
	}
	public void stop(){
		in.stop();
		System.exit(0);
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
	}
	public Registry getServer(){
		return this.server;
=======
>>>>>>> parent of fcf0fd2... Server-Balancer
	}
}
