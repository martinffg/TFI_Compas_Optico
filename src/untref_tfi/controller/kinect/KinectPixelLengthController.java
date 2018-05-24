package untref_tfi.controller.kinect;

public class KinectPixelLengthController {
	
	private double kinectDepthMeassure=0.0;
	private int pixelXCount=0;
	private int pixelYCount=0;
	
	
	public KinectPixelLengthController(int pixelsXCounted,int pixelsYCounted,double distancePixelToSensor){
		if (isDistanceOnCaptureAllowedRange(distancePixelToSensor)) {
			this.kinectDepthMeassure = distancePixelToSensor;
			this.pixelXCount=pixelsXCounted;
			this.pixelYCount=pixelsYCounted;
		}
	}
	
	public double getXLengthOfPixelsCountedInMetters(){
		return this.pixelXCount * this.getXPixelMeassureOnKinectMeassure();
	}
	
	public double getYLengthOfPixelsCountedInMetters(){
		return this.pixelYCount * this.getYPixelMeassureOnKinectMeassure();
	}
	
	private double getXPixelMeassureOnKinectMeassure(){
	// y = 0,0019x - 0,00002      R² = 0,9993  (longitud horizontal del pixel segun distancia en la kinect corregida)	
		double pixelMeassure= 0.0;
		if (isDistanceOnCaptureAllowedRange(this.kinectDepthMeassure)){		
			pixelMeassure=(this.kinectDepthMeassure * 0.00189) - 0.00002;
		}
		return pixelMeassure;
	}
	
	private double getYPixelMeassureOnKinectMeassure(){
	//  y = 0,0019x + 4E-05   R² = 1  (longitud vertical del pixel segun distancia en la kinect corregida)	
		double pixelMeassure= 0.0;
		if (isDistanceOnCaptureAllowedRange(this.kinectDepthMeassure)){		
			pixelMeassure=(this.kinectDepthMeassure * 0.00185) + 0.00004;
		}
		return pixelMeassure;
	}
	
	private boolean isDistanceOnCaptureAllowedRange(double distance){
		boolean answer = true;
		
		if (distance < (KinectSensorDataCollector.minDistanceMMAllowed/10000)) answer=false;
		
		if (distance > (KinectSensorDataCollector.maxDistanceMMAllowed/10000)) answer=false;
		
		return answer;
	}
}
