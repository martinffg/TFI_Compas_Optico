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
	private ImageCaptureRefresh imageCaptureRefresh=null;
	private int contadorRotacion=0;
							
	public ImageCaptureController(MainGraphicInterfaceController mainGIController) {
		
		this.compassMGIC=mainGIController;
		this.hwController=mainGIController.getHardwareController();
	}
	
	public void startImageCapture() {
		BufferedImage imagenKinect=null;
		if (hwController.chequearInicializacionKinect() && (!compassMGIC.isTestMode())) {
			data = hwController.getKinectSensorDataCollector();
			if (!compassMGIC.isDepthImageSelected()){
				imagenKinect = data.getImagenColor();
			}else{
				imagenKinect = data.getImagenProfundidad();
			}
			compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null)); 
			imageCaptureRefresh = new ImageCaptureRefresh(this);
			imageCaptureRefresh.run();
		} else {
			compassMGIC.setKinectImage(new Image(getClass().getResource("../../resource/images/errorImageOpeningKinectSensor.jpg").toString()));
		}
	}
	
	public void stopImageCapture() {
		if (imageCaptureRefresh!=null) { 
			imageCaptureRefresh.stop();
		}
	}
	
	public void imageRefresh(){
		BufferedImage imagenKinect=null;
		if (contadorRotacion==360) { 
			contadorRotacion=0; 
		}
		data = hwController.getKinectSensorDataCollector();
		if (!compassMGIC.isDepthImageSelected()){
			imagenKinect = setXYaxesAndVerticalLimitsToBuffImage(data.getImagenColor());
		}else{
			imagenKinect = setXYaxesAndVerticalLimitsToBuffImage(data.getImagenProfundidad());
		}
		
		if (imagenKinect!=null){
			compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null));
			compassMGIC.getKinectImageView().setImage(compassMGIC.getKinectImage());
			compassMGIC.getImageRosaIconView().setRotate(contadorRotacion++);
		}
	}

	private BufferedImage setXYaxesAndVerticalLimitsToBuffImage(BufferedImage imagenKinect) {
		
		Color colorOrange = Color.ORANGE;
		Color colorOOlimits= Color.BLACK;
		
		if (isAutoTrackingEnabled()){
			imagenKinect=procesarImagenContornoActivo(imagenKinect);
		} 
		
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
	
	private BufferedImage procesarImagenContornoActivo(BufferedImage imagenKinect){
		BufferedImage imagenContornoSalida = new BufferedImage(Kinect.screenWidth,Kinect.screenHeight,BufferedImage.TYPE_3BYTE_BGR);
		ActiveContoursControllerAdapter acController = compassMGIC.getActiveContoursController();
		if ((acController!=null)&&acController.areBothPointsSetted()){
			imagenContornoSalida=acController.reproduceImageWithContour(imagenKinect);
		}else{
			imagenContornoSalida=imagenKinect;
		}
		return imagenContornoSalida;
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
		
		return (i==(Kinect.screenWidth/2))||(j==(Kinect.screenHeight/2));
		
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
	
	public boolean isAutoTrackingEnabled(){
		ActiveContoursControllerAdapter acController = compassMGIC.getActiveContoursController();
		
		return compassMGIC.isAutotrackingSelected() && (acController!=null) && acController.areBothPointsSetted();
	}
}
