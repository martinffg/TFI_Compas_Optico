package untref_tfi.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SystemScreenMessagesPaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextArea messageBox;
	private MainGraphicInterfaceController mgic=null;
	
	public SystemScreenMessagesPaneController(String paneName,MainGraphicInterfaceController mgictrl) {
		
		this.mgic=mgictrl;
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana",18));
		title.setAlignment(Pos.TOP_CENTER);
		title.setPrefSize(210, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
						
		Button cleanButton = new Button("Clean");
		cleanButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	if (mgic!=null) {
								            		mgic.cleanUpdateSystemMessagesPanel();
								            	}
								            }	
							        });
		
		cleanButton.setPrefSize(70, 20);
		cleanButton.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
		cleanButton.setAlignment(Pos.CENTER);
				
		HBox headerPane = new HBox();
		headerPane.setMaxSize(280, 20);
		headerPane.getChildren().addAll(title,cleanButton);
		headerPane.setSpacing(2.0);
		
		messageBox = new TextArea(EMPTY_VALUE);
		messageBox.setEditable(false);
		messageBox.setPrefSize(280, 110);
		messageBox.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
		messageBox.setWrapText(true);
		
		panel = new VBox();
		panel.getChildren().addAll(headerPane, messageBox);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setPrefSize(280,130);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedMessage() {
		return !EMPTY_VALUE.equals(messageBox.getText());
	}
	
	public void setMessage(String message){
		String previous=messageBox.getText();
		if (setedMessage()) {
			messageBox.setText(previous+"\n"+message); 
		} else {
			messageBox.setText(message); 
		}
	}

	public void clearMessage() {
		messageBox.setText(EMPTY_VALUE);
	}
}