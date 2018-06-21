package untref_tfi.controller;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.Color;
import untref_tfi.controller.hardware.HardwareController;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.KinectSensorDataCollector;
import untref_tfi.domain.XYZpoint;

public class ImageCaptureController {
	
	private MainGraphicInterfaceController compassMGIC=null;
	private HardwareController hwController=null;
	private KinectSensorDataCollector data=null;
	private int contador=0;
	private boolean isTestMode=false;
					
	public ImageCaptureController(MainGraphicInterfaceController mainGIController,boolean isTest) {
		
		this.compassMGIC=mainGIController;
		this.hwController=mainGIController.getHardwareController();
		isTestMode=isTest;	
	}
	
	public void startImageCapture() {
		BufferedImage imagenKinect=null;
		if (hwController.chequearInicializacionKinect() && (!isTestMode)) {
			data = hwController.getKinectSensorDataCollector();
			if (!compassMGIC.isDepthImageSelected()){
				imagenKinect = data.getImagenColor();
			}else{
				imagenKinect = data.getImagenProfundidad();
			}
			compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null)); 
			ImageCaptureRefresh imageCaptureRefresh = new ImageCaptureRefresh(this);
			imageCaptureRefresh.run();
		} else {
			compassMGIC.setKinectImage(new Image(getClass().getResource("../../resource/images/errorImageOpeningKinectSensor.jpg").toString()));
		}
	}
	
	public void imageRefresh(){
		BufferedImage imagenKinect=null;
		if (contador==360) { 
			contador=0; 
		}
		data = hwController.getKinectSensorDataCollector();
		if (!compassMGIC.isDepthImageSelected()){
			imagenKinect = setXYaxesAndVerticalLimitsToBuffImage(data.getImagenColor());
		}else{
			imagenKinect = setXYaxesAndVerticalLimitsToBuffImage(data.getImagenProfundidad());
		}
		compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null));
		compassMGIC.getKinectImageView().setImage(compassMGIC.getKinectImage());
		compassMGIC.getImageRosaIconView().setRotate(contador++);
	}

	private BufferedImage setXYaxesAndVerticalLimitsToBuffImage(BufferedImage imagenKinect) {
		
		Color colorOrange = Color.ORANGE;
		Color colorOOlimits= Color.BLACK;
		
		for (int i = 0; i < imagenKinect.getWidth(); i++) {
			for (int j = 0; j < imagenKinect.getHeight() ; j++) {			
				if ((compassMGIC.isOutOfFocusPointsSelected())&&(isOutOfVerticalBoundsPoint(i,j))){
					imagenKinect.setRGB(i, j,colorOOlimits.getRGB());
				}
				if (evaluateIfIsAxePoint(i, j)) {
					imagenKinect.setRGB(i, j,colorOrange.getRGB());
				}
			}
		}
		
		return imagenKinect;
	}
	
	public boolean isOutOfVerticalBoundsPoint(int xValue,int yValue) {
		
		boolean result=false;
		int elevationFocus=this.hwController.getElevationAngle();
		double elevationGamma=0.0;
		double absoluteVerticalValue=0.0;
		double halfVerticalRange=Kinect.kinectVerticalTiltAbsValue+(double)Kinect.kinectFullVerticalViewingAngle/2;
		
		if (Math.abs(elevationFocus)>5){
			int selectedXpoint= xValue - MainGraphicInterfaceController.zeroXref;		// Convert X to cartesian axe
	    	int selectedYpoint=(yValue * -1) + MainGraphicInterfaceController.zeroYref;	// Convert Y to cartesian axe
	    	double selectedDepthPoint = this.getXYMatrizProfundidad(xValue,yValue);
	     	XYZpoint selectedPixel=new XYZpoint(selectedXpoint,selectedYpoint,selectedDepthPoint,"",null);
	     	if (selectedPixel.getAnglesCalculator().isGammaCalculable()) {
	     		elevationGamma=selectedPixel.getAnglesCalculator().getGamma();
	     	}
	     	absoluteVerticalValue= Math.abs(elevationFocus + elevationGamma);
	     	if ((absoluteVerticalValue>Kinect.kinectVerticalTiltAbsValue)&&(absoluteVerticalValue<=halfVerticalRange)){  // asi contemplo tambien los OOR fuera del ángulo de captura.
	 			result=true;
	 		}
 		}
 	
     	return result;
	}
	

	private Boolean evaluateIfIsAxePoint(int i, int j) {
		
		return (i==320)||(j==240);
		
	}
	
	public String getXYMatrizRGBColorCadena (int x,int y){
		String cadenaColor="";
		if (validateXYinserted(x, y)) {
			Color color = data.getColorEnPixel(x, y);
			cadenaColor="["+color.getRed()+";"+color.getGreen()+";"+color.getBlue()+"]";	
		}
		return cadenaColor;
	}
	
	public double getXYMatrizProfundidad (int x,int y){
		double resultado=0.0;
		if (validateXYinserted(x, y)) {
			resultado = data.getDistancia(x, y);
		}
		return resultado;
	}

	private boolean validateXYinserted(int x, int y) {
		return (x>=0)&&(y>=0)
				&&(x<Kinect.screenWidth)
				&&(y<Kinect.screenHeight);
	}	
}
