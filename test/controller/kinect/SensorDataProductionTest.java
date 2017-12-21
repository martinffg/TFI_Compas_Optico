package controller.kinect;

import java.awt.Color;
import org.junit.Assert;
import org.junit.Test;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.SensorDataProduction;


public class SensorDataProductionTest {
	
	@Test
	public void pruebaSensorKinectTest() {
		try {
						
			Kinect kinect = setupKinect();
			SensorDataProduction sensor = new SensorDataProduction(kinect);
			sensor.setPixelColorPorProfundidad(0.5f, 3, Color.BLACK);
			sensor.pintarCurvaNivel(4);
			sensor.setColorEnPixel(0,0,Color.WHITE);
			Assert.assertEquals(sensor.getColorEnPixel(0,0),Color.WHITE);
			Assert.assertNotNull(sensor.getDistancia(0, 0));
			Assert.assertEquals(sensor.getImagenColor().getRGB(40,40), sensor.getImagenColorBackup().getRGB(40,40));
			Assert.assertEquals(sensor.getImagenColor().getRGB(340,280), sensor.getImagenColorBackup().getRGB(340,280));
			Assert.assertNotNull(sensor.getImagenProfundidad());

		}catch(Exception e){
			//System.out.println("Exception in SensorDataProductionTest -> catched.");
		}
	}

	private Kinect setupKinect() {
		Kinect kinect = new Kinect();
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		kinect.setElevationAngle(0);
		return kinect;
	}		
}
	
