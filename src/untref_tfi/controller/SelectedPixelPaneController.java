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
import untref_tfi.controller.hardware.HardwareController;

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
	private HardwareController hwController=null;
	private MainGraphicInterfaceController mgic=null;

	public SelectedPixelPaneController(String paneName, MainGraphicInterfaceController mgic) {
		
		this.mgic=mgic;
		this.hwController=mgic.getHardwareController();
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.CENTER);
		title.setWrapText(true);
		title.setPrefSize(120,50);
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
		yLabel.setMaxSize(60, 25);
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
		xLengthLabel.setMaxSize(120, 25);
		xLengthLabel.setAlignment(Pos.CENTER);
		xLengthLabel.setTextFill(Paint.valueOf("#29446B"));
		xLength = new TextField(EMPTY_VALUE);
		xLength.setEditable(false);
		xLength.setMaxSize(120, 25);
		xLength.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		xLength.setAlignment(Pos.CENTER);
		
		Label yLengthLabel = new Label("Y[depth]");
		yLengthLabel.setFont(Font.font ("Verdana", 16));
		yLengthLabel.setMaxSize(120, 25);
		yLengthLabel.setAlignment(Pos.CENTER);
		yLengthLabel.setTextFill(Paint.valueOf("#29446B"));
		yLength = new TextField(EMPTY_VALUE);
		yLength.setEditable(false);
		yLength.setMaxSize(120, 25);
		yLength.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		yLength.setAlignment(Pos.CENTER);
		
		Label zLengthLabel  = new Label("Z[depth]");
		zLengthLabel.setFont(Font.font ("Verdana", 16));
		zLengthLabel.setMaxSize(120, 25);
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
		xyColor.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		xyColor.setAlignment(Pos.CENTER);
		
		Button focusButton = new Button("Focus On It");
		focusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	setFocusOnIt();
								            }	
							        });
		focusButton.setMaxSize(120, 25);
		focusButton.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		focusButton.setAlignment(Pos.CENTER);
		
		Button backToStartButton = new Button("Go to Start");
		backToStartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	setBackToStartPoint();
								            }	
							        });
		backToStartButton.setMaxSize(120, 25);
		backToStartButton.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		backToStartButton.setAlignment(Pos.CENTER);
		
		Button backToPreviousPointButton = new Button("Previous");
		backToPreviousPointButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	setBackToPreviousPoint();
								            }	
							        });
		backToPreviousPointButton.setMaxSize(120, 25);
		backToPreviousPointButton.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		backToPreviousPointButton.setAlignment(Pos.CENTER);
		
		panel = new VBox();
		panel.getChildren().addAll(title, posRefPane, posValPane,xLengthLabel,xLength,yLengthLabel,yLength,zLengthLabel,zLength,xyColorLabel,xyColor,focusButton,backToPreviousPointButton,backToStartButton);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(120, 500);
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
	
	private void setFocusOnIt(){
		
		XYZpoint focusedPoint = mgic.getLastSelectedPixel();
		
		if (focusedPoint.isFocusablePoint()) {
			double relativeElevation = focusedPoint.getAnglesCalculator().getGamma();
			int absoluteElevation = (int) Math.round(hwController.getElevationAngle()+relativeElevation);
			double relativeRotation = focusedPoint.getAnglesCalculator().getPhi();
			if (Math.abs(absoluteElevation)<=27) {  // HW limit for kinect Elevetion degree
				hwController.moveArduinoController(relativeRotation);
				hwController.setElevationAngle(absoluteElevation);			
			} else {
				System.out.println("Punto no enfocable, fuera de rango de HW - Vertical Focus Out Of Range");
			}
		} else {
			System.out.println("Punto no enfocable, fuera de rango de HW - Vertical Focus Out Of Range");
		}
	}
	
	private void setBackToStartPoint(){
		hwController.setElevationAngle(0);
		double correctionAngel = (-1)*hwController.getRotationAngle();
		hwController.moveArduinoController(correctionAngel);
		hwController.setRotationAngle(hwController.getRotationAngle());
	}
	
	private void setBackToPreviousPoint(){
		XYZpoint lastFocusedPoint = mgic.getLastSelectedPixel();
		XYZpoint previousFocusedPoint=mgic.getPreviousSelectedPixel();
		XYZpoint previousEstimatedPoint=null;
		
		if ((lastFocusedPoint!=null) && (lastFocusedPoint.isFocusablePoint())&&(previousFocusedPoint!=null)){
		
			previousEstimatedPoint = new XYZpoint((-1)*lastFocusedPoint.getXvalue(),
													(-1)*lastFocusedPoint.getYvalue(),
													previousFocusedPoint.getZlength(),
													previousFocusedPoint.getColorString());
			mgic.setPreviousSelectedPixel(previousEstimatedPoint);	
			mgic.swapSelectedPixel();
			setFocusOnIt();
			mgic.updateDisplayPanels();
		} else {
			System.out.print("No es posible obtener punto previo");
		}
	}
}