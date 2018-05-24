package untref_tfi.domain;

import untref_tfi.controller.kinect.KinectPixelLengthController;

public class XYZpoint {
	
	private int xValue=0;
	private double xLength=0.0;
	private int yValue=0;
	private double yLength=0.0;
	private double zLength=0.0;
	private AnglesCalculator angCalculator=null;
	private String colorString="";
	
	public XYZpoint(Integer xPos, Integer yPos, Double zPos,String color){
		this.xValue=xPos;
		this.yValue=yPos;
		this.zLength=zPos;
		KinectPixelLengthController pointLengthController = new KinectPixelLengthController(xPos,yPos,zPos);
		this.xLength=pointLengthController.getXLengthOfPixelsCountedInMetters();
		this.yLength=pointLengthController.getYLengthOfPixelsCountedInMetters();	
		this.angCalculator = new AnglesCalculator(this);
		this.colorString=color;
	}

	public int getXvalue() {
		return xValue;
	}

	public int getYvalue() {
		return yValue;
	}
	
	public double getXlength() {
		return xLength;
	}
	
	public double getYlength() {
		return yLength;
	}
	
	public double getZlength() {
		return zLength;
	}
	
	public boolean isZeroOrigin(){
		return (xValue==0)&&(yValue==0)&&(zLength==0.0);
	}
	
	public AnglesCalculator getAnglesCalculator(){
		return this.angCalculator;
	}
	
	public boolean isFocusablePoint() {
		return (angCalculator.isThetaCalculable() 
				&& angCalculator.isPhiCalculable() 
				&& angCalculator.isGammaCalculable());
	}
	
	public String getColorString(){
		return this.colorString;
	}
}
