package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Impl.CalculatorImpl;

public class UI implements Runnable{
	String zeile = null;
	BufferedReader console;
	boolean interupted = false;
	Main b;
	public UI(Main b){
		this.b = b;
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!interupted){
			try {
				if(b!=null)
					this.b.handleinput(console.readLine());
			} catch (IOException e) {
				// Sollte eigentlich nie passieren
				e.printStackTrace();
			}
		}
	}
	public void stop(){
		this.interupted = true;
	}
	public void restart(){
		this.interupted = false;
	}
}
