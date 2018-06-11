package untref_tfi.controller.kinect;

import untref_tfi.controller.MainGraphicInterfaceController;

public class KinectPointComponentsLengthController {
	
	private static final double kinectHalfVerticalViewingAngle=Kinect.kinectFullVerticalViewingAngle/2;
	private static final double kinectHalfHorizontalViewingAngle=Kinect.kinectFullHorizontalViewingAngle/2;
	private static final int halfHorizontalScreenPixelsCount=Kinect.screenWidth/2;
	private static final int halfVerticalScreenPixelsCount=Kinect.screenHeight/2;
	private final double horizontalPixelFactor=Math.tan(Math.toRadians(kinectHalfHorizontalViewingAngle))/halfHorizontalScreenPixelsCount;
	private final double verticalPixelFactor=Math.tan(Math.toRadians(kinectHalfVerticalViewingAngle))/halfVerticalScreenPixelsCount;
	private double distanceZn=0.0;
	private int pixelXCount=0;
	private int pixelYCount=0;
	private MainGraphicInterfaceController mgic=null;

	public KinectPointComponentsLengthController(int pixelsXCounted,int pixelsYCounted,
				double pointDepth,MainGraphicInterfaceController mgictrl) {
			
		this.mgic=mgictrl;
			
		if (isDistanceOnCaptureAllowedRange(pointDepth)) {
			this.distanceZn = calculateOrtogonalDistanceZn(pixelsXCounted,pixelsYCounted,pointDepth);
			this.pixelXCount=pixelsXCounted;
			this.pixelYCount=pixelsYCounted;
		} else {
			mgic.updateSystemMessagesPanel("Punto fuera de rango de captura.");
		}
	}
	
	public double getXlengthInMeters(){
		return this.pixelXCount * this.getXPixelMeassureOnKinect();
	}

	public double getYlengthInMeters(){
		return this.pixelYCount * this.getYPixelMeassureOnKinect();
	}
	
	public double getZlengthInMeters(){
		return this.distanceZn;
	}
	
	private double getXPixelMeassureOnKinect(){			
		return this.distanceZn * horizontalPixelFactor;
	}
	
	private double getYPixelMeassureOnKinect(){
		return this.distanceZn * verticalPixelFactor;
	}
	
	private boolean isDistanceOnCaptureAllowedRange(double distance){
		boolean answer = true;
		
		if (distance < (Kinect.minDistanceMMAllowed/10000)) answer=false;
		
		if (distance > (Kinect.maxDistanceMMAllowed/10000)) answer=false;
		
		return answer;
	}
	
	private double calculateOrtogonalDistanceZn(int pixelsXCount,int pixelsYCount,double pointDepth) {
		double distanceZN=0.0;
		double xComponentPow2=Math.pow((pixelsXCount*horizontalPixelFactor),2);
		double yComponentPow2=Math.pow((pixelsYCount*verticalPixelFactor),2);
		double divisor=Math.sqrt(1+xComponentPow2+yComponentPow2);
		
		distanceZN=pointDepth/divisor;		
				
		return distanceZN;
	}
}
