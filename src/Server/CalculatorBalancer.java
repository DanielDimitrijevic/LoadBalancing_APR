package Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import Impl.*;

public class CalculatorBalancer implements Calculator{
        Registry server;
        private int last;
        public CalculatorBalancer(Registry server) throws RemoteException{
                this.server = server;
                last = 0;
        }

        @Override
        public synchronized String pi(int iterations) throws RemoteException {
                if(this.last < this.server.list().length-1)
                        last ++;
                else
                        last = 0;
                Calculator c = null;
                try {
                        c = (Calculator)server.lookup (server.list()[this.last-1]);
                } catch (NotBoundException e) {
                        e.printStackTrace();
                }
                String re = "Error konnte pi nicht abrufen";
                if(c != null)
                        re = c.pi(iterations);
                
                return re;
        }
}