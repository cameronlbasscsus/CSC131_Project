public class Cellphone {
	//Register new tags?
	//Listen for tags
	private double GPSLat;
	private double GPSLon;
	//private double TagLoc;
	
	public void getTagInfo(String IT) {
	
	//Relay tag data to server 
	//call report tag in server
	//with the tag info from item tag
		
	GPSLat = -90.0 + Math.random() * 180.0;
	GPSLon = -180.0 + Math.random() * 360;
	
	IT = ItemTag.Transmit();
	
	String[] idInfo = IT.trim().split("\\st"); 		//to take care of a leading space
	if(idInfo[0] = null ) {
		System.out.println("Error: No Item Tag\n");
	}
	else if(idInfor[0] != null{
		
		//TagLoc = Math.sqrt(idInfo[1]^2 + idInfo[2]^2);
		proximity = disToTag(GPSLat, GPSLon, idInfo[1], idInfo[2]);
		
		//test value
		if(proximity <= 100) {
			//update server that a tag is nearby
		}
	}
	
	//Update status of a tag
}
	//find distance between cellphone and tag
	private double disToTag(double CellLat, double CellLon, double TagLat, double TagLon) {
		
		int earthRadiusKm = 6371;

		double dLat = Math.toRadians(CellLat - TagLat);
		double dLon = Math.toRadians(CellLon - TagLon);

		  CellLat = degreesToRadians(CellLat);
		  TagLat = Math.toRadians(TagLat);

	    double  a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		          Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double  c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return earthRadiusKm * c;
		
	}
	
	//change to test AGAIN
	
}