import java.io.*;
import java.util.*;
public class Server {
	public static void main(String[] args) {
		Server s = new Server();
		s.create("ass@butts.com",1234,"blue jacket");
	}
	String ServerDataFile = "db.txt";
	// CRUD: Create, Read, Update, Delete
	//Create: Register new tag with owner info.
	void create(String email, int TagID) {
		create(email, TagID, "");
	}
	void create(String email, int TagID, String ItemDescription) {
		String Record = email + " " + Integer.toString(TagID) + " " + ItemDescription;
		String Temp;
		String[] Temps;
		LinkedList<String> Records = new LinkedList<String>();
		boolean TagPresent = false;
		//Read all entries, scan for existing tag. If tag does not exist, append new record to file.
		try {
			System.out.println("Log: " + "Reading DB file.");
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){
				Records.add(Temp);
				Temps = Temp.split(" ");
				if(Temps[1] != null) TagPresent = (Math.abs(TagID) == Math.abs(Integer.parseInt(Temps[1])));
			}
			fin.close();
		} catch (Exception e) {
			System.out.println("Exception reading: " + e.getMessage());
		}
		if(!TagPresent) {
			try {
				System.out.println("Log: " + "Appending Record to DB file.");
				BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile,true));
				fout.write(Record);
				fout.write(13); // 
				fout.write(10);
				fout.close();
			} catch(Exception e) {
				System.out.println("Exception writing: " + e.getMessage());
				return;
			}
		}
	}
	//Read: Read info about a tag
	String read(int TagID) {
		String Temp, rval=null;
		String[] Temps;
		//Read all entries, scan for existing tag. If tag does not exist, return null.
		try {
			System.out.println("Log: " + "Reading DB file.");
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){
				Temps = Temp.split(" ");
				if(Temps[1] != null) if(Math.abs(TagID) == Math.abs(Integer.parseInt(Temps[1]))) rval=Temp;
			}
			fin.close();
		} catch (Exception e) {
			System.out.println("Exception reading: " + e.getMessage());
		}
		return rval;
	}
	//Update: Update tag status
	//Delete: Delete tag from database
	void ReportTag(int TagID) {
		
	}
}