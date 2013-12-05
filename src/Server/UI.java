package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI implements Runnable{
	String zeile = null;
	BufferedReader console;
	boolean interupted = false;
	Balancer b;
	public UI(Balancer b){
		this.b = b;
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!interupted){
			try {
				zeile = console.readLine();
			} catch (IOException e) {
				// Sollte eigentlich nie passieren
				e.printStackTrace();
			}
			System.out.println("Ihre Eingabe war: " + zeile);
		}
	}
	public void stop(){
		this.interupted = true;
	}
	public void restart(){
		this.interupted = false;
	}
}
