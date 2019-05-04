public class Cellphone {
	//Register new tags?
	//Listen for tags
	private double GPSLat;
	private double GPSLon;
	
	public void getTagInfo(String IT) {
	
	//Relay tag data to server 
	//call report tag in server
	//with the tag info from item tag
		
	GPSLat = -90.0 + Math.random() * 180.0;
	GPSLon = -180.0 + Math.random() * 360;
	
	IT = ItemTag.Transmit();						
	
	String[] idInfo = IT.trim().split("\\st"); 		//to take care of a leading space
	if(idInfo[0] == null ) {
		System.out.println("Error: No Item Tag\n");
	}
	else if(idInfo[0] != null){
		
		double tagLat = Double.parseDouble(idInfo[1]);
		double tagLon = Double.parseDouble(idInfo[2]);
		
		proximity = disToTag(GPSLat, GPSlon, tagLat, tagLon);
		
		//need to test what values are "close"
		if(proximity <= 100) {
			//tag is close, update server
			Server.ReportTag(IT);
			
		}
	}
	
	//Update status of a tag
}
	//find distance between cellphone and tag
	//This function uses the haversine formula to calculate distance over the Earth's surface
	//the distance is given as a straight line
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
	
	//change to test AGAIN x2
	
}