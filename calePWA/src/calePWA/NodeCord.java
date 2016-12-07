package calePWA;

public class NodeCord {
	
	private int nodeNum;
	private double xCord;
	private double yCord;
	private double zCord;
	
	public NodeCord(int nodeNum, double xCord, double yCord, double zCord) {
		super();
		this.nodeNum = nodeNum;
		this.xCord = xCord;
		this.yCord = yCord;
		this.zCord = zCord;
	}
	public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	public double getxCord() {
		return xCord;
	}
	public void setxCord(double xCord) {
		this.xCord = xCord;
	}
	public double getyCord() {
		return yCord;
	}
	public void setyCord(double yCord) {
		this.yCord = yCord;
	}
	public double getzCord() {
		return zCord;
	}
	public void setzCord(double zCord) {
		this.zCord = zCord;
	}

	
}
