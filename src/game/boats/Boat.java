package game.boats;

public abstract class Boat {

	private int idBoat;
	protected int sizeBoat;
	
	public Boat() {
		
	}
	
	public Boat(int idBoat) {
		super();
		this.idBoat = idBoat;
	}
	public int getIdBoat() {
		return idBoat;
	}
	public void setIdBoat(int idBoat) {
		this.idBoat = idBoat;
	}
	public int getSizeBoat() {
		return sizeBoat;
	}
	public void setSizeBoat(int sizeBoat) {
		this.sizeBoat = sizeBoat;
	}
	
	
}
