import org.junit.Assert;
import org.junit.Test;

import untref_tfi.controller.XYZpoint;
import untref_tfi.domain.AnglesCalculator;

public class AngleCalculatorTest {

	@Test
	public void AngleTest() {
		
		XYZpoint point1 = new XYZpoint(1,0,2.0);
		XYZpoint point2 = new XYZpoint(1,1,2.0);
		XYZpoint point3 = new XYZpoint(0,0,2.0);
		XYZpoint point4 = new XYZpoint(0,1,2.0);
		XYZpoint point5 = new XYZpoint(0,-1,-2.0);
		XYZpoint point6 = new XYZpoint(-1,0,-2.0);
		XYZpoint point7 = new XYZpoint(1,-1,-2.0);
		XYZpoint point8 = new XYZpoint(-1,-1,-2.0);
		
		AnglesCalculator angleCalc1 = new AnglesCalculator(point1);
		AnglesCalculator angleCalc2 = new AnglesCalculator(point2);
		AnglesCalculator angleCalc3 = new AnglesCalculator(point3);
		AnglesCalculator angleCalc4 = new AnglesCalculator(point4);
		AnglesCalculator angleCalc5 = new AnglesCalculator(point5);
		AnglesCalculator angleCalc6 = new AnglesCalculator(point6);
		AnglesCalculator angleCalc7 = new AnglesCalculator(point7);
		AnglesCalculator angleCalc8 = new AnglesCalculator(point8);
		
		Assert.assertEquals(1,angleCalc1.getPointXYZ().getXvalue(),0.1);
		Assert.assertEquals(0,angleCalc1.getPointXYZ().getYvalue(),0.1);
		Assert.assertEquals(0.004,angleCalc1.getPointXYZ().getXlength(),0.1);
		Assert.assertEquals(0,angleCalc1.getPointXYZ().getYlength(),0.1);
		Assert.assertEquals(2.0,angleCalc1.getPointXYZ().getZlength(),0.1);
		
		Assert.assertEquals(0.0,angleCalc1.getGamma(),0.1);
		Assert.assertEquals(0.11,angleCalc1.getPhi(),0.1);
		Assert.assertEquals(0.0,angleCalc1.getTheta(),0.1);
		
		
		Assert.assertEquals(0.11,angleCalc2.getGamma(),0.1);
		Assert.assertEquals(0.11,angleCalc2.getPhi(),0.1);
		Assert.assertEquals(45.0,angleCalc2.getTheta(),0.1);
		Assert.assertTrue(angleCalc2.isGammaCalculable());
		Assert.assertTrue(angleCalc2.isPhiCalculable());
		Assert.assertTrue(angleCalc2.isThetaCalculable());
		Assert.assertFalse(angleCalc2.isPointOnTheXYorigin());
		
		Assert.assertEquals(0.0,angleCalc3.getGamma(),0.1);
		Assert.assertEquals(0.0,angleCalc3.getPhi(),0.1);
		Assert.assertEquals(999.0,angleCalc3.getTheta(),0.1);
		
		Assert.assertEquals(0.11,angleCalc4.getGamma(),0.1);
		Assert.assertEquals(0.0,angleCalc4.getPhi(),0.1);
		Assert.assertEquals(90.0,angleCalc4.getTheta(),0.1);
		
		Assert.assertEquals(999.0,angleCalc5.getGamma(),0.1);
		Assert.assertEquals(999.0,angleCalc5.getPhi(),0.1);
		Assert.assertEquals(270.0,angleCalc5.getTheta(),0.1);
		
		Assert.assertEquals(999.0,angleCalc6.getGamma(),0.1);
		Assert.assertEquals(999.0,angleCalc6.getPhi(),0.1);
		Assert.assertEquals(180.0,angleCalc6.getTheta(),0.1);
		
		Assert.assertEquals(999.0,angleCalc7.getGamma(),0.1);
		Assert.assertEquals(999.0,angleCalc7.getPhi(),0.1);
		Assert.assertEquals(315.0,angleCalc7.getTheta(),0.1);
		
		Assert.assertEquals(999.0,angleCalc8.getGamma(),0.1);
		Assert.assertEquals(999.0,angleCalc8.getPhi(),0.1);
		Assert.assertEquals(225.0,angleCalc8.getTheta(),0.1);
	}

}
