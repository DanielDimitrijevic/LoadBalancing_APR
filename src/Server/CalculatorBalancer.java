package Server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Impl.*;

public class CalculatorBalancer implements Calculator {
	private Registry server;
	private int last;
	private int methode;
	private long lastid;
	private HashMap<Long, Integer> clientserver;
	private HashMap<Long, Long> clienttime;
	private ArrayList<Integer> lc;
	private ArrayList<Long> rt;
	private long timer = 300000;

	public CalculatorBalancer(Registry server, int methode)
			throws RemoteException {
		this.server = server;
		this.last = 1;
		this.methode = methode;
		clientserver = new HashMap<Long, Integer>();
		clienttime = new HashMap<Long, Long>();
		this.lastid = 100;
		lc = new ArrayList<Integer>();
		rt = new ArrayList<Long>();
	}

	@Override
	public synchronized String pi(int iterations, Long id)
			throws RemoteException {
		try {
			ArrayList<String> ar = new ArrayList<String>(Arrays.asList(server
					.list()));
			String re = id + " Error kein Server gefunden";
			if ((this.clientserver.containsKey(id) && this.clienttime
					.containsKey(id))) {
				long past = this.clienttime.get(id);
				long current = System.currentTimeMillis();
				if (current - past >= timer) {
					this.clienttime.remove(id);
					this.clientserver.remove(id);
					re = this.selBalancer(iterations, 0);
				} else {
					Calculator c = (Calculator) server.lookup(ar
							.get(this.clientserver.get(id)));
					re = c.pi(iterations, id);
				}
			} else {
				re = this.selBalancer(iterations, id);
				// this.clienttime.put(id, System.currentTimeMillis());
			}
			return re;
		} catch (NotBoundException e) {
			System.err.println("Nicht in der Retgistry vorhanden");
		}
		return "ERROR!";
	}

	public String selBalancer(int iterations, long id) throws RemoteException {
		String re = "ERROR!";
		id = this.lastid;
		this.lastid++;
		this.clienttime.put(id, System.currentTimeMillis());
		switch (this.methode) {
		case 0:
			re = balancerr(iterations, id);
			break;
		case 1:
			re = balancelc(iterations, id);
			break;
		case 2:
			re = balancert(iterations, id);
			break;
		}
		return re;
	}

	public String balancerr(int iterations, long id) throws RemoteException {
		if (this.last < this.server.list().length - 1)
			last++;
		else
			last = 1;
		Calculator c = null;
		try {
			this.clientserver.put(id, this.last - 1);
			c = (Calculator) server.lookup(server.list()[this.last - 1]);
		} catch (NotBoundException e) {
			System.err.println("Nicht in Registry vorhanden!");
		}
		String re = "Error konnte pi nicht abrufen";
		if (c != null)
			re = c.pi(iterations, id);
		return re;
	}

	public String balancelc(int iterations, long id) throws RemoteException {
		this.checklistlc();
		int se = getlc();
		this.lc.set(se, this.lc.get(se) + 1);
		this.clientserver.put(id, se);
		String name = this.server.list()[se];
		Calculator c = null;
		try {
			c = (Calculator) server.lookup(name);
		} catch (NotBoundException e) {
			System.err.println("Nicht in registry Vorhanden");
		}
		String re = "Error konnte pi nicht abrufen";
		if (c != null)
			re = c.pi(iterations, id);
		this.lc.set(se, this.lc.get(se) - 1);
		return re;
	}

	public void checklistlc() throws AccessException, RemoteException {
		while (lc.size() < this.server.list().length) {
			lc.add(0);
		}
		while (lc.size() > this.server.list().length) {
			lc.remove(lc.size() - 1);
		}
	}

	public int getlc() {
		int min = 0;
		for (int i = 0; i < lc.size(); i++) {
			if ((lc.get(i) < lc.get(min))) {
				min = i;
			}
		}
		return min;
	}

	public String balancert(int iterations, long id) throws RemoteException {
		long start = System.currentTimeMillis();
		checklistrt();
		int se = getrt();
		this.clientserver.put(id, se);
		String name = this.server.list()[se];
		Calculator c = null;
		try {
			c = (Calculator) server.lookup(name);
		} catch (NotBoundException e) {
			System.err.println("Nicht in Registry vorhanden");
		}
		String re = "Error konnte pi nicht abrufen";
		if (c != null)
			re = c.pi(iterations, id);
		this.rt.set(se, System.currentTimeMillis() - start);
		return re;
	}

	public void checklistrt() throws AccessException, RemoteException {
		while (rt.size() < this.server.list().length) {
			rt.add((long) 0);
		}
		while (rt.size() > this.server.list().length) {
			rt.remove(rt.size() - 1);
		}
	}

	public int getrt() {
		int min = 0;
		for (int i = 0; i < rt.size(); i++) {
			if ((rt.get(i) < rt.get(min))) {
				min = i;
			}
		}
		return min;
	}

	public void setTimer(long ms) {
		this.timer = ms;
	}

	public void setMethode(int m) {
		this.methode = m;
	}
}