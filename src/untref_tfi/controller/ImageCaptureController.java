package untref_tfi.controller;

import java.awt.image.BufferedImage;
import java.awt.Color;
import javafx.embed.swing.SwingFXUtils;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.SensorData;
import untref_tfi.controller.kinect.SensorDataProduction;

public class ImageCaptureController {
	
	private SensorData data;
	private Kinect kinect;
	private MainGraphicInterfaceController compassMGIC;
	private int contador=0;
	
	public ImageCaptureController(MainGraphicInterfaceController mainGIController) {
		setupKinect();
		compassMGIC=mainGIController;
	}
	
	private void setupKinect() {
		construirKinect();
		startKinect();
		esperarUmbralInicioKinect();
		chequearInicializacionKinect();
		setearAnguloDeElevacionDefault();
	}

	private void setearAnguloDeElevacionDefault() {
		kinect.setElevationAngle(0);
	}

	private void chequearInicializacionKinect() {
		if (!kinect.isInitialized()) {
			System.out.println("Falla al inicializar la kinect.");
			System.exit(1);
		}
	}

	private void esperarUmbralInicioKinect() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void construirKinect() {
		kinect = new Kinect();
	}

	private void startKinect() {
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
	}
	
	public void startImageCapture() {
		data = new SensorDataProduction(kinect);
		BufferedImage imagenKinect = data.getImagenColor();
		compassMGIC.setKinectImage(SwingFXUtils.toFXImage(imagenKinect, null));

		ImageCaptureRefresh imageCaptureRefresh = new ImageCaptureRefresh(this);
		imageCaptureRefresh.run();
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
				// pinta de color los ejes centrales de la imagen (ancho 2 pixeles)
				if ((i==319)||(i==320)||(j==239)||(j==240)){
					imagenKinect.setRGB(i, j,color.getRGB());
				} 
				/*
					else if (Math.sqrt(Math.pow(i-319,2)+Math.pow(j-239,2))==10){
						imagenKinect.setRGB(i, j,color.getRGB());
					}
				*/
			}
		}
		
		return imagenKinect;
	}

}
