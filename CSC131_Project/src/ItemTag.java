public class ItemTag {
	private int ID;
	private double GPSLat;
	private double GPSLon;
	//Startup
	public ItemTag(int ID) {
		this.ID=ID;
		this.GPSLat = -90.0 + Math.random() * 180.0;
		this.GPSLon = -180.0 + Math.random() * 360;
	}
	//Shutdown
	//Transmit info
	public String Transmit() {
		return Integer.toString(ID).concat(" " + Double.toString(GPSLat).concat(" " + Double.toString(GPSLon)));
		//Returns "<ID> <GPSLat> <GPSLon>"
	}
}