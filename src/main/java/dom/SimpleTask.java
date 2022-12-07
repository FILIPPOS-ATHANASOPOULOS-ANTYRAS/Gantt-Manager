package dom;

public class SimpleTask extends Task{
	
	//ID , NAME , MAMAID , START , END , COST
	public SimpleTask(int ID, String name, int mamaID, int start, int end, int cost) {super(ID, name, mamaID, start, end, cost);}
	
	public String toString() {
		return " Task Task \"" + name + "\" " + String.valueOf(start) + "," + String.valueOf(end) + "," + String.valueOf(cost);
	}

}
