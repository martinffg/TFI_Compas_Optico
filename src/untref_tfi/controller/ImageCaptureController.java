package untref_tfi.controller;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.Color;
import untref_tfi.controller.hardware.HardwareController;
import untref_tfi.controller.kinect.KinectSensorDataCollector;

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
			imagenKinect = setXYaxesToBuffImage(data.getImagenColor());
		}else{
			imagenKinect = setXYaxesToBuffImage(data.getImagenProfundidad());
		}
		compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null));
		compassMGIC.getKinectImageView().setImage(compassMGIC.getKinectImage());
		compassMGIC.getImageRosaIconView().setRotate(contador++);
	}

	private BufferedImage setXYaxesToBuffImage(BufferedImage imagenKinect) {

		Color color = Color.ORANGE;
		for (int i = 0; i < imagenKinect.getWidth(); i++) {
			for (int j = 0; j < imagenKinect.getHeight() ; j++) {			
				if (evaluatePintablePoint(i, j)) 
					imagenKinect.setRGB(i, j,color.getRGB());
			}
		}
		
		return imagenKinect;
	}

	private Boolean evaluatePintablePoint(int i, int j) {
		
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
				&&(x<MainGraphicInterfaceController.maxWidth)
				&&(y<MainGraphicInterfaceController.maxLength);
	}	
}
