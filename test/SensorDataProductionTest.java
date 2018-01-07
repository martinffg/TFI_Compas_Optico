import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.Assert;
import org.junit.Test;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.SensorDataProduction;


public class SensorDataProductionTest {
	
	@Test
	public void pruebaSensorKinectTest() {			
		try {
			Kinect kinect = setupKinect();
			SensorDataProduction sensor = new SensorDataProduction(kinect,Color.gray,0);
			BufferedImage img1 = sensor.getImagenColor();
			BufferedImage img2 = sensor.getImagenColorBackup();
			Assert.assertEquals(img1.getRGB(40,40), img2.getRGB(40,40));
			Assert.assertEquals(img1.getRGB(340,280), img2.getRGB(340,280));
			sensor.setColorEnPixel(0,0,Color.WHITE);
			sensor.getColorEnPixel(0,0);
			sensor.getDistancia(0, 0);
			Assert.assertEquals(sensor.getColorEnPixel(0,0),Color.WHITE);
			Assert.assertNotNull(sensor.getDistancia(0, 0));
			Assert.assertNotNull(sensor.getImagenProfundidad());
		}catch(Exception e){}
	}

	private Kinect setupKinect() throws InterruptedException {
		Kinect kinect = new Kinect();
		kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
		//Thread.sleep(2000);
		kinect.setElevationAngle(0);
		
		return kinect;
	}
}
	
