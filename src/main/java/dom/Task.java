package dom;

public abstract class Task {

	final int mamaID, ID; // The ID, mamaID will never change for any task
	int start, end; // For complex tasks these may change
	double cost;
	final String name; // Name wont change
	
	public Task(int ID, String name, int mamaID, int start, int end, int cost) { 
		this.ID = ID;
		this.mamaID = mamaID;
		this.start = start;
		this.end = end;
		this.cost = cost;
		this.name = name;
	}
	
	public Task(int ID, String name, int mamaID) { // in case of complex tasks these fields not required
		this.ID = ID;
		this.mamaID = mamaID;
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getMamaID() {
		return mamaID;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public double getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
}
