package server;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static void main(String[] args) {
		try {
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");	
			
			
			while(true) {
				Socket client1 = ecoute.accept();

				Socket client2 = ecoute.accept();
				new ThreadServer(client1, client2).start();
			}
		} catch(Exception e) {
			System.out.println("MainServer : " + e.getMessage());
		}

	}
}
