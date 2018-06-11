package untref_tfi.domain;

import untref_tfi.controller.MainGraphicInterfaceController;
import untref_tfi.controller.kinect.KinectPointComponentsLengthController;

public class XYZpoint {
	
	private int xValue=0;
	private int yValue=0;
	private double xLength=0.0;
	private double yLength=0.0;
	private double zLength=0.0;
	private double kinectDepth=0.0;
	private AnglesCalculator angCalculator=null;
	private String colorString="";
	private MainGraphicInterfaceController mgic=null;

		public XYZpoint(Integer xPos, Integer yPos, Double kinectDepthMeassure,String color,MainGraphicInterfaceController mgictrl) {
				
		this.mgic=mgictrl;
		this.xValue=xPos;
		this.yValue=yPos;
		this.kinectDepth=kinectDepthMeassure;
		
		KinectPointComponentsLengthController pointLengthController = new KinectPointComponentsLengthController(xPos,yPos,kinectDepthMeassure,mgic);
		this.xLength=pointLengthController.getXlengthInMeters();
		this.yLength=pointLengthController.getYlengthInMeters();
		this.zLength=pointLengthController.getZlengthInMeters();
		this.angCalculator = new AnglesCalculator(this);
		this.colorString=color;
	}

	public int getXvalue() {
		return xValue;
	}

	public int getYvalue() {
		return yValue;
	}
	
	public double getKinectDepth(){
		return this.kinectDepth;
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
