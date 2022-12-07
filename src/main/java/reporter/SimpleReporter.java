package reporter;

import backend.ReportType;
import java.io.*;
//import dom.*;
import dom2app.*;

public class SimpleReporter implements IReporter {

	@Override
	public void report(String path, ReportType type, SimpleTableModel table) throws IOException {
		FileWriter file = new FileWriter(path);
		if (type == ReportType.TEXT) createTXTreport(file, type, table);		
		else if (type == ReportType.MD) createMDreport(file, type, table);	
		else if (type == ReportType.HTML) createHTMLreport(file, type, table);	
		file.close();
	}


	
    void createTXTreport(FileWriter file, ReportType type, SimpleTableModel table)throws IOException{
		for (String name : table.getColumnNames()) file.write(name + "\t");
		file.write("\n");
		for (String[] line : table.getData()){
			for (String word : line) file.write(word + "\t");
			file.write("\n");
		}
	}

	void createMDreport(FileWriter file, ReportType type, SimpleTableModel table)throws IOException{
		for (String name : table.getColumnNames()) file.write("*" + name + "*\t");
		file.write("\n");
		for (String[] line : table.getData()){
			if (Integer.parseInt(line[2]) == 0) for (String word : line) file.write("**" + word + "**\t");
			else for (String word : line) file.write(word + "\t");
			file.write("\n");
		}
	}

	void createHTMLreport(FileWriter file, ReportType type, SimpleTableModel table)throws IOException{
		file.write("<!doctype html>\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content\"text/html; charset=windows-1253\">\n");
		file.write("<title>Gantt Project Data</title>\n</head>\n<body>\n\n<table>\n<tr>\n");
		for (String name : table.getColumnNames()) file.write("<td>" + name + "</td>\t");
		file.write("</tr>\n\n");
		for (String[] line : table.getData()){
			file.write("<tr>\n");
			for (String word : line) file.write( "<td>" + word + "</td>\t");
			file.write("</tr>\n\n");
		}
		file.write("</table></body>\n</html>");
	}
		
		
}
