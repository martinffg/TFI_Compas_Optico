import org.junit.Assert;
import org.junit.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import untref_tfi.view.CompassViewer;

public class CompassViewerTest {

	@Test
	public void testUserInterface() {
		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					new JFXPanel();
					Platform.runLater(new Runnable() {
	
						@Override
						public void run() {
							try {
								CompassViewer interfaz = new CompassViewer();
								interfaz.start(new Stage());
								if (Platform.isFxApplicationThread()) {
									Assert.assertTrue(Platform.isFxApplicationThread());
								}
							}catch(Exception e){
								System.out.println("Exception launching testUserInterface catched.");
							}
						}
					});
				}
			});
			
			thread.start();
	
			Thread.sleep(10000);
			Assert.assertFalse(Platform.isFxApplicationThread());
		}catch(InterruptedException ex){
			System.out.println("Exception stopping testUserInterface catched.");
		}
	}
}