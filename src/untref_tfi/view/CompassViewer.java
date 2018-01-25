package untref_tfi.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import untref_tfi.controller.MainGraphicInterfaceController;

public class CompassViewer extends Application{
	
	private Scene myScene;
	private MainGraphicInterfaceController mainGIController;
	private boolean isTestMode=false;
	
	public CompassViewer(boolean isTest){
		isTestMode=isTest;
		//System.out.println("Cvw Testmode: "+isTestMode);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainGIController = new MainGraphicInterfaceController(isTestMode);
		myScene = mainGIController.getMainScene();		
		primaryStage.setScene(myScene);
		primaryStage.setTitle("Compas Optico");
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
	}
	
	public MainGraphicInterfaceController getMainGraphIntCont(){
		return mainGIController;
	}
}
