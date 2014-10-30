package clueGame;

import java.io.*;
import java.util.*;


public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException(String message){
		super(message);
		writeToLog();
	}
	
	
	// Write error to error log file
	private void writeToLog(){
		Date errorDate = new Date();
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("resources/logfile.txt",true));
			out.append(errorDate + " " + this.getLocalizedMessage() +"\n");
		} catch (Exception e) {
			System.out.println( e.getLocalizedMessage());
		}
		finally{
			out.close();
		}
	}
}
