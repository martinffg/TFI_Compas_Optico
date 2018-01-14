package untref_tfi.controller.kinect;

public class KinectDepthMeassureFixerController {
	
	private double kinectDepthMeassure=0.0;
	
	public KinectDepthMeassureFixerController(double kinectMeassure){
		this.kinectDepthMeassure=kinectMeassure;
	}
	
	public double getUnfixedKinectDepthMeassure(){
		return this.kinectDepthMeassure;
	}
	
	public double getRealKinectDepthMeassure() {
		
		double realMeassure=0.0;
		if (this.kinectDepthMeassure>0){
			realMeassure = getUnfixedKinectDepthMeassure() + getDeltaErrorOnDepthMeassured();
		}
		return realMeassure;
	}
	
	public double getDeltaErrorOnDepthMeassured(){
	// y = 0,1347x + 0,0903   R² = 0,9785  error de la medicion de la depth segun valor de depth
		double deltaError=0.0;
		if (this.kinectDepthMeassure>0){
			deltaError = (this.kinectDepthMeassure * 0.1347) + 0.0903;
		}
		return deltaError;
	}
}
