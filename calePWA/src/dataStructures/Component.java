package dataStructures;

public class Component {
	
	private String id;
	private Part p;
	private double xC;
	private double yC;
	private double zC;
	private String side;
	
	public Component(String id, Part p, double xC, double yC, double zC, String side) {

		this.id = id;
		this.p = p;
		this.xC = xC;
		this.yC = yC;
		this.zC = zC;
		this.side = side;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Part getP() {
		return p;
	}
	public void setP(Part p) {
		this.p = p;
	}
	public double getxC() {
		return xC;
	}
	public void setxC(double xC) {
		this.xC = xC;
	}
	public double getyC() {
		return yC;
	}
	public void setyC(double yC) {
		this.yC = yC;
	}
	public double getzC() {
		return zC;
	}
	public void setzC(double zC) {
		this.zC = zC;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}

}
