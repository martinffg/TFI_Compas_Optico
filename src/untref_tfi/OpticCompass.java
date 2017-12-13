package untref_tfi;

import javafx.application.Application;
import javafx.stage.Stage;
import untref_tfi.view.CompassViewer;

public class OpticCompass extends Application {
	
	private static boolean isTestMode=false;
	
	public static void main(String[] args) {
		if (args.length>=1) {
			isTestMode=true;
		}else {
			isTestMode=false;
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		CompassViewer compassView = new CompassViewer(isTestMode);
		compassView.start(primaryStage);
	}
	
}
