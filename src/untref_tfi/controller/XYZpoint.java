package untref_tfi.controller;

public class XYZpoint {
	
	private int xValue=0;
	private int yValue=0;
	private double zValue=0.0;
	
	public XYZpoint(Integer xPos, Integer yPos, Double zPos){
		this.xValue=xPos;
		this.yValue=yPos;
		this.zValue=zPos;
	}

	public int getXvalue() {
		return xValue;
	}

	public int getYvalue() {
		return yValue;
	}
	
	public double getZvalue() {
		return zValue;
	}
	
	public boolean isZeroOrigin(){
		return (xValue==0)&&(yValue==0)&&(zValue==0.0);
	}
}
