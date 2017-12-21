package untref_tfi.controller.kinect;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface SensorData {

	BufferedImage getImagenColor();
	
	BufferedImage getImagenColorBackup();
	
	Color getColorEnPixel(int x, int y);
	
	void setColorEnPixel(int x, int y, Color color);
	
	double getDistancia(int x, int y);
	
	BufferedImage getImagenProfundidad();
	
	void setPixelColorPorProfundidad(float dist, int cantPixeles, Color colorContorno);
	
	void pintarCurvaNivel(int distEntreCurvas);
	
}
