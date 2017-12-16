package untref_tfi.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class OpticCompassLauncher extends Application {
	
	private static boolean isTestMode=false;
	
	public static void main(String[] args) {
		if (args.length==0) {
			isTestMode=false;
		} else if (args[0].equals("test")) {
			isTestMode=true;
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		CompassViewer compassView = new CompassViewer(isTestMode);
		compassView.start(primaryStage);
	}
	
}
