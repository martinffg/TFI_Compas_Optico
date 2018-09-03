import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.Assert;
import org.junit.Test;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.KinectSensorDataCollector;


public class SensorDataProductionTest {
	
	@Test
	public void pruebaSensorKinectTest() {			
		try {
			Kinect kinect = setupKinect();
			KinectSensorDataCollector sensor = new KinectSensorDataCollector(kinect,Color.gray,0);
			BufferedImage img1 = sensor.getImagenColor();
			
			sensor.setColorEnPixel(0,0,Color.WHITE);
			Color color = sensor.getColorEnPixel(0,0);
			color.brighter();
			double dist = sensor.getDistancia(0, 0);
			Assert.assertEquals(Color.WHITE,sensor.getColorEnPixel(0,0));
			Assert.assertEquals(dist,sensor.getDistancia(0, 0),0.1);
			Assert.assertNotNull(sensor.getImagenProfundidad());
			Assert.assertNotNull(img1);
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
	
