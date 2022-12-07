package parser;

import java.util.*;
import dom.*;

import java.io.*;

public class SimpleTextParser implements IParser {
    public Diagram parse(String fileName , String delimiter){
    	TaskFactory taskFactory = new TaskFactory();

        ArrayList<Task> taskList = new ArrayList<Task>();
        ArrayList<Task> simpleTasks =  new ArrayList<Task>();

		//Find Simple tasks 
        File file = new File(fileName);
        try (Scanner input = new Scanner(file)) {
			while (input.hasNextLine()){
				 String[] taskValues = input.nextLine().split(delimiter);
				 if(taskValues.length == 6){
					SimpleTask simpleTask = null;
					try {
						simpleTask = taskFactory.createSimpleTask(Integer.parseInt(taskValues[0]), taskValues[1], Integer.parseInt(taskValues[2]), Integer.parseInt(taskValues[3]), Integer.parseInt(taskValues[4]), Integer.parseInt(taskValues[5]));
					} catch (Exception e) {
						System.out.println(e);
						System.exit(1);
					}
			         simpleTasks.add(simpleTask);
			         taskList.add(simpleTask);
				 }
			} 
		} catch (NumberFormatException | FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

		//Find complex tasks
		try (Scanner input = new Scanner(file)) {
			while (input.hasNextLine()){
				String[] taskValues = input.nextLine().split(delimiter);
				if (taskValues.length != 6){
					int tempMamaID = Integer.parseInt(taskValues[0]);
					
					ArrayList<Task> subTasks =  new ArrayList<Task>();
					for (Task task : simpleTasks) {
						if (task.getMamaID() == tempMamaID) {       				
							subTasks.add(task);
						}
					}
					ComplexTask complexTask = null;
					try {
						complexTask = taskFactory.createComplexTask(Integer.parseInt(taskValues[0]),taskValues[1],0,subTasks);
					} catch (Exception e) {
						System.out.println(e);
						System.exit(1);
					} 
					taskList.add(complexTask);
				}
				//Simple Task with mamaId 0
				else if (taskValues.length != 6 && Integer.parseInt(taskValues[0]) == 0){
					Task simpleTask = new SimpleTask(Integer.parseInt(taskValues[0]), taskValues[1], Integer.parseInt(taskValues[2]), Integer.parseInt(taskValues[3]), Integer.parseInt(taskValues[4]), Integer.parseInt(taskValues[5]));
					taskList.add(simpleTask);
				}
			}
		} catch (NumberFormatException | FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

		//create diagram and return
        Diagram resultDiagram = new GanttDiagram(taskList);
        return resultDiagram;

    }
}
