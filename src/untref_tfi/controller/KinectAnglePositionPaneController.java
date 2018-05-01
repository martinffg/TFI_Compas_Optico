package untref_tfi.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class KinectAnglePositionPaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextField hPos;
	private final TextField vPos;
	
	public KinectAnglePositionPaneController(String paneName) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMaxSize(166, 25);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label hLabel = new Label("Horizontal");
		hLabel.setFont(Font.font ("Verdana", 14));
		hLabel.setMinSize(80, 25);
		hLabel.setAlignment(Pos.CENTER);
		hLabel.setTextFill(Paint.valueOf("#29446B"));
		hPos = new TextField(EMPTY_VALUE);
		hPos.setEditable(false);
		hPos.setMaxSize(80, 25);
		hPos.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		hPos.setAlignment(Pos.CENTER);
		
		Label vLabel = new Label("Vertical");
		vLabel.setFont(Font.font ("Verdana", 14));
		vLabel.setMinSize(80, 25);
		vLabel.setAlignment(Pos.CENTER);
		vLabel.setTextFill(Paint.valueOf("#29446B"));
		vPos = new TextField(EMPTY_VALUE);
		vPos.setEditable(false);
		vPos.setMaxSize(80, 25);
		vPos.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		vPos.setAlignment(Pos.CENTER);
		
		VBox hPane = new VBox();
		hPane.setMaxSize(80, 25);
		hPane.getChildren().addAll(hLabel, hPos);
		hPane.setSpacing(3.0);
		
		VBox vPane = new VBox();
		vPane.setMaxSize(80, 25);
		vPane.getChildren().addAll(vLabel, vPos);
		vPane.setSpacing(3.0);
		
		HBox valuesPane = new HBox();
		valuesPane.setMaxSize(166, 25);
		valuesPane.getChildren().addAll(hPane, vPane);
		valuesPane.setSpacing(3.0);
		
		panel = new VBox();
		panel.getChildren().addAll(title, valuesPane);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(170, 100);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(3.0);
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