package untref_tfi.controller;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

public class ImageCaptureRefresh extends TimerTask {
	
	private ImageCaptureController captureController;
	
	public ImageCaptureRefresh(ImageCaptureController captCtr){
		this.captureController=captCtr;
	}

	private void execute(ImageCaptureController captCtr) {
		Platform.runLater(() -> {
			
			Timer timer = new Timer();
			long period = 100; // (1 / 10) * 1000; // actual 10 fps - max 30 fps con period 34
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					captCtr.imageRefresh();
				}				
			}, 0, period);
		 });
	}

	@Override
	public void run() {
		execute(this.captureController);	
	}

}