package untref_tfi.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

public class HorizontalKinectAngleSelectionPaneController {

	private final VBox panel;
	private TextField angleValue;
	
	public HorizontalKinectAngleSelectionPaneController(String paneName,MainGraphicInterfaceController mgic) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(60, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		angleValue = new TextField("90°");
		angleValue.setEditable(false);
		angleValue.setMaxSize(100, 30);
		angleValue.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		angleValue.setAlignment(Pos.CENTER);
		
		HBox titlePanel = new HBox();
		titlePanel.getChildren().addAll(title, angleValue);
		titlePanel.setStyle("-fx-background-color: #6DF1D8;");
		titlePanel.setMinSize(290, 30);
		titlePanel.setAlignment(Pos.CENTER);
		titlePanel.setSpacing(75.0);
		
		Slider sliderAnguloHorizontal = new Slider(0,360, 90);
		sliderAnguloHorizontal.setMajorTickUnit(45);
		sliderAnguloHorizontal.setShowTickLabels(true);
		sliderAnguloHorizontal.setShowTickMarks(true);
		sliderAnguloHorizontal.setBlockIncrement(1);
		sliderAnguloHorizontal.setSnapToTicks(true);
		sliderAnguloHorizontal.setMinorTickCount(5);
		sliderAnguloHorizontal.setOrientation(Orientation.HORIZONTAL);
		sliderAnguloHorizontal.setPrefSize(290, 50);
		sliderAnguloHorizontal.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object.intValue() +"";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 3));
				}
		});
		
		sliderAnguloHorizontal.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    //mgic.setRotationAngle(new_val.intValue());
        			angleValue.setText(String.valueOf(new_val.intValue())+"°");
                }
            });
		
		panel = new VBox();
		panel.getChildren().addAll(titlePanel,sliderAnguloHorizontal);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(290, 90);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}
	
}