import java.io.*;
import java.util.*;
public class Server {
//	public static void main(String[] args) {
//		Server s = new Server();
//		System.out.println("Log: " + "create 1234");
//		s.create("cameronlbass@csus.edu", 1234, "red jacket");
//		System.out.println("Log: " + "create 1234 bad reg");
//		s.create("blackhat@bad.site", 1234, "blue jacket"); // Will not register identical item tags
//		System.out.println("Log: " + "create 1111");
//		s.create("cameronlbass@csus.edu", 1111, "dell 6000 laptop");
//		System.out.println("Log: " + "report 1234 lost");
//		s.update(1234, true); // Item ID 1234 reported missing
//		System.out.println("Log: " + "report 1234 found");
//		s.update(1234, false); // Item ID 1234 reported found
//		System.out.println("Log: " + "delete 1234");
//		s.delete(1234); // Item ID 1234 tag expired? 
//		
//	}
	String ServerDataFile = "db.txt";
	// CRUD: Create, Read, Update, Delete
	//Create: Register new tag with owner info.
	public void create(String email, int TagID) {
		System.out.println("Log: " + "create email id");
		create(email, TagID, "");
	}
	public void create(String email, int TagID, String ItemDescription) {
	// Read all entries, scan for existing tag and quit if found. If tag does not exist, append new record to file.
		System.out.println("Log: " + "create email id desc");
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
		System.out.println("Log: " + "read id");
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
		System.out.println("Log: " + "update");
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;
		String[] Temps;
		int dummyTagID;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			System.out.println("Log: " + "update scanning records");
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
		System.out.println("Log: " + "delete");
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;
		String[] Temps;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			System.out.println("Log: " + "delete scanning records");
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
		System.out.println("Log: " + "writeback");
		// Write back all records with the modified/deleted record
		try {
			BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile));
			System.out.println("Log: " + "writeback of db with modification");
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
		int foundTagID = Integer.parseInt(Info[0]);
		String Record = read(foundTagID);
		String[] RecordInfo = Record.split(" ");
		String email = RecordInfo[0];
		RecordInfo[0]="";
		int TagID = Integer.parseInt(RecordInfo[1]);
		RecordInfo[1]="";
		String ItemDescription = String.join(" ", RecordInfo).trim(); 
		if(TagID < 0) { // If item record indicates the item is lost, send report email.
			System.out.println("Emailing " + email + " that " + ItemDescription + " was found.");
		}
	}
	//this is a test comment from Sean to test commit 123 test 
}