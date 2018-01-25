package untref_tfi.controller.kinect;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class KinectSensorDataCollector {

	private byte[] colorFrame;
	private short[] depth;
	private Color[][] matrizColor;
	private Color colorOutOfRange=Color.gray;
	private double[][] matrizProfundidad;
	private int imageWidth;
	private int imageHeight;
	private int elevationAngle=0;  // angle in degrees between -27 to 27
	private BufferedImage imagenColor;
	private BufferedImage imagenColorBackup;
	private BufferedImage imagenProfundidad;
	public static final float maxDistanceMMAllowed = 36000;//3,60 m
	public static final float minDistanceMMAllowed = 8000;// 0,80 m
		
	public KinectSensorDataCollector(Kinect kinect,Color colorOOR,int elevation) {
		this.elevationAngle=elevation;
		if (kinect==null) {
			kinect = startKinectSensor();
		}	
		kinect.setElevationAngle(elevationAngle);
		this.colorOutOfRange=colorOOR;
		this.colorFrame = kinect.getColorFrame();
		this.depth = kinect.getDepthFrame();		
		this.imageWidth = kinect.getColorWidth();
		this.imageHeight = kinect.getColorHeight();

		this.construirMatrizColor();
		this.construirMatrizProfundidad();

	}

	private Kinect startKinectSensor() {
		Kinect kinect;
		kinect = new Kinect();
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		return kinect;
	}

	public Color getColorEnPixel(int x, int y) {
		return matrizColor[x][y];
	}
	
	public void setColorEnPixel(int x, int y, Color color) {
		matrizColor[x][y]=color;
	}

	public double getDistancia(int x, int y) {
		return matrizProfundidad[x][y];
	}

	public BufferedImage getImagenColor() {
		return imagenColor;
	}
	
	public BufferedImage getImagenColorBackup() {
		return imagenColorBackup;
	}

	public BufferedImage getImagenProfundidad() {
		return imagenProfundidad;
	}
	
	private void construirMatrizColor() {
		matrizColor = new Color[this.getWidth()][this.getHeight()];
		imagenColor = new BufferedImage(this.getWidth(), this.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		imagenColorBackup = new BufferedImage(this.getWidth(), this.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		int iEspejado=0,posicionInicial=0,height=0,red=0,green=0,blue=0,alpha=0;
		Color color=null;
		
		for (int j = 0; j < this.getHeight(); j++) {
			for (int i = 0; i < this.getWidth(); i++) {
				posicionInicial = i * 4;
				height = this.getWidth() * 4 * j;
				blue = posicionInicial + height;
				green = posicionInicial + 1 + height;
				red = posicionInicial + 2 + height;
				alpha = posicionInicial + 3 + height;
				color = construirColor(blue, green, red, alpha);
				iEspejado=this.getWidth()-1-i; // como la captura es espejada, se requiere espejar en eje y
				this.matrizColor[iEspejado][j] = color;
				this.imagenColor.setRGB(iEspejado, j, color.getRGB());
				this.imagenColorBackup.setRGB(iEspejado, j, color.getRGB());
			}
		}
	}

	private Color construirColor(int blue, int green, int red, int alpha) {
		return new Color(this.colorFrame[red] & 0xFF,
				this.colorFrame[green] & 0xFF,
				this.colorFrame[blue] & 0xFF,
				this.colorFrame[alpha] & 0xFF);
	}

	private void construirMatrizProfundidad() {
		matrizProfundidad = new double[this.getWidth()][this.getHeight()];		
		imagenProfundidad = new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		int iEspejado=0;
		float fixedMax=maxDistanceMMAllowed/10000;  // paso a metros la medida
		float fixedMin=minDistanceMMAllowed/10000;  // paso a metros la medida
		int k=0;
		int height=0;
		Color color=null;
		double unfixedDepth=0.0;
		double fixedDepth=0.0;
		
		for (int j = 0; j < this.getHeight(); j++) {
			for (int i = 0; i < this.getWidth(); i++) {
				height = this.getWidth() * j;
				k = i + height;
				iEspejado=this.getWidth()-1-i; // como la captura es espejada, se requiere espejar en eje y
				// Aqui trato el problema del error de lectura de profundidad con certeza del 0,9785
				unfixedDepth=(double)depth[k]/10000;  // guardo en metros la profundidad sensada y sin corregir
				KinectDepthMeassureFixerController meassureFixer = new KinectDepthMeassureFixerController(unfixedDepth);
				fixedDepth=meassureFixer.getRealKinectDepthMeassure(); 
				color = getColorPorProfundidad(iEspejado,j,fixedDepth, fixedMax, fixedMin);
				this.matrizProfundidad[iEspejado][j] = fixedDepth;
				this.imagenProfundidad.setRGB(iEspejado, j, color.getRGB());
			}
		}
	}
	
	private Color getColorPorProfundidad(int i,int j,double fixedDepth, float max, float min) {
		Color color;
		if ((fixedDepth>=min)&&(fixedDepth<=max)) {
			color = this.getColorEnPixel(i, j);
		} else {
			color = this.colorOutOfRange;   // todo lo que esta fuera del rango de captura lo pinta de un color definido
		}
		return color;
	}

	private int getWidth() {
		return this.imageWidth;
	}

	private int getHeight() {
		return this.imageHeight;
	}		
}