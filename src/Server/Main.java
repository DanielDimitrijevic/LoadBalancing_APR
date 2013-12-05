package Server;

import java.rmi.AccessException;
import java.rmi.RemoteException;

public interface Main {
        public void handleinput(String inp) throws AccessException, RemoteException;
}