package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			new ThreadClient(s).start();
			System.out.println("Bienvenue à vous !");
			Scanner sc=new Scanner(System.in);
			String message="";
			while (!message.equals("/leave")) {
				message=sc.nextLine();
				out.println(message);
			}
			sc.close();
			s.close();
			System.exit(0);
		} catch(Exception e) {
			System.out.println("MainClient : " + e.getMessage());
		}

	}
}
