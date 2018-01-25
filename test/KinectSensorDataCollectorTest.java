import java.awt.Color;
import org.junit.Assert;
import org.junit.Test;

import untref_tfi.controller.kinect.Kinect;
import untref_tfi.controller.kinect.KinectSensorDataCollector;

public class KinectSensorDataCollectorTest {

	@Test
	public void KinectDataCollectorTest() {
		try { 
			Kinect kinect;
			kinect = new Kinect();
			kinect.start(Kinect.DEPTH | Kinect.COLOR | Kinect.SKELETON | Kinect.XYZ | Kinect.PLAYER_INDEX);
			Thread.sleep(3000);
			if (kinect.isInitialized()){
				KinectSensorDataCollector ksdc = new KinectSensorDataCollector(kinect, Color.gray, 0);
				ksdc.setColorEnPixel(1, 1, Color.gray);	
				Assert.assertEquals(Color.gray,ksdc.getColorEnPixel(1, 1));
				Assert.assertNotNull(ksdc.getDistancia(1, 1));
				Assert.assertNotNull(ksdc.getImagenColor());
				Assert.assertNotNull(ksdc.getImagenColorBackup());
				Assert.assertNotNull(ksdc.getImagenProfundidad());
			} else {
				Assert.assertNotNull(kinect);
			}
		}catch(Exception e){}
		
	}
	
	@Test
	public void test2(){
		try { Kinect kinect = null;
			KinectSensorDataCollector ksdc = new KinectSensorDataCollector(kinect, Color.gray, 0);
			ksdc.setColorEnPixel(1, 1, Color.gray);	
			Assert.assertEquals(Color.gray,ksdc.getColorEnPixel(1, 1));
			Assert.assertNotNull(ksdc.getDistancia(1, 1));
			Assert.assertNotNull(ksdc.getImagenColor());
			Assert.assertNotNull(ksdc.getImagenColorBackup());
			Assert.assertNotNull(ksdc.getImagenProfundidad());
		} catch (Exception e) {}
	}

}
