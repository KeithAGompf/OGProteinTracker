package proteintracker;

public class Chicken implements Meat_Interface{
	public int meat_protein() {
		System.out.println("inside chicken");
		return 50;
	}
}
