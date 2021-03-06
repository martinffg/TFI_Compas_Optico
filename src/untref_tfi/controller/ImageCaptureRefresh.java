package untref_tfi.controller;

import java.util.Timer;
import java.util.TimerTask;

public class ImageCaptureRefresh extends TimerTask {
	
	private static final long DEFAULT_PERIOD = 100; // (1 / 10) * 1000; // 10 fps (max 30 fps) para modo normal
	private static final long MAXIMUM_PERIOD = 333; // (1 / 3) * 1000; // 3 fps para Active Contours
	private static final long DEFAULT_DELAY = 0;
	private static final long MAXIMUM_DELAY = 40;
	private long valuePeriod=0;
	private long valueDelay=0;
	private ImageCaptureController captureController;
	private Timer timer = null;
	
	public ImageCaptureRefresh(ImageCaptureController captCtr){
		this.captureController=captCtr;
		this.valuePeriod=DEFAULT_PERIOD;
		this.valueDelay=DEFAULT_DELAY;
	}

	private void execute(ImageCaptureController captCtr) {
				
		timer = new Timer();
		
		if (!captureController.isAutoTrackingEnabled()){
			this.valuePeriod = DEFAULT_PERIOD;
			this.valueDelay= DEFAULT_DELAY;
		} else {
			this.valuePeriod = MAXIMUM_PERIOD;
			this.valueDelay= MAXIMUM_DELAY;
		}
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				captCtr.imageRefresh();
			}				
		}, valueDelay, valuePeriod);	 
	}

	@Override
	public void run() {
		execute(this.captureController);	
	}
	
	public void stop(){
		timer.cancel();
		timer.purge();
	}
}