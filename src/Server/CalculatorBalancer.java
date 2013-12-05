package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Impl.*;

public class CalculatorBalancer implements Calculator{
	ArrayList<CalculatorImpl> server;
	private int last, anz;
	public CalculatorBalancer(int anz) throws RemoteException{
		this.anz = anz;
		server = new ArrayList<CalculatorImpl>();
		for(int i=0; i < this.anz;i++){
			server.add(new CalculatorImpl());
		}
		last = 0;
	}

	@Override
	public synchronized String pi(int iterations) throws RemoteException {
		String re = "Server "+ this.last + ": " + server.get(last).pi(iterations);
		if(this.last < this.server.size()-1)
			last ++;
		else
			last = 0;
		return re;
	}

}
