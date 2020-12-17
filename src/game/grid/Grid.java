package game.grid;

import java.io.BufferedReader;
import java.io.PrintWriter;
import game.boats.*;

public class Grid {

	
	private String[][] grid;
	private boolean canNotBePlaced = false;
	private int id;
	private boolean touche;
	private boolean victory = false;
	private boolean legit = false;


	
	public Grid(String[][] grid) {
		super();
		this.grid = grid;
		for(int i=0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = ".";
			}
		}
	}
	public String[][] getGrid() {
		return grid;
	}
	public void setGrid(String[][] grid) {
		this.grid = grid;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isVictory() {
		return victory;
	}
	public void setVictory(boolean victory) {
		this.victory = victory;
	}
	public boolean isTouche() {
		return touche;
	}
	public void setTouche(boolean touche) {
		this.touche = touche;
	}
	
	
	
	public boolean isLegit() {
		return legit;
	}
	
	public void setLegit(boolean legit) {
		this.legit = legit;
	}
	
	
	/**
	 * Affiche la grille de jeu
	 * 
	 * @param out : la sortie où afficher la grille
	 */
	public void affiche(PrintWriter out) {
		for(int i=0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				out.print(grid[i][j] + " ");
			}
			out.print("\n");
		}
	}
	
	
	/**
	 * Permet de choisir l'emplacement d'un bateau et de le placer
	 * 
	 * @param in : l'entrée des coordonnées se passe par ici
	 * @param out : la prise d'informations se passe ici
	 * @param boat : le bateau concerné par le choix de l'emplacement
	 */
	public void choix(BufferedReader in, PrintWriter out, Boat boat) {
		while(!this.isLegit()) {
			if(boat.getSizeBoat() == 1) out.println("Bateau de 1 case à placer");
			else out.println("Bateau de " + boat.getSizeBoat() + " à placer.");
			out.println("Placer où ?");
			out.println("\nx : ");
			String coordX; String coordY;
			try {
				coordX = in.readLine();
				int x = Integer.parseInt(coordX);
				out.println("y : ");
				coordY = in.readLine();
				int y = Integer.parseInt(coordY);
				out.println("Horizontalement ou verticalement ? [H/V]");
				String orientation = in.readLine();
				boolean isHorizontal=true;
				if(orientation.equals("H") || orientation.equals("h")) isHorizontal = true;
				else if(orientation.equals("V") || orientation.equals("v")) isHorizontal = false;
				else out.println("Indication non reconnue : bateau aligné horizontalement par défaut.");
				
				out.println("J" + this.getId() + " : placement d'un bateau taille "
				+ boat.getSizeBoat()+ " en [" + coordX + ";" + coordY + "]");
				
				this.placement(x, y, boat.getSizeBoat(), isHorizontal);
				this.setLegit(true);
				this.affiche(out);
			} catch (Exception e) {
				out.println("Emplacement non reconnu. Veuillez recommencer.");
			} 
		}
		
		
		
		
		
	}
	
	/**
	 * Place un bateau sur la grille
	 * 
	 * @param x : la valeur en x de la première case du bateau
	 * @param y : la valeur en y de la première case du bateau
	 * @param size : la taille du bateau
	 * @param horizontal : un booléen qui renvoie true si le bateau est placé à l'horizontale
	 */
	public void placement(int x, int y, int size, boolean horizontal) {
		
			if(horizontal) {
				for(int i = x; i < x + size; i++) {
					if(isUsedCase(i, y)) {
						System.out.println("Emplacement [" + i + ";" + y + "] déjà utilisé");
						canNotBePlaced = true;
						break;
					}
				}
				for(int i = x; i < x + size; i++) {
					if(!canNotBePlaced) this.grid[y - 1][i - 1] = "o";
				}
			}
			else {
				for(int i = y; i < y + size; i++) {
					if(isUsedCase(x, i)) {
						System.out.println("Emplacement [" + x + ";" + i + "] déjà utilisé");
						canNotBePlaced = true;
						break;
					}
				}
				for(int i = y; i < y + size; i++) {
					if(!canNotBePlaced) this.grid[i - 1][x - 1] = "o";
				}
			}
		
	}
	
	
	/**
	 * Traite l'attaque du joueur
	 * 
	 * @param adverse : l'adversaire qui va subir l'attaque
	 * @param x : la valeur en x de l'attaque
	 * @param y : la valeur en y de l'attaque
	 * @param vueAdverse : le joueur attaquant va avoir connaissance de cette vue
	 */
	public void attaque(Grid adverse, int x, int y, Grid vueAdverse) {
		

				if(adverse.isUsedCase(x, y)) {
					this.setTouche(true);
					adverse.grid[y - 1][x - 1] = "+";
					vueAdverse.grid[y - 1][x - 1] = "+";

				}
				else {
					this.setTouche(false);
					adverse.grid[y - 1][x - 1] = "-";
					vueAdverse.grid[y - 1][x - 1] = "-";

				}
			
			
		
		
	}
	
	//Affiche un message en fonction du résultat de votre attaque
	public String messageTouche(Grid adverse) {
		if(this.isTouche()) return "Vous avez touché !";
		else if(!this.isTouche()) return "Vous avez manqué votre cible !";
		return null;
	}
	
	
	//Vérifie si une case est occupée ou non
	public boolean isUsedCase(int x, int y) {
		return grid[y - 1][x - 1].equals("o");
	}
	
	//Vérifie si un joueur a gagné
	public void checkVictory(Grid adverse) {
		int count = 0;
		for(int i=0; i < adverse.grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(adverse.grid[i][j].equals("o")) count++;
			}
		}
		if (count == 0) this.setVictory(true);
	}
	
}
