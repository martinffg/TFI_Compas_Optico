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
	
	public HardwareController (MainGraphicInterfaceController maingic,boolean isTestMode){	
		this.mgic=maingic;
		this.horizontalCtrl = new HorizontalAngleRotationController();
		this.startKinectWork(isTestMode);
	}
	
	public void moveArduinoController(Double angleSelected){
		int stepsSelected = stepsCalculator(angleSelected);
		
		System.out.println("Pasos a mover: " + stepsSelected);
		
		if (stepsSelected>=0){
			horizontalCtrl.movingForwardArduino(stepsSelected);
		} else {
			horizontalCtrl.movingBackwardArduino(Math.abs(stepsSelected));
		}
		
		setRotationAngle(angleSelected);
		
	}
	
	private int stepsCalculator(Double angle){
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
}
