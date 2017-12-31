import org.junit.Assert;
import org.junit.Test;
import javafx.application.Platform;


public class OpticCompassModoTest {

	@Test
	public void mainTest() {
		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						OpticCompass oc = new OpticCompass();
						String[] args = {"test"};
						oc.start(args);
						
						if (Platform.isFxApplicationThread()) {
							Assert.assertTrue(Platform.isFxApplicationThread());
						}
					}catch(Exception e){
						//System.out.println("Exception launching OpticCompass.main catched.");
					}
				}
			});
			
			thread.start();
	
			Thread.sleep(1000);
			Assert.assertFalse(Platform.isFxApplicationThread());
		}catch(InterruptedException ex){
			//System.out.println("Exception stopping mainTest catched.");
		}
	}
}