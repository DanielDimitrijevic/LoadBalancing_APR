package Impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Binder extends Remote{
	public void bind(String name, int port ,CalculatorImpl imp)throws RemoteException;
}
