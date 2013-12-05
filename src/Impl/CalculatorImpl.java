package Impl;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import Server.Main;
import Server.UI;


public class CalculatorImpl implements Calculator ,Main{
        private String name;
        private UI in;
        private Registry reg;
    public CalculatorImpl (int regport,int bindport, String name, String reghost,boolean gui)throws RemoteException, AlreadyBoundException{
            this.name = name;
            if(System.getSecurityManager() == null)
                        System.setSecurityManager(new SecurityManager());
            reg = LocateRegistry.getRegistry(reghost, regport);
            Calculator stup = (Calculator) UnicastRemoteObject.exportObject(this,bindport);
            reg.bind(name, stup);
            System.out.println("Verbunden");
            if(gui){
                    in = new UI(this);
                    Thread t = new Thread(in);
                    t.start();
            }
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
        return name + " berechnete: " +4*res;
    }
    public static void main(String[] args){
            int regport = 0;
                int bindport = 0;
                String host = "";
                String name = "";
                boolean a = true;
                if(args.length > 0)
            if(args[0].charAt(0) == 'd'){
                    a=false;
                regport = 4567;
                bindport = 7895;
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
    }
        public void handleinput(String inp) throws AccessException, RemoteException {
                String [] ar = inp.split(" ");
                if(ar[0].equals("help") || ar[0].equals("?")){
                        this.outhelp();
                }else if(ar[0].equals("stop")){
                        this.stop();
                }
                else{
                        System.out.println("Befehl nicht vorhanden");
                }                
        }
        public void outhelp(){
                System.out.println("Befehle:");
                System.out.println("help | ?                        Listet alle verfügbaren befehle auf");
                System.out.println("stop                                 Beendet das Programm");
        }
        public void stop() throws AccessException, RemoteException{
                try {
                        reg.unbind(this.name);
                } catch (NotBoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                System.exit(0)
;        }
}