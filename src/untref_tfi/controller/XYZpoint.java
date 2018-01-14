package untref_tfi.controller;

import untref_tfi.controller.kinect.KinectPixelLengthController;

public class XYZpoint {
	
	private int xValue=0;
	private double xLength=0.0;
	private int yValue=0;
	private double yLength=0.0;
	private double zLength=0.0;
	
	public XYZpoint(Integer xPos, Integer yPos, Double zPos){
		this.xValue=xPos;
		this.yValue=yPos;
		this.zLength=zPos;
		KinectPixelLengthController xLengthController = new KinectPixelLengthController(zPos,xPos);
		this.xLength=xLengthController.getLengthOfPixelsCountedInMetters();
		KinectPixelLengthController yLengthController = new KinectPixelLengthController(zPos,yPos);
		this.yLength=yLengthController.getLengthOfPixelsCountedInMetters();	
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
}
