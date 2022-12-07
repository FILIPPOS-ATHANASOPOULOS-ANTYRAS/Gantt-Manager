package dom;

import java.util.ArrayList;

public class GanttDiagram extends Diagram {

	public GanttDiagram(ArrayList<Task> taskList) {
		this.tasks = taskList;
	}

	@Override
	public Diagram orderTasks() {
		//System.out.println("TESTING");
		for (Task task : tasks) { // loop all tasks once to set start, end, cost to complex tasks
			if (task instanceof ComplexTask) { //if task is of type ComplexTask
				//System.out.println("TASK is complex task");
				ComplexTask complex = (ComplexTask) task; //start calculating start, end, cost
				int tempStart = -1, tempEnd = -1, tempCost = 0; //init tempStart, tempEnd to NULL, and cost to 0
				//System.out.print("Entering for... ");
				//System.out.println(task);
				for (Task subtask : complex.tasks) {
					//System.out.println("Calculating its values...");
					tempCost += subtask.getCost();
					if (subtask.getStart() < tempStart || tempStart == -1) tempStart = subtask.getStart(); // if current task start is smaller than current value, decrease it
					if (subtask.getEnd() > tempEnd || tempEnd == -1) tempEnd = subtask.getEnd(); // if current task end is bigger than current value, increase it
				}
				//System.out.println(tempStart);
				//System.out.println(tempEnd);
				complex.setStart(tempStart); // put finalized values in complex task
				complex.setEnd(tempEnd);
				complex.setCost(tempCost);
			}
		}// we now have the start, end values of every task, we can start sorting
		//redo this
		// System.out.println();
		ArrayList<Task> tempTasks = new ArrayList<Task>(); // create TempTask list, this is what we "return"
		while (tasks.size() > 0) { // sort
			Task currentLowest = tasks.get(0);
			int tempMamaID = 0;
			for (Task task : tasks) { // find the top level task with the smallest start date, cant do for each because I want the index if the element for remove
				//if (task.getID() != 0) continue;
				if (currentLowest.getMamaID() != 0 && task.getMamaID() == 0 ) currentLowest = task; //if current task is not top level replace it immediately
				if ( task.getStart() < currentLowest.getStart() && task.getMamaID() == 0) currentLowest = task; // if there is another top level task with lower start put that
				if ( task.getStart() == currentLowest.getStart() && task.getID() < currentLowest.getID()) currentLowest = task; // if we have a tie and the new task has lower ID then that one wins
				
			}
			//System.out.println("Lowest Task: " + currentLowest);
			//System.out.println("Now getting its sub tasks...");
			tempTasks.add(currentLowest); // add the task to temp task
			tempMamaID = currentLowest.getID(); // 
			tasks.remove(tasks.indexOf(currentLowest)); // remove it from the actual list
			if (tasks.size() > 0) currentLowest = tasks.get(0);
			for (int j = 0; j < tasks.size(); j++) { // put all its sub tasks in order
				for (Task task : tasks) { // find the sub task with the smallest start date
					if (task.getMamaID() != tempMamaID) continue;
					if (currentLowest.getMamaID() != tempMamaID ) currentLowest = task;
					if (task.getStart() < currentLowest.getStart() ) currentLowest = task;
					if (task.getStart() == currentLowest.getStart() && task.getID() < currentLowest.getID()) currentLowest = task;
				}
				if (currentLowest.getMamaID() == tempMamaID) {
					tempTasks.add(currentLowest);
					//System.out.println("Lowest SubTask: " + currentLowest);
					tasks.remove(tasks.indexOf(currentLowest)); 
				}
				if (tasks.size() > 0) currentLowest = tasks.get(0); // reset
			}
			
		}
		Diagram resultDiagram = new GanttDiagram(tempTasks);
		//System.out.println("PRINTING ALL TASKS");
		//((GanttDiagram) resultDiagram).printTasks();
		return resultDiagram;
	}
	
	public String toString() {
		return "Diagram with " + this.getAllTasks().size() + " tasks";
	}
	
	public void printTasks() {
		for (Task task : tasks) System.out.println(task);
	}
}
