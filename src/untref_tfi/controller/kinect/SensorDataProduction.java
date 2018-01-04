package untref_tfi.controller.kinect;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SensorDataProduction implements SensorData {

	private byte[] colorFrame;
	private short[] depth;
	private Color[][] matrizColor;
	private Color colorOutOfRange=Color.gray;
	private double[][] matrizProfundidad;
	private int imageWidth;
	private int imageHeight;
	private BufferedImage imagenColor;
	private BufferedImage imagenColorBackup;
	private BufferedImage imagenProfundidad;
		
	public SensorDataProduction(Kinect kinect,Color colorOOR) {
		
		this.colorOutOfRange=colorOOR;
		if (!kinect.isInitialized()) {
			kinect = new Kinect();
			kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			kinect.setElevationAngle(0);
		}
				
		this.colorFrame = kinect.getColorFrame();
		this.depth = kinect.getDepthFrame();		
		this.imageWidth = kinect.getColorWidth();
		this.imageHeight = kinect.getColorHeight();

		this.construirMatrizColor();
		this.construirMatrizProfundidad();

	}

	@Override
	public Color getColorEnPixel(int x, int y) {
		return matrizColor[x][y];
	}
	
	@Override
	public void setColorEnPixel(int x, int y, Color color) {
		matrizColor[x][y]=color;
	}

	@Override
	public double getDistancia(int x, int y) {
		return matrizProfundidad[x][y];
	}

	@Override
	public BufferedImage getImagenColor() {
		return imagenColor;
	}
	
	@Override
	public BufferedImage getImagenColorBackup() {
		return imagenColorBackup;
	}

	@Override
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
		float max = 30000;//3 metros
		float min = 7000;//70 cm
		int k=0;
		int height=0;
		Color color=null;
		
		for (int j = 0; j < this.getHeight(); j++) {
			for (int i = 0; i < this.getWidth(); i++) {
				height = this.getWidth() * j;
				k = i + height;
				iEspejado=this.getWidth()-1-i; // como la captura es espejada, se requiere espejar en eje y
				color = getColorPorProfundidad(iEspejado,j,k, max, min);
				this.matrizProfundidad[iEspejado][j] = depth[k];
				this.imagenProfundidad.setRGB(iEspejado, j, color.getRGB());
			}
		}
	}

	private Color getColorPorProfundidad(int i,int j,int z, float max, float min) {
		Color color;
		if ((depth[z]>=min)&&(depth[z]<=max)) {
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