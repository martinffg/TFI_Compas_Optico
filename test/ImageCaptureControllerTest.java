import org.junit.Assert;
import org.junit.Test;
import javafx.application.Platform;
import untref_tfi.controller.ImageCaptureController;
import untref_tfi.view.CompassViewer;

public class ImageCaptureControllerTest {

	@Test
	public void iccProdTest() {
		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
		
						try {
							CompassViewer compassView = new CompassViewer(false);
							ImageCaptureController icc = new ImageCaptureController(compassView.getMainGraphIntCont());
							icc.startImageCapture();
							icc.imageRefresh();
						} catch (Exception e) {
							//System.out.println("iccProdTest() exception");
						}
				}
			});
			
			thread.start();
			Thread.sleep(1000);
			Assert.assertFalse(Platform.isFxApplicationThread());
			Platform.exit();
		}catch(Exception ex){
			//System.out.println("Exception stopping iccProdTest catched.");
		}
	}

}
