import java.io.*;
import java.util.*;
public class Server {
	public static void main(String[] args) {
		Server s = new Server();
		s.create("cameronlbass@csus.edu", 1234, "red jacket");
		s.create("ass@butts.com", 1234, "blue jacket"); // Will not register identical item tags
	}
	String ServerDataFile = "db.txt";
	// CRUD: Create, Read, Update, Delete
	//Create: Register new tag with owner info.
	public void create(String email, int TagID) {
		create(email, TagID, "");
	}
	public void create(String email, int TagID, String ItemDescription) {
	// Read all entries, scan for existing tag and quit if found. If tag does not exist, append new record to file.
		String Record = email + " " + Integer.toString(TagID) + " " + ItemDescription;
		if(read(TagID) == null) {
			try {
				System.out.println("Log: " + "Appending Record to DB file.");
				BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile,true)); // Open file for writing in Append mode
				fout.write(Record);
				fout.write(13);
				fout.write(10);
				fout.flush();
				fout.close();
			} catch(Exception e) {
				System.out.println("Exception writing: " + e.getMessage());
			}
		}
	}
	//Read: Read info about a tag
	public String read(int TagID) {
		String Temp, rval=null;
		String[] Temps;
		//Read all entries, scan for existing tag. If tag does not exist, return null.
		try {
			System.out.println("Log: " + "Reading DB file.");
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){
				Temps = Temp.split(" ");
				if(Temps[1] != null && Math.abs(TagID) == Math.abs(Integer.parseInt(Temps[1]))) rval=Temp;
			}
			fin.close();
		} catch (Exception e) {
			System.out.println("Exception reading: " + e.getMessage());
		}
		return rval;
	}
	//Update: Update tag status
	public void update(int TagID, boolean IsLost) {
	// Update the record of TagID, where a negative TagID flags a status of "lost"
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;
		String[] Temps;
		int dummyTagID;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){
				Temps = Temp.split(" ");
				if(Temps[1] != null && Math.abs(TagID) == Math.abs((dummyTagID = Integer.parseInt(Temps[1])))) {
					Temps[1] = IsLost ? Integer.toString(-dummyTagID) : Integer.toString(dummyTagID) ;
					Temp = String.join(" ", Temps);
				}
				Records.add(Temp);
			}
			fin.close();
		} catch (Exception e) {
			System.out.println("Exception reading: " + e.getMessage());
		}
		// Write back all records with the modified record
		writeback(Records);
	}
	//Delete: Delete tag from database
	public void delete(int TagID) {
	// Removes the record indicated by TagID
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;
		String[] Temps;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){
				Temps = Temp.split(" ");
				if(Temps[1] != null && Math.abs(TagID) != Math.abs(Integer.parseInt(Temps[1]))) {
					//Record to be deleted is not added
					Records.add(Temp);
				}
			}
			fin.close();
		} catch (Exception e) {
			System.out.println("Exception reading: " + e.getMessage());
		}
		// Write back all records except the modified record
		writeback(Records);
	}
	private void writeback(LinkedList<String> Records) {
	// Utility function for update and delete.
		// Write back all records with the modified/deleted record
		try {
			System.out.println("Log: " + "Appending Record to DB file.");
			BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile));
			for(String Record : Records) {
				fout.write(Record);
				fout.write(13);
				fout.write(10);
			}
			fout.flush();
			fout.close();
		} catch(Exception e) {
			System.out.println("Exception writing: " + e.getMessage());
		}
	}
	public void ReportTag(String TagInfo) {
	// Given some information from a tag "<ID> <GPSLat> <GPSLon>", will send an email with GPS for the relevant Tag ID.
		String[] Info = TagInfo.split(" "); // Parse Info "<ID> <GPSLat> <GPSLon>"
		int TagID = Integer.parseInt(Info[0]);
		String Record = read(Integer.parseInt(Info[0]));
		String[] RecordInfo = Record.split(" ");
		if(true) { // If item record indicates the item is lost, send report email.
			System.out.println("Emailing " + RecordInfo[0] + " that ... " );
		}
	}
	//this is a test comment from Sean to test commit 123 test 
}