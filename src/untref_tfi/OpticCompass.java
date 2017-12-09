package untref_tfi;

import javafx.application.Application;
import javafx.stage.Stage;
import untref_tfi.view.CompassViewer;

public class OpticCompass extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		CompassViewer compassView = new CompassViewer();
		compassView.start(primaryStage);
	}
	
}
