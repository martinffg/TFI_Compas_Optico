package untref_tfi.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import untref_tfi.controller.hardware.HardwareController;
import untref_tfi.controller.kinect.Kinect;

public class VerticalAngleSelectionPaneController {

	private final VBox panel;
	private TextField angleValue;
	@SuppressWarnings("unused")
	private HardwareController hwController=null;
	
	public VerticalAngleSelectionPaneController(String paneName,HardwareController hwController) {
		
		this.hwController=hwController;
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(60, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		angleValue = new TextField("0°");
		angleValue.setEditable(false);
		angleValue.setMaxSize(90, 50);
		angleValue.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		angleValue.setAlignment(Pos.CENTER);
		
		Slider sliderAnguloVertical = new Slider(-Kinect.kinectVerticalTiltAbsValue, Kinect.kinectVerticalTiltAbsValue, 0);
		sliderAnguloVertical.setMajorTickUnit(3);
		sliderAnguloVertical.setShowTickLabels(true);
		sliderAnguloVertical.setShowTickMarks(true);
		sliderAnguloVertical.setBlockIncrement(1);
		sliderAnguloVertical.setSnapToTicks(true);
		sliderAnguloVertical.setMinorTickCount(1);
		sliderAnguloVertical.setOrientation(Orientation.VERTICAL);
		sliderAnguloVertical.setPrefSize(100, 290);
		sliderAnguloVertical.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object.intValue() + "";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 3));
				}
		});
		
		sliderAnguloVertical.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	hwController.setElevationAngle(new_val.intValue());
        			angleValue.setText(String.valueOf(new_val.intValue())+"°");
                }
            });
		
		Button plusButton = new Button("+");
		plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	if (hwController.getElevationAngle()<Kinect.kinectVerticalTiltAbsValue){
								            		hwController.setElevationAngle(hwController.getElevationAngle()+1);
								        			angleValue.setText(String.valueOf(hwController.getElevationAngle())+"°");
								            	}
								            }	
							        });
		plusButton.setPrefSize(25, 25);
		plusButton.setStyle("-fx-text-fill: green; -fx-font-size: 10;");
		plusButton.setAlignment(Pos.CENTER);
		
		Button minusButton = new Button("-");
		minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	
								            	if (hwController.getElevationAngle()>-Kinect.kinectVerticalTiltAbsValue){
								            		hwController.setElevationAngle(hwController.getElevationAngle()-1);
								        			angleValue.setText(String.valueOf(hwController.getElevationAngle())+"°");
								            	}
								            }	
							        });
		minusButton.setPrefSize(25, 25);
		minusButton.setStyle("-fx-text-fill: green; -fx-font-size: 10;");
		minusButton.setAlignment(Pos.CENTER);
		
		VBox addremoveOnePanel = new VBox();
		addremoveOnePanel.getChildren().addAll(plusButton,minusButton);
		
		HBox anglePanel = new HBox();
		anglePanel.getChildren().addAll(addremoveOnePanel,angleValue);
		
		panel = new VBox();
		panel.getChildren().addAll(title,sliderAnguloVertical,anglePanel);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(120, 350);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}
	
}