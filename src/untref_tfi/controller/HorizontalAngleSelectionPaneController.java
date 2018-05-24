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

public class HorizontalAngleSelectionPaneController {

	private final VBox panel;
	private TextField angleValue;
	private Double angleTarget;
	private HardwareController hwController=null;
	private double oneStepAngle = 0.0;
	
	public HorizontalAngleSelectionPaneController(String paneName,HardwareController hwController) {
		
		this.hwController=hwController;
		angleTarget=0.0;
		oneStepAngle = hwController.getHorizontalCtrl().getOneStepDegree();
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 16));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(40, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		angleValue = new TextField("0.0°");
		angleValue.setEditable(false);
		angleValue.setMaxSize(75, 30);
		angleValue.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		angleValue.setAlignment(Pos.CENTER);
		
		Button jfxButton = new Button("Apply");
		jfxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	applyHorizontalMoveButton();
								            }	
							        });
		
		Button plusButton = new Button("+");
		plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwController.moveArduinoController(oneStepAngle);
								            }	
							        });
		
		Button minusButton = new Button("-");
		minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwController.moveArduinoController(-oneStepAngle);
								            }	
							        });
				
		HBox titlePanel = new HBox();
		titlePanel.getChildren().addAll(title,minusButton,angleValue,plusButton,jfxButton);
		titlePanel.setStyle("-fx-background-color: #6DF1D8;");
		titlePanel.setMaxSize(290, 30);
		titlePanel.setAlignment(Pos.CENTER);
		titlePanel.setSpacing(5.0);
		
		Slider sliderAnguloHorizontal = new Slider(-180,180,0.0);
		sliderAnguloHorizontal.setMajorTickUnit(45.0);
		sliderAnguloHorizontal.setShowTickLabels(true);
		sliderAnguloHorizontal.setShowTickMarks(true);
		sliderAnguloHorizontal.setBlockIncrement(oneStepAngle);
		sliderAnguloHorizontal.setSnapToTicks(true);
		sliderAnguloHorizontal.setMinorTickCount(18);
		sliderAnguloHorizontal.setOrientation(Orientation.HORIZONTAL);
		sliderAnguloHorizontal.setPrefSize(300, 50);
		
		
		sliderAnguloHorizontal.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object.intValue()+"";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 1));
				}
		});
		
		sliderAnguloHorizontal.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        			angleValue.setText(String.valueOf(String.format("%.1f", new_val)+"°"));
        			angleTarget = new_val.doubleValue();
        			//System.out.println("Angulo Objetivo Actual:" +angleTarget);
            }
        });
		
		panel = new VBox();
		panel.getChildren().addAll(titlePanel,sliderAnguloHorizontal);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMaxSize(318,90);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}
	
	private void applyHorizontalMoveButton() {
		hwController.moveArduinoController(angleTarget);
		System.out.println("Angulo a mover: "+angleTarget);
	}
}