package untref_tfi.controller.kinect;

import edu.ufl.digitalworlds.j4k.J4KSDK;

public class Kinect extends J4KSDK {
	
	public static final int screenWidth=640;
	public static final int screenHeight=480;
	public static final float kinectVerticalTiltAbsValue = 27; 
	public static final double kinectFullVerticalViewingAngle=43;
	public static final double kinectFullHorizontalViewingAngle=57;
	public static final float maxDistanceMMAllowed = 37500;//3,60 m
	public static final float minDistanceMMAllowed = 7000;// 0,80 m
	
	public Kinect() {
		setColorResolution(screenWidth, screenHeight);
		setDepthResolution(screenWidth, screenHeight);
	}
	
	@Override
	public void onColorFrameEvent(byte[] arg0) {		
		// requested by J4KSDK (abstract method)
	}

	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2,float[] arg3) {
		// requested by J4KSDK (abstract method)
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] arg0, float[] arg1,float[] arg2, byte[] arg3) {
		// requested by J4KSDK (abstract method)
	}
}