package dom;

import java.util.ArrayList;

public abstract class Diagram {
	
	public ArrayList<Task> tasks = new ArrayList<Task>(); // format [100,101,102,103,200,201,202,300,301....]
	
	public ArrayList<Task> getAllTasks() {
		return tasks;
	}
	
	public abstract Diagram orderTasks();
}
