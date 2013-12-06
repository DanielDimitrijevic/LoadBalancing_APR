package Impl;

import java.rmi.*;
/**
 * Interface welche die Pi methode definiert
 * @author Dominik Backhausen
 */
public interface Calculator extends Remote {
	/**
	 * Methode die Pi berechnenn soll
	 * @param iterations genaucichkeit
	 * @param id Userid für Delayed Bindung
	 * @return eine generierte Antwort die bestimmte informationen beinhaltet
	 * @throws RemoteException
	 */
    public String pi (int iterations, Long id)throws RemoteException;
}