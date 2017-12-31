package untref_tfi.controller.kinect;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SensorDataProduction implements SensorData {

	private byte[] colorFrame;
	private short[] depth;
	private Color[][] matrizColor;
	private double[][] matrizProfundidad;
	private int width;
	private int height;
	private BufferedImage imagenColor;
	private BufferedImage imagenColorBackup;
	private BufferedImage imagenProfundidad;
		
	public SensorDataProduction(Kinect kinect) {
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
		this.width = kinect.getColorWidth();
		this.height = kinect.getColorHeight();

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
		
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {

				int posicionInicial = j * 4;
				
				int height = this.getWidth() * 4 * i;
				int blue = posicionInicial + height;
				int green = posicionInicial + 1 + height;
				int red = posicionInicial + 2 + height;
				int alpha = posicionInicial + 3 + height;

				Color color = construirColor(blue, green, red, alpha);
				this.matrizColor[j][i] = color;
				imagenColor.setRGB(j, i, color.getRGB());
				imagenColorBackup.setRGB(j, i, color.getRGB());
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
		
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				
				int height = this.getWidth() * i;
				int z = j + height;

				float max = 30000;//3 metros
				float min = 7000;//70 cm

				Color color = getColorPorProfundidad(z, max, min);
				this.matrizProfundidad[j][i] = depth[z];
				imagenProfundidad.setRGB(j, i, color.getRGB());
			}
		}
	}

	private Color getColorPorProfundidad(int z, float max, float min) {
		Color color;
		if (depth[z] == 0) {
			color = Color.gray;
		} else if (depth[z] > max) {
			color = Color.white;
		} else if (depth[z] < min) {
			color = Color.white;
		} else {
			float hue = (1 / (max - min)) * (depth[z] - min);
			color = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
		}
		return color;
	}

	private int getWidth() {
		return this.width;
	}

	private int getHeight() {
		return this.height;
	}		
}