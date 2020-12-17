package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import game.boats.*;
import game.grid.Grid;

public class ThreadServer extends Thread {


	BufferedReader in1; PrintWriter out1;
	BufferedReader in2; PrintWriter out2;
	static PrintWriter[] outs=new PrintWriter[100]; 

	public ThreadServer(Socket client1,Socket client2) {
		try {

			in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			out1 = new PrintWriter(client1.getOutputStream(), true);

			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out2 = new PrintWriter(client2.getOutputStream(), true);


			out1.println("Vous êtes le joueur 1\n");
			out2.println("Vous êtes le joueur 2\n");


		}catch (Exception e) {}
	}

	public void run() {
		//Définition de la grille de jeu et de sa taille par le joueur 1
		Grid grid1; Grid grid2; Grid vue1; Grid vue2; //Les deux grilles + ce que leur adversaire voit
		try {
			
			out1.println("Définissez la taille de la grille de jeu :");
			String sizeStr = in1.readLine();
			int size = Integer.parseInt(sizeStr);
			
			grid1 = new Grid(new String[size][size]);
			grid1.setId(1);
			out1.println("Grille de taille " + size);
			grid1.affiche(out1);
			out1.println("---------------------------------------------------------------");
			
			grid2 = new Grid(new String[size][size]);
			grid2.setId(2);
			out2.println("Grille de taille " + size);
			grid2.affiche(out2);
			out2.println("---------------------------------------------------------------");
			
			vue1 = new Grid(new String[size][size]);
			vue2 = new Grid(new String[size][size]);
			
			//Définition du nombre de bateaux et placement
			if(size >= 2) {
				Boat one = new OneCaseBoat(0);
				grid1.choix(in1, out1, one);
				grid2.choix(in2, out2, one);
			}
			if(size >= 4) {
				Boat two = new TwoCasesBoat(1);
				grid1.choix(in1, out1, two);
				grid2.choix(in2, out2, two);
			}
			if(size >= 6) {
				Boat three  =new ThreeCasesBoat(2);
				grid1.choix(in1, out1, three);
				grid2.choix(in2, out2, three);
			}
			if(size >= 8) {
				Boat four  =new FourCasesBoat(3);
				grid1.choix(in1, out1, four);
				grid2.choix(in2, out2, four);
			}
			if(size >= 10) {
				Boat five = new FiveCasesBoat(4);
				grid1.choix(in1, out1, five);
				grid2.choix(in2, out2, five);
			}
			
			//Déroulement du jeu
			while (true) {
				
				String x1 = null;String y1 = null;String x2 = null;String y2 = null;
				
				grid1.setLegit(false);grid2.setLegit(false);

				
				while(!grid1.isLegit()) /*Tant que le placement n'est pas autorisé*/ {
					out1.println("---------------------------------------------------------------");
					//choix d'une case à viser
					out1.println("J1, choisissez votre case :");
					out1.println("x : ");
					x1=in1.readLine();
					out1.println("y : ");
					y1=in1.readLine();
					//Affichage des actions de chaque joueur
					try {
						grid1.attaque(grid2, Integer.parseInt(x1), Integer.parseInt(y1), vue1);
						grid1.setLegit(true);

					} catch (Exception e) {
						out1.println("Une ou les deux entrées sont incorrectes. "
								+ "Veuillez saisir à nouveau votre attaque");
					}
					
				}

				while(!grid2.isLegit()) {
					out2.println("---------------------------------------------------------------");
					//choix d'une case à viser
					out2.println("J2, choisissez votre case :");
					out2.println("x : ");
					x2=in2.readLine();
					out2.println("y : ");
					y2=in2.readLine();
					//Affichage des actions de chaque joueur
					try {
						grid2.attaque(grid1, Integer.parseInt(x2), Integer.parseInt(y2), vue2);
						grid2.setLegit(true);
					} catch (Exception e) {
						out2.println("Une ou les deux entrées sont incorrectes. "
								+ "Veuillez saisir à nouveau attaque");
					}
					
				}
					
	
				
				//Traitement des actions :
				out1.println(grid1.messageTouche(grid1));
				out2.println(grid2.messageTouche(grid2));
				out1.println("---------------------------------------------------------------");
				out2.println("---------------------------------------------------------------");
				out1.println("Vos attaques : ");
				out2.println("Vos attaques : ");
				vue1.affiche(out1);
				vue2.affiche(out2);//Va permettre d'afficher les attaques déjà réalisées
				out1.println("J2 a visé : [" +x2 + ";" + y2 + "]");
				out2.println("J1 a visé : [" +x1 + ";" + y1 + "]");
				grid1.affiche(out1); //Affiche la grille du joueur
				grid2.affiche(out2);

				
				//Traitement d'une victoire (tous les bateaux coulés)
				grid1.checkVictory(grid2);
				grid2.checkVictory(grid1);
				if(grid1.isVictory() && !grid2.isVictory()) {
					out1.println("Vous avez gagné, félicitations !");
					out2.println("Vous avez perdu...");
					break;
				}
				if(grid2.isVictory() && !grid1.isVictory()) {
					out1.println("Vous avez perdu...");
					out2.println("Vous avez gagné, félicitations !");
					break;
				}
				if(grid1.isVictory() && grid2.isVictory()) {
					out1.println("Egalité.");
					out2.println("Egalité.");
					break;
				}
			}
			in1.close();in2.close();out1.close();out2.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
