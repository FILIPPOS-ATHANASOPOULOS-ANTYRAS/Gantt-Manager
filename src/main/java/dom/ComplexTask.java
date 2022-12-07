package dom;

import java.util.ArrayList;

public class ComplexTask extends Task{
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public ComplexTask(int ID, String name, int mamaID, ArrayList<Task> tasks) {
		super(ID, name, mamaID);
		this.tasks = tasks;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String toString() {
		return "Complex Task Task \"" + name + "\" " + String.valueOf(start) + "," + String.valueOf(end) + "," + String.valueOf(cost) + "," + String.valueOf(tasks.size());
	}
}
