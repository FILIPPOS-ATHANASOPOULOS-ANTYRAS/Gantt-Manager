package dom;

import java.util.ArrayList;

public class TaskFactory {
	
	// Invalid Cases:
	// ID <= 0
	// MamaID = ID
	// Name = null
	// End < Start
	// Cost <= 0
	public SimpleTask createSimpleTask(int id, String name, int mamaID, int start, int end, int cost) throws Exception{
		if ( (id <= 0) || (mamaID == id) ) {
			throw new Exception("Invalid ID");
		}
		if (name == null) {
			throw new Exception("Invalid name");
		}
		if (end < start) {
			throw new Exception("Invalid duration");
		}
		if (cost <= 0) {
			throw new Exception("Invalid cost");
		}
		SimpleTask simpleTask = new SimpleTask(id, name, mamaID, start, end, cost);
		return simpleTask;
	}
	
	
	public ComplexTask createComplexTask(int ID, String name, int mamaID, ArrayList<Task> tasks) throws Exception {
		
		if ( (ID <= 0) || (mamaID == ID) ) {
			throw new Exception("Invalid ID");
		}
		if (name == null) {
			throw new Exception("Invalid name");
		}
		if (tasks.size() == 0) {
			throw new Exception("Can't Create Complex Task Without Subtasks");
		}
		
		return new ComplexTask(ID, name, mamaID, tasks);
	}
}
