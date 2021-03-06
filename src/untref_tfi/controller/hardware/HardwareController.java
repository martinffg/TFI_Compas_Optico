package untref_tfi.controller.hardware;

import java.awt.Color;

import untref_tfi.controller.MainGraphicInterfaceController;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.KinectSensorDataCollector;

public class HardwareController {
	
	private HorizontalAngleRotationController horizontalCtrl = null;
	private int elevationAngle=0;
	private double rotationAngle=0.0;
	private MainGraphicInterfaceController mgic=null;
	private Kinect kinect=null;
	private KinectSensorDataCollector data=null;
				
	public HardwareController (MainGraphicInterfaceController mgictrl){	
		this.mgic=mgictrl;
		this.horizontalCtrl = new HorizontalAngleRotationController();
		this.startKinectWork(mgic.isTestMode());
	}
	
	public void moveArduinoController(double angleSelected){
		double horizontalFixedAngleSelected=fixAngleSelectedHorizontalDesviation(angleSelected);
		
		double targetAngleSelected=angleSelected+this.getRotationAngle();

		int stepsSelected = stepsCalculator(horizontalFixedAngleSelected);
		
		if (Math.abs(targetAngleSelected)<=180){
			
			if (stepsSelected>=0){
				horizontalCtrl.movingForwardArduino(stepsSelected);
			} else {
				horizontalCtrl.movingBackwardArduino(Math.abs(stepsSelected));
			}
			
			setRotationAngle(angleSelected);
		
		}else {
			mgic.updateSystemMessagesPanel("El ángulo acumulado es > +/-180 grados.");
		}
	}
	
	private int stepsCalculator(double angle){
		return (int)Math.round(angle / horizontalCtrl.getOneStepDegree());
	}
	
	public int getElevationAngle(){
		return this.elevationAngle;
	}
	
	public void setElevationAngle(int elevation){

		this.elevationAngle=elevation;
		mgic.updateSensorPositionPanel();
	
	}
	
	public double getRotationAngle(){
		return this.rotationAngle;
	}
	
	public void setRotationAngle(double rotation){
		this.rotationAngle+=rotation;
		mgic.updateSensorPositionPanel();
	}
	
	public HorizontalAngleRotationController getHorizontalCtrl() {
		return horizontalCtrl;
	}
	
	public Kinect getKinect(){
		return this.kinect;
	}
	
	private void setupKinect() {
		construirKinect();
		startKinect();
		esperarUmbralInicioKinect();
		if (chequearInicializacionKinect()) {
			setearAnguloDeElevacionDefault(); 
		}
	}

	private void setearAnguloDeElevacionDefault() {
		kinect.setElevationAngle(0);
	}

	public boolean chequearInicializacionKinect() {
		return kinect.isInitialized();
	}

	private void esperarUmbralInicioKinect() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	public void startKinectWork(boolean isTestMode) {
		if (!isTestMode) {
			setupKinect();
		} else {
			construirKinect();
		}
	}

	private void construirKinect() {
		kinect = new Kinect();
	}

	private void startKinect() {
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
	}
	
	public KinectSensorDataCollector getKinectSensorDataCollector(){
		data = new KinectSensorDataCollector(getKinect(),getAwtColor(mgic.getColorOOR()),getElevationAngle());
		return data;
	}
	
	private Color getAwtColor(javafx.scene.paint.Color colorJFX){
		
		Color colorAwt = new Color((float)colorJFX.getRed(),(float)colorJFX.getGreen(),(float)colorJFX.getBlue(),(float)colorJFX.getOpacity());
		
		return colorAwt;
	}
	
	private double fixAngleSelectedHorizontalDesviation(double angleSelected){
		
		double fixedAngle=0.0;
		// formula inicial
		
		if (Math.abs(angleSelected)<=18.0) {
			// y = 0,0855x + 0,2162		R² = 0,9957		Correccion Angular horizontal de desvio en cono de 28,5°
			fixedAngle=Math.abs(angleSelected)+(0.0855*Math.abs(angleSelected)+0.2162);
		}else{
			fixedAngle=Math.abs(angleSelected)+1.8;
		}
		
		if (Math.abs(angleSelected)>22.0) {
			fixedAngle=Math.abs(angleSelected)+2.5;
		}
		if (Math.abs(angleSelected)>=28.5) {
			fixedAngle=Math.abs(angleSelected)+0.9;
		}
		if (Math.abs(angleSelected)>=179.1) {
			fixedAngle=Math.abs(angleSelected);  // Cerca de 180° el error asciende a +/-1.8 grados
		}
		if (angleSelected<0.0) {
			fixedAngle=(-1)*fixedAngle;
		}
		return fixedAngle;
	}
	
	public void cleanCounters(){
		this.elevationAngle=0;
		this.rotationAngle=0.0;
	}
	
}
