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
import untref_tfi.controller.hardware.HorizontalAngleRotationController;

public class RotationDelaySelectionPaneController {

	private final VBox panel;
	private TextField timeWaitValue;
	private TextField increasePercentValue;
	private HorizontalAngleRotationController hwRotationController=null;
		
	public RotationDelaySelectionPaneController(String paneName,HardwareController hwController) {
		
		this.hwRotationController=hwController.getHorizontalCtrl();
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 11));
		title.setAlignment(Pos.TOP_CENTER);
		title.setPrefSize(205, 15);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label titleDelay = new Label("Speed Delay[ms]");
		titleDelay.setFont(Font.font ("Verdana", 11));
		titleDelay.setAlignment(Pos.TOP_CENTER);
		titleDelay.setPrefSize(120, 15);
		titleDelay.setTextFill(Paint.valueOf("#29446B"));
				
		Label titlePercent = new Label("Speed Curve[%]");
		titlePercent.setFont(Font.font ("Verdana", 11));
		titlePercent.setAlignment(Pos.TOP_CENTER);
		titlePercent.setPrefSize(120, 15);
		titlePercent.setTextFill(Paint.valueOf("#29446B"));
		
		timeWaitValue = new TextField("5");
		timeWaitValue.setEditable(false);
		timeWaitValue.setMaxSize(55,15);
		timeWaitValue.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		timeWaitValue.setAlignment(Pos.CENTER);
		
		increasePercentValue = new TextField("0");
		increasePercentValue.setEditable(false);
		increasePercentValue.setMaxSize(55,15);
		increasePercentValue.setStyle("-fx-text-fill: green; -fx-font-size: 14;");
		increasePercentValue.setAlignment(Pos.CENTER);
		
		Button plusButton = new Button("+");
		plusButton.setPrefSize(15, 15);
		plusButton.setFont(Font.font ("Verdana", 10));
		plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwRotationController.setTimeWaitThread(1);
								            	timeWaitValue.setText(String.valueOf(hwRotationController.getTimeWaitThread()));
								            }	
							        });
		
		Button plusButtonPercent = new Button("+");
		plusButtonPercent.setPrefSize(15, 15);
		plusButtonPercent.setFont(Font.font ("Verdana", 10));
		plusButtonPercent.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwRotationController.setIncreasePercent(1);
								            	increasePercentValue.setText(String.valueOf(hwRotationController.getIncreasePercent()));
								            }	
							        });
		
		Button minusButton = new Button("-");
		minusButton.setPrefSize(15, 15);
		minusButton.setFont(Font.font ("Verdana", 10));
		minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwRotationController.setTimeWaitThread(-1);
								            	timeWaitValue.setText(String.valueOf(hwRotationController.getTimeWaitThread()));
								            }	
							        });
		
		Button minusButtonPercent = new Button("-");
		minusButtonPercent.setPrefSize(15, 15);
		minusButtonPercent.setFont(Font.font ("Verdana", 10));
		minusButtonPercent.setOnMouseClicked(new EventHandler<MouseEvent>() {
								            public void handle(MouseEvent e) {
								            	hwRotationController.setIncreasePercent(-1);
								            	increasePercentValue.setText(String.valueOf(hwRotationController.getIncreasePercent()));
								            }	
							        });
				
		HBox titlePanel = new HBox();
		titlePanel.getChildren().addAll(titleDelay,minusButton,timeWaitValue,plusButton);
		titlePanel.setStyle("-fx-background-color: #6DF1D8;");
		titlePanel.setMaxSize(205, 15);
		titlePanel.setAlignment(Pos.CENTER);
		titlePanel.setSpacing(2.0);
		
		HBox titlePanelPercent = new HBox();
		titlePanelPercent.getChildren().addAll(titlePercent,minusButtonPercent,increasePercentValue,plusButtonPercent);
		titlePanelPercent.setStyle("-fx-background-color: #6DF1D8;");
		titlePanelPercent.setMaxSize(205, 15);
		titlePanelPercent.setAlignment(Pos.CENTER);
		titlePanelPercent.setSpacing(2.0);
		
		Slider sliderTimeWaitHorizontal = new Slider(HorizontalAngleRotationController.minorTimeWait,HorizontalAngleRotationController.majorTimeWait,HorizontalAngleRotationController.minorTimeWait);
		sliderTimeWaitHorizontal.setMajorTickUnit(5);
		sliderTimeWaitHorizontal.setShowTickLabels(true);
		sliderTimeWaitHorizontal.setShowTickMarks(true);
		sliderTimeWaitHorizontal.setBlockIncrement(1);
		sliderTimeWaitHorizontal.setSnapToTicks(true);
		sliderTimeWaitHorizontal.setMinorTickCount(1);
		sliderTimeWaitHorizontal.setOrientation(Orientation.HORIZONTAL);
		sliderTimeWaitHorizontal.setPrefSize(205, 15);
		
		sliderTimeWaitHorizontal.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object.intValue()+"";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 1));
				}
		});
		
		sliderTimeWaitHorizontal.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        			timeWaitValue.setText(String.valueOf(new_val.intValue()));
        			hwRotationController.setTimeWaitThread(new_val.intValue());
            }
        });
		
		panel = new VBox();
		panel.getChildren().addAll(title,titlePanelPercent,titlePanel,sliderTimeWaitHorizontal);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setPrefSize(200,120);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}
	
	public VBox getPane() {
		return this.panel;
	}
}