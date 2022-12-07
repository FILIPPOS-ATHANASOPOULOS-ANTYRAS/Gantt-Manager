package backend;

import java.io.IOException;
import java.util.ArrayList;

import reporter.*;
import dom.*;


import dom2app.SimpleTableModel;
import parser.*;

public class MainController implements IMainController{
	
	GanttDiagram ganttDiagram = null;
	
	public void setGanttDiagram(GanttDiagram ganttDiagram) {
		this.ganttDiagram = ganttDiagram;
		
	}

	/**
	 * Loads a delimited file in the prescribed format and represents
	 * its contents (i.e., a Gantt project's tasks) as objects in main memory
	 * Each row is a task; fields are separated by a delimiter
	 * The following columns are implicitly assummed: 
	 * COLUMN_NAMES = {"TaskId" , "TaskText", "MamaId","Start" , "End" , "Cost" }
	 * 
	 * @param fileName A path of the location of the tsv file
	 * @param delimiter A string that separates the columns of each row
	 * @return a SimpleTableModel (@see dom2app.SimpleTableModel) that represents the project tasks as a List of string arrays
	 */
	public SimpleTableModel load(String fileName, String delimiter) {
		
//		if (fileName == null || delimiter == null) { //null or \t??
//			String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
//			return createTableModel("All Tasks", names, new ArrayList<Task>());
//		}
		
        // -------------this is new-------------
		if (delimiter != "\t"){
			String[] names = {"Not a tsv file"};
			ArrayList<Task> error = new ArrayList<Task>();
			SimpleTask emptyRaster = new SimpleTask(100, fileName, 1, 1, 1, 1);
			error.add(emptyRaster);

			return createTableModel("Error!", names, error);
		}
		// -------------------------------------
		
		ParserFactory pFactory = new ParserFactory();
		IParser parser = pFactory.createParser();

		ganttDiagram = (GanttDiagram) parser.parse(fileName, delimiter);
		
		if (ganttDiagram.getAllTasks().size() == 0) {
			String[] names = {"No Tasks Found"};
			return createTableModel("All Tasks", names, ganttDiagram.getAllTasks());
		}
		
		ganttDiagram = (GanttDiagram) ganttDiagram.orderTasks();
		
		
		String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
		return createTableModel("All Tasks", names, ganttDiagram.getAllTasks());
	}

	/**
	 * Assuming a Gantt project has been loaded, it returns all the tasks whose TaskTest is prefixed by the method's argument
	 * 
	 * @param prefix A string with the prefix of the task description that we are looking for.
	 * @return a SimpleTableModel (@see dom2app.SimpleTableModel) that represents the retrieved tasks as a List of string arrays
	 */
	
	public SimpleTableModel getTasksByPrefix(String prefix){
		ArrayList<Task> ganttList = new ArrayList<Task>();
		if (ganttDiagram == null) {
			String[] names = {"No File has been loaded"};
			return createTableModel("No File has been loaded", names, ganttList);
		}
		for (Task task : ganttDiagram.getAllTasks()) ganttList.add(task); // Deep copy ganttDiagram to ganttList
		for (int i = 0; i<prefix.length(); i++) {
			for (Task task : ganttDiagram.getAllTasks()) {
				if (task.getName().length() > i) {
					if (task.getName().toLowerCase().charAt(i) != prefix.toLowerCase().charAt(i)) { // Uppercase and lowercase dont matter
						if (ganttList.indexOf(task) != -1) ganttList.remove(ganttList.indexOf(task));
					}
				}
			}
		}
		if (ganttList.size() == 0){
			String[] names = {"No Task Starts With That Prefix"};
			return createTableModel("No Task Starts With That Prefix", names, ganttList);
		}
		String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
		return createTableModel("Filter by Prefix", names, ganttList);
	}

	/**
	 * Assuming a Gantt project has been loaded, it returns the task whose taskId is equal to the method's argument
	 * 
	 * @param id An int with the id of the task that we are looking for.
	 * @return a SimpleTableModel (@see dom2app.SimpleTableModel) that represents the retrieved task as a List of string arrays
	 */
	public SimpleTableModel getTaskById(int id){
		ArrayList<Task> correctId = new ArrayList<Task>();
		
		if (ganttDiagram == null) { // this means no file has been loaded
			String[] names = {"No File has been loaded"};
			return createTableModel("No File has been loaded", names, correctId);
		}
		
		if (id <= 0) {
			String[] names = {"No Task Can Have ID Lesser Or Equal To Zero"};
			return createTableModel("No File has been loaded", names, correctId);
		}
		
		for (Task task : ganttDiagram.getAllTasks()) if (task.getID() == id) correctId.add(task);
		
		if (correctId.size() == 0) {
			String[] names = {"Task with given ID not found"};
			return createTableModel("No Task Found with that ID", names, correctId);
		}
		
		String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
		return createTableModel("Filter by ID", names, correctId);
		
	}

	/**
	 * Assuming a Gantt project has been loaded, it returns its top-level tasks 
	 * 
	 * @return the top-level tasks of the project as a SimpleTableModel (@see dom2app.SimpleTableModel) 
	 */
	public SimpleTableModel getTopLevelTasks(){
		ArrayList<Task> ganttList = new ArrayList<Task>();
		if (ganttDiagram == null) { // this means no file has been loaded
			String[] names = {"No File has been loaded"};
			return createTableModel("No File has been loaded", names, ganttList);
		}
		String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
		for (Task task : ganttDiagram.getAllTasks()) if (task.getMamaID() == 0) ganttList.add(task);	
		return createTableModel("Top Level Tasks", names, ganttList);
	}  

	/**
	 * Assuming a Gantt project has been loaded, it creates a report in a specified format
	 * The report lists, in a sorted fashion, all the tasks of the project.
	 * 
	 * @param path The path for the filename that will be produced
	 * @param type a ReportType (@see backend.ReportType) with the types of reports that can be produced.
	 * @return the number of tasks processed for the file creation and written as lines; -1 if sth goes wrong.  
	 */
	public int createReport(String path, ReportType type){
		if (ganttDiagram == null) return -1;
		
		String[] names = {"TaskId","TaskText", "MamaId","Start","End","Cost"};
		ReporterFactory rFactory = new ReporterFactory();
		IReporter reporter = rFactory.createReporter("SimpleReporter");
		
		try {
			reporter.report(path, type, createTableModel("Report", names, ganttDiagram.getAllTasks()));
		} catch (IOException e) {
			System.out.println("Ayo this aint right tho");
			e.printStackTrace();
		}
		return ganttDiagram.getAllTasks().size();
	}
	
	private SimpleTableModel createTableModel(String name, String[] pColumnNames, ArrayList<Task> DataList){
		ArrayList<String[]> resultTable = new ArrayList<String[]>();
		for(Task task : DataList) {
			String[] lineToAdd = new String[6];
			lineToAdd[0] = Integer.toString(task.getID());
			lineToAdd[1] = task.getName();
			lineToAdd[2] = Integer.toString(task.getMamaID());
			lineToAdd[3] = Integer.toString(task.getStart());
			lineToAdd[4] = Integer.toString(task.getEnd());
			lineToAdd[5] = String.valueOf(task.getCost());
			
			resultTable.add(lineToAdd);
		}
		
		SimpleTableModel table = new SimpleTableModel(name, "Gantt Diagram", pColumnNames, resultTable);
		return table;
	}
	
}
