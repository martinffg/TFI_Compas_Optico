package untref_tfi.pkg_ActiveContours;

import javafx.application.Application;
import javafx.stage.Stage;
import untref_tfi.pkg_ActiveContours.controllers.ActiveContoursController;

public class PrincipalGraphicInterface extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		new ActiveContoursController();

		
	}
}




