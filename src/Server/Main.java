package Server;

import java.rmi.AccessException;
import java.rmi.RemoteException;
/**
 * Inteface um die eingaben de Users zu verwalten
 * @author Daniel Dimitrijevic
 */
public interface Main {
	/**
	 * Verarbeitet Userinput
	 * @param inp die eingegebene Zeile
	 * @throws AccessException
	 * @throws RemoteException
	 */
	public void handleinput(String inp) throws AccessException, RemoteException;
}