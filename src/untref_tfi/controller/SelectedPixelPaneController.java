package untref_tfi.controller;

import javafx.geometry.Insets;
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
	private final TextField xLength;
	private final TextField yLength;
	private final TextField zLength;
	private final TextField xyColor;
	private XYZpoint selectedPoint=null;

	public SelectedPixelPaneController(String paneName) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMaxSize(130, 25);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label xLabel = new Label("x");
		xLabel.setFont(Font.font ("Verdana", 16));
		xLabel.setMinSize(60, 25);
		xLabel.setAlignment(Pos.CENTER);
		xLabel.setTextFill(Paint.valueOf("#29446B"));
		xPos = new TextField(EMPTY_VALUE);
		xPos.setEditable(false);
		xPos.setMaxSize(60, 25);
		xPos.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xPos.setAlignment(Pos.CENTER);
		
		Label yLabel = new Label("y");
		yLabel.setFont(Font.font ("Verdana", 16));
		yLabel.setMinSize(60, 25);
		yLabel.setAlignment(Pos.CENTER);
		yLabel.setTextFill(Paint.valueOf("#29446B"));
		yPos = new TextField(EMPTY_VALUE);
		yPos.setEditable(false);
		yPos.setMaxSize(60, 25);
		yPos.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		yPos.setAlignment(Pos.CENTER);
		
		HBox posRefPane = new HBox();
		posRefPane.setMaxSize(120, 25);
		posRefPane.getChildren().addAll(xLabel, yLabel);
		posRefPane.setSpacing(5.0);
		
		HBox posValPane = new HBox();
		posValPane.setMaxSize(120, 25);
		posValPane.getChildren().addAll(xPos, yPos);
		posValPane.setSpacing(3.0);
		
		Label xLengthLabel = new Label("X[depth]");
		xLengthLabel.setFont(Font.font ("Verdana", 16));
		xLengthLabel.setMinSize(120, 25);
		xLengthLabel.setAlignment(Pos.CENTER);
		xLengthLabel.setTextFill(Paint.valueOf("#29446B"));
		xLength = new TextField(EMPTY_VALUE);
		xLength.setEditable(false);
		xLength.setMaxSize(120, 25);
		xLength.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xLength.setAlignment(Pos.CENTER);
		
		Label yLengthLabel = new Label("Y[depth]");
		yLengthLabel.setFont(Font.font ("Verdana", 16));
		yLengthLabel.setMinSize(120, 25);
		yLengthLabel.setAlignment(Pos.CENTER);
		yLengthLabel.setTextFill(Paint.valueOf("#29446B"));
		yLength = new TextField(EMPTY_VALUE);
		yLength.setEditable(false);
		yLength.setMaxSize(120, 25);
		yLength.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		yLength.setAlignment(Pos.CENTER);
		
		Label zLengthLabel  = new Label("Z[depth]");
		zLengthLabel.setFont(Font.font ("Verdana", 16));
		zLengthLabel.setMinSize(120, 25);
		zLengthLabel.setAlignment(Pos.CENTER);
		zLengthLabel.setTextFill(Paint.valueOf("#29446B"));
		zLength = new TextField(EMPTY_VALUE);
		zLength.setEditable(false);
		zLength.setMaxSize(120, 25);
		zLength.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		zLength.setAlignment(Pos.CENTER);
		
		
		Label xyColorLabel = new Label("color[R;G;B]");
		xyColorLabel.setFont(Font.font ("Verdana", 16));
		xyColorLabel.setMaxSize(120, 25);
		xyColorLabel.setAlignment(Pos.CENTER);
		xyColorLabel.setTextFill(Paint.valueOf("#29446B"));
		xyColor = new TextField(EMPTY_VALUE);
		xyColor.setEditable(false);
		xyColor.setMaxSize(120, 25);
		xyColor.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xyColor.setAlignment(Pos.CENTER);
		
		panel = new VBox();
		panel.getChildren().addAll(title, posRefPane, posValPane,xLengthLabel,xLength,yLengthLabel,yLength,zLengthLabel,zLength,xyColorLabel,xyColor);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(130, 300);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(3.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(xPos.getText()) && !EMPTY_VALUE.equals(yPos.getText());
	}

	public void setXYZvalues(XYZpoint pixel,String rgbColor){
		this.selectedPoint = pixel;
		xPos.setText(String.valueOf(pixel.getXvalue()));
		yPos.setText(String.valueOf(pixel.getYvalue()));
		xLength.setText(String.format("%.3f", pixel.getXlength())+"m");
		yLength.setText(String.format("%.3f", pixel.getYlength())+"m");
		zLength.setText(String.format("%.3f", pixel.getZlength())+"m");
		xyColor.setText(rgbColor);	
	}

	public void clearValues() {
		xPos.setText(EMPTY_VALUE);
		yPos.setText(EMPTY_VALUE);
		xLength.setText(EMPTY_VALUE);
		yLength.setText(EMPTY_VALUE);
		zLength.setText(EMPTY_VALUE);
	}

	public XYZpoint getXYZpoint(){
		return this.selectedPoint;
	}
}