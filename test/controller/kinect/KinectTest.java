package controller.kinect;

import org.junit.Assert;
import org.junit.Test;

import untref_tfi.controller.kinect.Kinect;

public class KinectTest {

	@Test
	public void kinectTest() {

		Kinect sensor = new Kinect();
		
		sensor.onColorFrameEvent(null);
		sensor.onSkeletonFrameEvent(null,null,null,null);
		sensor.onDepthFrameEvent(null,null,null,null);
		
		Assert.assertNotNull(sensor);
		Assert.assertNull(sensor.getColorFrame());	
		Assert.assertNull(sensor.getDepthFrame());		
	}

}
