package untref_tfi.controller.kinect;

public class KinectDepthMeassureFixerController {
	
	private static final int zeroXref=Kinect.screenWidth/2;  // 0Xref: 320
	private static final int zeroYref=Kinect.screenHeight/2; // 0Yref: 240
	private double kinectDepthMeassure=0.0;
	private int xValue=0;
	private int yValue=0;
	
	
	public KinectDepthMeassureFixerController(double kinectMeassure,int i,int j){
		kinectDepthMeassure=kinectMeassure;
		xValue=i-zeroXref;			// Convert X to cartesian axe Value
		yValue=(j * -1) + zeroYref;  // Convert Y to cartesian axe Value
	}
	
	public double getUnfixedKinectDepthMeassure(){
		return this.kinectDepthMeassure;
	}
	
	public double getRealKinectDepthMeassure() {
		
		double realMeassure=0.0;
		if (this.kinectDepthMeassure>0){
			realMeassure = getUnfixedKinectDepthMeassure() 
					+ getDeltaErrorOnDepthMeassuredRespectOrigin()
					+ getHorizontalErrorOnDepthMeassured()
					+ getVerticalErrorOnDepthMeassured();
		}
		return realMeassure;
	}
	
	private double getDeltaErrorOnDepthMeassuredRespectOrigin(){
	// y = 0,1795x + 0,0475   R² = 0,9943     error de la medicion de la depth respecto al origen
		double deltaError=0.0;
		if (this.kinectDepthMeassure>0){
			deltaError = (this.kinectDepthMeassure * 0.1795) + 0.0475;
		}
		return deltaError;
	}
	
	public double getHorizontalErrorOnDepthMeassured(){
	// y = 0,001x - 0,0157    R² = 0,9771		error de la medicion de la depth respecto al eje X
		double deltaError=0.0;
		if ((this.kinectDepthMeassure>0)&&(Math.abs(xValue)>0)){
			deltaError = (Math.abs(xValue) * 0.001) - 0.0157;
		}
		return deltaError;
	}
	
	public double getVerticalErrorOnDepthMeassured(){
	// y = 0,0007x - 0,0225		R² = 0,9507		error de la medicion de la depth respecto al eje Y
		double deltaError=0.0;
		if ((this.kinectDepthMeassure>0)&&(Math.abs(yValue)>0)){
			deltaError = (Math.abs(yValue) * 0.0007) - 0.0225;
		}
		return deltaError;
	}
	
}
