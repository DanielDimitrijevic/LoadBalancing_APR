package Server;

import Impl.Calculator;
import Impl.CalculatorImpl;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Balancer implements Main{
        private UI in;
        private CalculatorBalancer b;
        private PrintStream o;
        private Thread t;
        private Registry server, reg;
        private ArrayList<CalculatorImpl> localserver;
        private int regport;
        private int bindport;
        private int serverport;
        private String name;
        private int createport;
        public static void main(String [] args){
                int regport = 0;
                int bindport = 0;
                int serverport = 0;
                String name = "";
                boolean a = true;
                if(args.length > 0)
            if(args[0].charAt(0) == 'd'){
                    a=false;
                regport = 1099;
                bindport = 1234;
                serverport = 4567;
                name = "Pi";
            }else if ( args.length> 1){
                    try{
                         a = false;
                         name = args[0];
                         regport = Integer.parseInt(args[1]);
                         bindport = Integer.parseInt(args[2]);
                         serverport = Integer.parseInt(args[3]);
                    }catch(NumberFormatException e){
                            System.out.println("Bitte beachten das die Ports Zahlen sein müssen!");
                            System.exit(0);
                    }
            }
                if(a){
                        System.out.println("Bitte folgende Syntax verwenden:");
                        System.out.println("d für die default werte!");
                        System.out.println("oder");
                        System.out.println("<name> <Registry Port für Service> <Service Port> <Registry Ports für Server>");
                }else{
                        try {
                                new Balancer(regport, bindport, serverport, name);
                        } catch (RemoteException | MalformedURLException e) {
                                System.err.println("Das Programm wurde aufgrund eines Verbindungsfehlers beendet");
                                System.exit(0);
                        }
                }
        }
        private Balancer(int regport,int bindport,int serverport, String name) throws RemoteException, MalformedURLException{
                this.regport = regport;
                this.bindport = bindport;
                this.serverport = serverport;
                this.name = name;
                this.createport =1234;
                localserver = new ArrayList<CalculatorImpl>();
                o = new PrintStream (System.out);
                o.println("Balancer startet....");
                o.println("Regestry wird erstellt....");
                if(System.getSecurityManager() == null)
                        System.setSecurityManager(new SecurityManager());
                reg = LocateRegistry.createRegistry(regport);
                server = LocateRegistry.createRegistry(serverport);
                o.println("Registry erstellt!");
                o.println("Balancer algoritmus wird geladen....");
                Calculator ci = new CalculatorBalancer(server,0);
                Calculator stup = (Calculator) UnicastRemoteObject.exportObject(ci,bindport);
                o.println("Balancer algoritmus wurde geladen!");
                o.println("Service wird angeboten.....");
                reg.rebind(name, stup);
                o.println("Service wurde gebunden!");
                try {
                        creatsr("1");
                } catch (AlreadyBoundException e) {
                        // TODO Auto-generated catch block
                        System.err.println("Bereits in Registry vorhanden");
                }
                o.println("Balancer gestartet!");
                in = new UI(this);
                t = new Thread(in);
                t.start();
                o.println("Bereit für eingaben!");
                
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
                                	System.err.println("Bereits in Registry vorhanden");
                                }
                        else
                                o.println("Bitte die anzahl zu erstellenden Server angeben!");
                }else if(ar[0].equals("stop")){
                        this.stop();
                }else if(ar[0].equals("setT")){
                	try{
                		b.setTimer(Long.parseLong(ar[1]));
                	}
                	catch(NumberFormatException e){
                        o.println("Bitte eine Zahl eingeben!");
                }
                }else if(ar[0].equals("setM")){
                	try{
                		int m =Integer.parseInt(ar[1]);
                		if(m != 0 || m != 1 || m!=2){
                			o.println("Bitte eine richtige Methoden id eingeben!");
                		}else{
                			b.setMethode(m);
                		}
                	}
                	catch(NumberFormatException e){
                        o.println("Bitte eine Zahl eingeben!");
                }
                }	
                else{
                        o.println("Befehl nicht vorhanden");
                }
        }
        public void outhelp(){
                o.println("Befehle:");
                o.println("help | ?                                Listet alle verfügbaren befehle auf");
                o.println("list                                        Listet alle server auf die am loadbalancer verfügbar sind");
                o.println("create (anzahl)                erstellt neue Server");
                o.println("setT (zahl)                setzt den SessionTimer ( 60000 = 1 min)");
                o.println("setM (zahl)                setzt die LB-methode ( 0:Round Robin | 1:Least Connection | 2:Response Time )");
                o.println("stop                                 Beendet das Programm");
        }
        public void listser() throws AccessException, RemoteException{
                String[] ar = server.list();
                o.println("Es sind derzeit: " + ar.length + " Server registriert");
                for(int i = 0; i < ar.length;i++)
                        o.println(ar[i]);
        }
        public void creatsr(String anz) throws AccessException, RemoteException, AlreadyBoundException{
                int an=0;
                ArrayList<String> ar = new ArrayList<String>(Arrays.asList(server.list()));
                //String[] ar = server.list();
                try{
                        an = Integer.parseInt(anz);
                        for(int i = 0; i < an; i++){
                                //o.println(i);
                                String s = "Server";
                                for(int ii = 0; ar.contains(s);ii++)
                                        s = "Server " + ii;
                                localserver.add(new CalculatorImpl(serverport,createport,s,"127.0.0.1",false));
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
        }
}