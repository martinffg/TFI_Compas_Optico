import org.junit.Assert;
import org.junit.Test;

import untref_tfi.controller.kinect.KinectDepthMeassureFixerController;

public class KinectDepthMeassureFixerControllerTest {

	@Test
	public void KinectDepthMeassureFixerControllerMethodsTest() {
		
		double meassure = 2.0;
		
		KinectDepthMeassureFixerController kdmfc = new KinectDepthMeassureFixerController(meassure); 
		
		Assert.assertEquals(0.36,kdmfc.getDeltaErrorOnDepthMeassured(),0.1);
		Assert.assertEquals(2.36,kdmfc.getRealKinectDepthMeassure(),0.1);
		Assert.assertEquals(2.0,kdmfc.getUnfixedKinectDepthMeassure(),0.1);
	
	}

}
