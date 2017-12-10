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
			System.out.println("Falla al inicializar la kinect.");
			System.exit(2);
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
	public double getDistancia(int x, int y) {
		return matrizProfundidad[x][y];
	}

	@Override
	public BufferedImage getImagenColor() {
		return imagenColor;
	}
	
	public BufferedImage getImagenColorBackup() {
		return imagenColorBackup;
	}

	@Override
	public BufferedImage getImagenProfundidad() {
		return imagenProfundidad;
	}
		
	@Override
	public void setPixelColorPorProfundidad(float dist, int cantPixeles, Color colorContorno) {
		
		short deltaContorno = 200;
		
		for (int i = 0; i < this.getWidth(); i+=cantPixeles) {
			for (int j = 0; j < this.getHeight() ; j+= cantPixeles) {								
				evaluarSetPixelColorPorProfundidad(dist, cantPixeles, colorContorno, deltaContorno, i, j);
			}
		}
	}

	private void evaluarSetPixelColorPorProfundidad(float dist, int cantPixeles, Color colorContorno,
			short deltaContorno, int i, int j) {
		if(this.getDistancia(i,j) < dist - deltaContorno ){	
			
			this.pintarContorno(i, j, cantPixeles, Color.BLACK, this.getImagenProfundidad());		
		
		} else 
			if ( this.getDistancia(i, j) >= dist - deltaContorno && this.getDistancia(i, j) <= dist + deltaContorno) {
			
				this.pintarContorno(i, j, cantPixeles,colorContorno, this.getImagenProfundidad());
			}
	}	
	
	@Override
	public void pintarCurvaNivel(int distEntreCurvas) {
		
		int deltaContorno = 50;
		int max = 30000;//3 metros
		int min = 7000;//70 cm
		int dist = max;
				
		int anchoMax= getWidth() -1;
		int altoMax= getHeight() -1;
		if (dist >= 2) {
			deltaContorno = 100;
		}
		
		while(dist >= min){
		
			recorrerImagenCurvaNivel(dist, anchoMax, altoMax, deltaContorno);
			dist -= distEntreCurvas;
		
		}
	}

	private void recorrerImagenCurvaNivel(int dist, int anchoMax, int altoMax, int deltaContorno) {
		
		for (int i = anchoMax; i > 0; i -= 3) {
			for (int j = altoMax; j > 0; j -= 3) {
												
				evaluarPintarPuntoCurvaNivel(dist, deltaContorno, i, j);
			}
		}
	}

	private void evaluarPintarPuntoCurvaNivel(int dist, int deltaContorno, int i, int j) {
		Color color;
		float hue;
		if (this.getDistancia(i, j) >= dist - deltaContorno
				&& this.getDistancia(i, j) <= dist + deltaContorno) {
			
			hue = (float)(this.getDistancia(i, j)/100000);
			color = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
			
			this.pintarContorno(i, j, 3, color, this.getImagenColor());
		}
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
		
		for (int i = 0; i < 480; i++) {
			for (int j = 0; j < 640; j++) {
				
				int height = 640 * i;
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
			color = Color.WHITE;
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
	
	private void pintarContorno(int fila, int columna, int cantidadDePixeles, Color color,BufferedImage img){
		
		int pixeles = cantidadDePixeles/2 + cantidadDePixeles % 2;
		
		int pixelInicioDeFila = fila - pixeles;
		int pixelFinalDeFila = fila + pixeles;
		int pixelInicioDeColumna = columna - pixeles;
		int pixelFinalDeColumna = columna + pixeles;
		
		for (int i = pixelInicioDeFila; i < pixelFinalDeFila ; i++){
			
			for (int j = pixelInicioDeColumna; j < pixelFinalDeColumna ; j++){
				
				if (esPosicionValida(i,j)){
					img.setRGB(i,j, color.getRGB());
				}
			}
		}
	}

	private boolean esPosicionValida(int fila, int columna) {
		
		boolean filaValida = fila >=0 && fila < this.getWidth();
		boolean columnaValida =  columna >=0 && columna < this.getHeight();
		
		return filaValida && columnaValida;
	}
		
}