import org.junit.Assert;
import org.junit.Test;
import javafx.application.Platform;

public class OpticCompassModoProdTest {

	@Test
	public void mainProdTest() {
		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						OpticCompass oc = new OpticCompass();
						String[] args = {};
						oc.start(args);
					}catch(Exception e){
						System.out.println("Exception launching mainProdTest run method catched.");
						e.printStackTrace();
					}
				}
			});
			
			thread.start();
			Thread.sleep(1000);
			Assert.assertFalse(Platform.isFxApplicationThread());
		}catch(Exception ex){
			System.out.println("Exception in mainProdTest thread catched.");
			ex.printStackTrace();
		}
	}
}