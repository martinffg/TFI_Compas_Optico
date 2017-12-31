package untref_tfi.controller;

public class XYpoint {
	
	private int xValue=0;
	private int yValue=0;
	
	public XYpoint(Integer xPos, Integer yPos){
		this.xValue=xPos;
		this.yValue=yPos;		
	}

	public int getxValue() {
		return xValue;
	}

	public int getyValue() {
		return yValue;
	}
}
