package untref_tfi.controller;

import java.awt.image.BufferedImage;
import java.awt.Color;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.SensorData;
import untref_tfi.controller.kinect.SensorDataProduction;

public class ImageCaptureController {
	
	private SensorData data;
	private Kinect kinect;
	private MainGraphicInterfaceController compassMGIC;
	private int contador=0;
	private boolean isTestMode=false;
			
	public ImageCaptureController(MainGraphicInterfaceController mainGIController,boolean isTest) {
		
		isTestMode=isTest;
		if (!isTestMode) {
			setupKinect();
		} else {
			construirKinect();
		}
		compassMGIC=mainGIController;
		
	}
	
	private void setupKinect() {
		construirKinect();
		startKinect();
		esperarUmbralInicioKinect();
		if (chequearInicializacionKinect()) {
			setearAnguloDeElevacionDefault(); 
		}
		/*else {
			System.out.println("El sensor Kinect no esta inicializado, fallo al capturar imagenes");
		}*/
	}

	private void setearAnguloDeElevacionDefault() {
		kinect.setElevationAngle(0);
	}

	private boolean chequearInicializacionKinect() {
		return kinect.isInitialized();
	}

	private void esperarUmbralInicioKinect() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	private void construirKinect() {
		kinect = new Kinect();
	}

	private void startKinect() {
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
	}
	
	public void startImageCapture() {
		if (chequearInicializacionKinect() && (!isTestMode)) {
			data = new SensorDataProduction(kinect);
			BufferedImage imagenKinect = data.getImagenColor();
			compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null)); 
			ImageCaptureRefresh imageCaptureRefresh = new ImageCaptureRefresh(this);
			imageCaptureRefresh.run();
		} else {
			compassMGIC.setKinectImage(new Image(getClass().getResource("../../resource/images/errorImageOpeningKinectSensor.jpg").toString()));
		}
	}
	
	public void imageRefresh(){
		if (contador==360) { 
			contador=0; 
		}
		data = new SensorDataProduction(kinect);
		BufferedImage imagenKinect = setXYaxesToBuffImage(data.getImagenColor());
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
		Color color = data.getColorEnPixel(x, y);
		cadenaColor="["+color.getRed()+";"+color.getGreen()+";"+color.getBlue()+"]";	
		return cadenaColor;
	}
	
	public double getXYMatrizProfundidad (int x,int y){
		return data.getDistancia(x, y);
	}
}
