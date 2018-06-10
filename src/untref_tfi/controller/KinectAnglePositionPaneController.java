package untref_tfi.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class KinectAnglePositionPaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextField hPos;
	private final TextField vPos;
	private MainGraphicInterfaceController mgic=null;
	
	public KinectAnglePositionPaneController(String paneName,MainGraphicInterfaceController mgictrl) {
		
		this.mgic=mgictrl;
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana",14));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMaxSize(150, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label hLabel = new Label("Horizontal");
		hLabel.setFont(Font.font ("Verdana", 11));
		hLabel.setMinSize(75, 20);
		hLabel.setAlignment(Pos.CENTER);
		hLabel.setTextFill(Paint.valueOf("#29446B"));
		hPos = new TextField(EMPTY_VALUE);
		hPos.setEditable(false);
		hPos.setMaxSize(75, 20);
		hPos.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		hPos.setAlignment(Pos.CENTER);
		
		Label vLabel = new Label("Vertical");
		vLabel.setFont(Font.font ("Verdana", 11));
		vLabel.setMinSize(75, 20);
		vLabel.setAlignment(Pos.CENTER);
		vLabel.setTextFill(Paint.valueOf("#29446B"));
		vPos = new TextField(EMPTY_VALUE);
		vPos.setEditable(false);
		vPos.setMaxSize(75, 20);
		vPos.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		vPos.setAlignment(Pos.CENTER);
		
		VBox hPane = new VBox();
		hPane.setMaxSize(75, 20);
		hPane.getChildren().addAll(hLabel, hPos);
		hPane.setSpacing(2.0);
		
		VBox vPane = new VBox();
		vPane.setMaxSize(75, 20);
		vPane.getChildren().addAll(vLabel, vPos);
		vPane.setSpacing(2.0);
		
		HBox valuesPane = new HBox();
		valuesPane.setMaxSize(150, 20);
		valuesPane.getChildren().addAll(hPane, vPane);
		valuesPane.setSpacing(2.0);
		
		Button cleanCalibrateButton = new Button("Clean / Calibrate");
		cleanCalibrateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	mgic.cleanUpdateSensorPositionPanel();
								            }	
							        });
		cleanCalibrateButton.setMaxSize(150, 15);
		cleanCalibrateButton.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
		cleanCalibrateButton.setAlignment(Pos.CENTER);
		
		panel = new VBox();
		panel.getChildren().addAll(title, valuesPane,cleanCalibrateButton);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMaxSize(150, 90);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(hPos.getText()) && !EMPTY_VALUE.equals(vPos.getText());
	}
	
	public void setHVvalues(double hValue,int vValue){
		
		hPos.setText(String.valueOf(String.format("%.2f", hValue)+"°"));
		vPos.setText(vValue+"°");
	}

	public void clearValues() {
		hPos.setText(EMPTY_VALUE);
		vPos.setText(EMPTY_VALUE);
	}
}