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
	
	String ServerDataFile = "db.txt";				//database file
	public boolean create(String email, int TagID, String ItemDescription) {
		/* Part of database CRUD, Create will create a new record consisting 
		 * of "<String: email> <int: TagID> <String: ItemDescription>"
		 * with the requirement that the TagID is unique. If the given TagID
		 * is not unique, the record will not be added and return false.
		 * Default val of ItemDescription is preferred to be "".
		 */
		//System.out.println("Log: " + "create email id desc");
		String Record = email + " " + Integer.toString(TagID) + " " + ItemDescription;
		if(read(TagID) == null) {	//if there isn't a matching record
			try {
				//System.out.println("Log: " + "Appending Record to DB file.");
				BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile,true)); // Open file for writing in Append mode
				fout.write(Record);		//writes <email> + <ID> + <description>
				fout.write(13);			//essentially \r
				fout.write(10);			//essentially \n
				fout.flush();			//flushes output stream
				fout.close();			//closes writing to file
				//adds a fun little success message
				System.out.println("Item ID " + TagID + "registered under email " + email);
				return true;			//writing record to file worked
			} catch(Exception e) {
				System.out.println("Exception writing: " + e.getMessage());
			}
		}
		System.out.println("Item is already in system!");
		return false;
	}
	
	public String read(int TagID) {
		/* Part of databse CRUD, Read will find a record with a matching TagID.
		 * The search will ignore sign, and therefore will ignore whether the TagID
		 * indicates the status of the item. If there is no matching record, 
		 * a null value will be returned.
		 */
		//System.out.println("Log: " + "read id");
		String Temp, rval=null;
		String[] Temps;
		//Read all entries, scan for existing tag. If tag does not exist, return null.
		try {
			//System.out.println("Log: " + "Reading DB file.");
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			while((Temp=fin.readLine()) != null){	//the line has stuff
				Temps = Temp.split(" ");			//array of strings of the line info
				//if there is a tag id and its not lost, set record val to Temp (line info)
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
		/* Part of databse CRUD, Update will change the sign on an indicated TagID.
		 * A negative value indicates that the item has been reported lost, whereas
		 * a positive value indicates that the item has not been reported lost or
		 * was reported as recovered.
		 */
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;			//will be line of info (email+ID+description)
		String[] Temps;
		int dummyTagID;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			System.out.println("Scanning records for item...");
			while((Temp=fin.readLine()) != null){	//while the line has stuff
				Temps = Temp.split(" ");			//split into Temps
				//if ID!=null and item is not lost
				if(Temps[1] != null && Math.abs(TagID) == Math.abs((dummyTagID = Integer.parseInt(Temps[1])))) {
					//if Lost, set ID to neg, else set ID to pos
					Temps[1] = IsLost ? Integer.toString(-dummyTagID) : Integer.toString(dummyTagID) ;
					//sets Temp to Temps array but with spaces between each item
					Temp = String.join(" ", Temps);
					System.out.println("Updated status of " + TagID + " to lost! :(");
				}
				Records.add(Temp);	//add lost item to records
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
		/* Part of database CRUD, Delete removes a record from the database.
		 */
		//System.out.println("Log: " + "delete");
		// Read all records for the target record
		LinkedList<String> Records = new LinkedList<String>();
		String Temp;
		String[] Temps;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(ServerDataFile));
			//System.out.println("Log: " + "delete scanning records");
			while((Temp=fin.readLine()) != null){
				Temps = Temp.split(" ");
				//while id is valid and item is lost
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
		// Helper function for update and delete.
		//System.out.println("Log: " + "writeback");
		// Write back all records with the modified/deleted record
		try {
			BufferedWriter fout = new BufferedWriter(new FileWriter(ServerDataFile));
			//System.out.println("Log: " + "writeback of db with modification");
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
		/* Part of the Server utility functions,
		 * ReportTag is invoked by a cellphone that has received a tag transmission.
		 * The TagID is processed, and if the tag has been reported missing,
		 * an email with the relevant information is sent.
		 */
		//System.out.println("Log: " + "report tag");
		String[] Info = TagInfo.split(" "); // Parse Info "<ID> <GPSLat> <GPSLon>"
		int foundTagID = Integer.parseInt(Info[0]);
		String Record = read(foundTagID);		//checks if valid item
		String[] RecordInfo = Record.split(" ");
		String email = RecordInfo[0];
		RecordInfo[0]="";	//clear record
		int TagID = Integer.parseInt(RecordInfo[1]);
		RecordInfo[1]="";	//clear record
		String ItemDescription = String.join(" ", RecordInfo).trim(); 
		if(TagID < 0) { // If item record indicates the item is lost, send report email.
			System.out.println("Item found by a Finder Cellphone!");
			System.out.println("Emailing " + email + " that " + ItemDescription + " was found!");
		}
	}
}