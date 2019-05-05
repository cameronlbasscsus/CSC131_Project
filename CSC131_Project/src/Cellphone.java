public class Cellphone {
	
	/*This class is responsible for reporting item tags to the server. 
	 * The cell phone is given random coordinates and an ItemTag's
	 * information is relayed. To simulate finding the item the distance
	 * between the item and the cell phone is calculated. If the distance
	 * is within the limit the item is considered found and the server is 
	 * updated with the tag's information.
	 */
	
	private double GPSLat;
	private double GPSLon;
	private double proximity;
	
	public void getTagInfo(String IT, Server s) {
	
		//Relay tag data to server 
		//call report tag in server
		//with the tag info from item tag
			
		GPSLat = -90.0 + Math.random() * 180.0;
		GPSLon = -180.0 + Math.random() * 360;					
		
		//.trim() to take care of a leading/ending space just in case
		//.split puts IT's data (ID LAT LON) into an array
		String[] idInfo = IT.trim().split(" ");
//		for(String e : idInfo) {
//			System.out.println("Debug: " + e);
//		}
		
		if(idInfo[0] == null ) {		//if theres no id tag
			System.out.println("Error: No Item Tag\n");
		}
		else if(idInfo[0] != null){
			double tagLat = Double.parseDouble(idInfo[1]);
			double tagLon = Double.parseDouble(idInfo[2]);
			
			proximity = disToTag(GPSLat, GPSLon, tagLat, tagLon);
			
			//need to test what values are "close"
			if(proximity != 0){
				//tag is close, update server
				
				s.ReportTag(IT);		//object from dostuff.java
				
			}
		}
		
		
	}
	//find distance between cellphone and tag
	//This function uses the haversine formula to calculate distance over the Earth's surface
	//the distance is given as a straight line
	private double disToTag(double CellLat, double CellLon, double TagLat, double TagLon) {
		
		int earthRadiusKm = 6371;

		double dLat = Math.toRadians(CellLat - TagLat);
		double dLon = Math.toRadians(CellLon - TagLon);

		  CellLat = Math.toRadians(CellLat);
		  TagLat = Math.toRadians(TagLat);

	    double  a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		          Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(CellLat) * Math.cos(TagLat); 
		double  c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return earthRadiusKm * c;
		
	}
	
	
}