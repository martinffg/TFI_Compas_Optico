import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import untref_tfi.controller.MainGraphicInterfaceController;

public class MainGraphicInterfaceControllerTest extends Application {
	
	private MainGraphicInterfaceController mgic=null;
	private Scene myScene= null;
	private Color color = new Color(120,120,120,1);
	
	@Test
	public void mgicTest() {
		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						String[] args = {};
						MainGraphicInterfaceControllerTest.main(args);
					}catch(Exception e){
						System.out.println("Exception in Thread run method."); 
						e.printStackTrace();
					}
				}
			});
			
			thread.start();
			Assert.assertFalse(Platform.isFxApplicationThread());
			Thread.sleep(3000);
		}catch(Exception ex){
			System.out.println("Exception in Thread");
			ex.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mgic = new MainGraphicInterfaceController(true);
		myScene = mgic.getMainScene();
		mgic.disableDepthImageSelection();
		mgic.enableDepthImageSelection();
		mgic.setElevationAngle(0);
		mgic.setColorOutOfRange(120,120,120,1);
		mgic.setImageController(mgic.getImageCaptureController());
		mgic.setKinectImage(mgic.getImageRosaIconView().getImage());
		mgic.setKinectImageView(mgic.getImageRosaView());
		
		primaryStage.setScene(myScene);
		primaryStage.setTitle("Compas Optico Testing");
		primaryStage.setWidth(1280);
		primaryStage.setHeight(1024);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	       @Override
	       public void handle(WindowEvent e) {
	    	   System.exit(0);
	       }
		});
		primaryStage.show();
		Assert.assertNotNull(mgic);
		Assert.assertTrue(mgic.isDepthImageSelected());
		Assert.assertEquals(0, mgic.getElevationAngle());
		Assert.assertEquals(color,mgic.getColorOOR());
		Assert.assertNotNull(mgic.getImageCaptureController());
		Assert.assertNotNull(mgic.getImageRosaIconView());
		Assert.assertNotNull(mgic.getImageRosaView());
		Assert.assertNotNull(mgic.getKinectImage());
		Assert.assertNotNull(mgic.getKinectImageView());
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
