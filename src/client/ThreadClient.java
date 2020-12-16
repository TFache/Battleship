package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ThreadClient extends Thread {

	BufferedReader in; Scanner scan;

	public ThreadClient(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		scan = new Scanner(in);
	}

	public void run(){
		try {
			
			while (true) {
				System.out.println(scan.nextLine());
			}
		}catch (NoSuchElementException e) {
			System.out.println("Déconnexion effectuée. Veuillez taper \"/leave\" pour quitter la partie.");
		};
	}
}
