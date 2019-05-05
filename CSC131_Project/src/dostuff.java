public class dostuff {
	public static void main(String[] args) {
		for(String s : args) {
			System.out.print(s + " ");
		}
		
		//create server
		Server s = new Server();
		//registering item
		System.out.println("Registering item...");
		s.create("cameronlbass@csus.edu", 1234, "blue jacket");
		//report item as lost
		s.update(1234, true);
		//new finder cellphone entity
		Cellphone c = new Cellphone();
		c.getTagInfo((new ItemTag(1234)).Transmit(), s);
		//users can add ids to server
		//user can notify server an item is lost
		//finder cellphone finds tagid (use Transmit and getTagInfo)
		//use cellphone.java to notify server
		//server checks database if valid info
		//if valid, update database and notify owner with an email
		
	}
}