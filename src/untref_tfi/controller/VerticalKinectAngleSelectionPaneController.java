package untref_tfi.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

public class VerticalKinectAngleSelectionPaneController {

	private final VBox panel;
	private TextField angleValue;
	
	public VerticalKinectAngleSelectionPaneController(String paneName,MainGraphicInterfaceController mgic) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(60, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		angleValue = new TextField("0°");
		angleValue.setEditable(false);
		angleValue.setMaxSize(70, 30);
		angleValue.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		angleValue.setAlignment(Pos.CENTER);
		
		Slider sliderAnguloVertical = new Slider(-27, 27, 0);
		sliderAnguloVertical.setMajorTickUnit(3);
		sliderAnguloVertical.setShowTickLabels(true);
		sliderAnguloVertical.setShowTickMarks(true);
		sliderAnguloVertical.setBlockIncrement(1);
		sliderAnguloVertical.setSnapToTicks(true);
		sliderAnguloVertical.setMinorTickCount(1);
		sliderAnguloVertical.setOrientation(Orientation.VERTICAL);
		sliderAnguloVertical.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object + "°";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 3));
				}
		});
		
		sliderAnguloVertical.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    mgic.setElevationAngle(new_val.intValue());
        			angleValue.setText(String.valueOf(new_val.intValue())+"°");
                }
            });
		
		panel = new VBox();
		panel.getChildren().addAll(title, sliderAnguloVertical,angleValue);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(100, 250);
		panel.setAlignment(Pos.CENTER);
	}

	public VBox getPane() {
		return this.panel;
	}
	
}