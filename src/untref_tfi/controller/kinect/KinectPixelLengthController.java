package untref_tfi.controller.kinect;

public class KinectPixelLengthController {
	
	private double kinectDepthMeassure=0.0;
	private int pixelCount=0;
	
	public KinectPixelLengthController(double distancePixelToSensor,int pixelsCounted){
		if (isDistanceOnCaptureAllowedRange(distancePixelToSensor)) {
			this.kinectDepthMeassure = distancePixelToSensor;
			this.pixelCount=pixelsCounted;
		}
	}
	
	public double getLengthOfPixelsCountedInMetters(){
		return this.pixelCount * this.getPixelMeassureOnKinectMeassure();
	}
	
	private double getPixelMeassureOnKinectMeassure(){
	// y = 0,00193x - 0,00002      R² = 0,9998  (longitud del pixel segun distancia en la kinect corregida)	
		double pixelMeassure= 0.0;
		if (isDistanceOnCaptureAllowedRange(this.kinectDepthMeassure)){		
			pixelMeassure=(this.kinectDepthMeassure * 0.00193) - 0.00002;
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
