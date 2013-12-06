package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Impl.CalculatorImpl;
/**
 * Dies Klasse Erstellt ein Userinterface
 * @author Dominik Backhausen Daniel Dimitrijevic
 */
public class UI implements Runnable {
	String zeile = null;
	BufferedReader console;
	boolean interupted = false;
	Main b;
	/**
	 * konstruktor
	 * @param b Klasse an welche der User input zu verarbeitung geschickt werden soll
	 */
	public UI(Main b) {
		this.b = b;
		console = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		while (!interupted) {
			try {
				if (b != null)
					this.b.handleinput(console.readLine());
			} catch (IOException e) {
				// Sollte eigentlich nie passieren
				System.err.println("Fehler bei Eingabe!");
			}
		}
	}
	/**
	 * Stopt die Möglichkeit zur Eingabe
	 */
	public void stop() {
		this.interupted = true;
	}
	/**
	 * Stellt die Möglichkeit zur eingabe wieder her wieder her
	 */
	public void restart() {
		this.interupted = false;
	}
}