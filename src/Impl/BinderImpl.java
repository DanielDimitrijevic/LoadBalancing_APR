package Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Server.Balancer;

public class BinderImpl implements Binder{
	private Balancer b;

	public BinderImpl(Balancer b){
		this.b =b;
	}
	@Override
	public void bind(String name, int port, CalculatorImpl imp)
			throws RemoteException {
		Calculator stup = (Calculator) UnicastRemoteObject.exportObject(imp,port);
		b.getServer().rebind(name,stup);
	}

}
