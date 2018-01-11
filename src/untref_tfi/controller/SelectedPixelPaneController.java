package untref_tfi.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SelectedPixelPaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextField xPos;
	private final TextField yPos;
	private final TextField xyDepthZ;
	private final TextField xyColor;
	private XYZpoint selectedPoint=null;

	public SelectedPixelPaneController(String paneName) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(150, 30);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label xLabel = new Label("x");
		xLabel.setFont(Font.font ("Verdana", 20));
		xLabel.setMinSize(70, 30);
		xLabel.setAlignment(Pos.CENTER);
		xLabel.setTextFill(Paint.valueOf("#29446B"));
		xPos = new TextField(EMPTY_VALUE);
		xPos.setEditable(false);
		xPos.setMaxSize(70, 30);
		xPos.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xPos.setAlignment(Pos.CENTER);
		
		Label yLabel = new Label("y");
		yLabel.setFont(Font.font ("Verdana", 20));
		yLabel.setMinSize(70, 30);
		yLabel.setAlignment(Pos.CENTER);
		yLabel.setTextFill(Paint.valueOf("#29446B"));
		yPos = new TextField(EMPTY_VALUE);
		yPos.setEditable(false);
		yPos.setMaxSize(70, 30);
		yPos.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		yPos.setAlignment(Pos.CENTER);
		
		HBox posRefPane = new HBox();
		posRefPane.setMaxSize(140, 30);
		posRefPane.getChildren().addAll(xLabel, yLabel);
		posRefPane.setSpacing(5.0);
		
		HBox posValPane = new HBox();
		posValPane.setMaxSize(140, 30);
		posValPane.getChildren().addAll(xPos, yPos);
		posValPane.setSpacing(3.0);
			
		
		Label xyDepthLabel = new Label("Z_depth[m]");
		xyDepthLabel.setFont(Font.font ("Verdana", 20));
		xyDepthLabel.setMinSize(140, 30);
		xyDepthLabel.setAlignment(Pos.CENTER);
		xyDepthLabel.setTextFill(Paint.valueOf("#29446B"));
		xyDepthZ = new TextField(EMPTY_VALUE);
		xyDepthZ.setEditable(false);
		xyDepthZ.setMaxSize(140, 30);
		xyDepthZ.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xyDepthZ.setAlignment(Pos.CENTER);
		
		
		Label xyColorLabel = new Label("color [R;G;B]");
		xyColorLabel.setFont(Font.font ("Verdana", 20));
		xyColorLabel.setMinSize(140, 30);
		xyColorLabel.setAlignment(Pos.CENTER);
		xyColorLabel.setTextFill(Paint.valueOf("#29446B"));
		xyColor = new TextField(EMPTY_VALUE);
		xyColor.setEditable(false);
		xyColor.setMaxSize(140, 30);
		xyColor.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xyColor.setAlignment(Pos.CENTER);
		
		panel = new VBox();
		panel.getChildren().addAll(title, posRefPane, posValPane,xyDepthLabel,xyDepthZ,xyColorLabel,xyColor);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(150, 285);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(5.0);
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(xPos.getText()) && !EMPTY_VALUE.equals(yPos.getText());
	}

	public void setXYZvalues(int x, int y, double z_depth, String rgbColor) {
		xPos.setText(String.valueOf(x));
		yPos.setText(String.valueOf(y));
		xyDepthZ.setText(String.format("%.3f", z_depth));
		xyColor.setText(rgbColor);
		this.selectedPoint = new XYZpoint(x,y,z_depth);
	}

	public void clearValues() {
		xPos.setText(EMPTY_VALUE);
		yPos.setText(EMPTY_VALUE);
		xyDepthZ.setText(EMPTY_VALUE);
	}

	public XYZpoint getXYZpoint(){
		return this.selectedPoint;
	}
}