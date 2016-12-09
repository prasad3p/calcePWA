package dataStructures;

public class Part {

	private String id;
	private double len;
	private double wid;
	
	public Part(String id, double len, double wid) {
		
		this.id = id;
		this.len = len;
		this.wid = wid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getLen() {
		return len;
	}
	public void setLen(double len) {
		this.len = len;
	}
	public double getWid() {
		return wid;
	}
	public void setWid(double wid) {
		this.wid = wid;
	}
	
}
