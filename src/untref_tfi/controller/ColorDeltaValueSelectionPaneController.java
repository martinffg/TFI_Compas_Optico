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

public class ColorDeltaValueSelectionPaneController {

	private final VBox panel;
	private TextField colorDeltaValue;
	private MainGraphicInterfaceController mgic;
		
	public ColorDeltaValueSelectionPaneController(String paneName,MainGraphicInterfaceController mainigc) {
		
		this.mgic=mainigc;
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 17));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(50, 20);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		colorDeltaValue = new TextField("30");
		colorDeltaValue.setEditable(false);
		colorDeltaValue.setPrefSize(50,14);
		colorDeltaValue.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
		colorDeltaValue.setAlignment(Pos.CENTER);
		
		Slider sliderColorDeltaValue = new Slider(0,50,30);
		sliderColorDeltaValue.setMajorTickUnit(5);
		sliderColorDeltaValue.setShowTickLabels(true);
		sliderColorDeltaValue.setShowTickMarks(true);
		sliderColorDeltaValue.setBlockIncrement(1);
		sliderColorDeltaValue.setSnapToTicks(true);
		sliderColorDeltaValue.setMinorTickCount(1);
		sliderColorDeltaValue.setOrientation(Orientation.HORIZONTAL);
		sliderColorDeltaValue.setPrefSize(50, 120);
		sliderColorDeltaValue.setLabelFormatter(new StringConverter<Double>(){
				@Override
				public String toString(Double object) {
					return object.intValue() + "";
				}
				
				@Override
				public Double fromString(String string) {
					return new Double(string.substring(0, string.length() - 3));
				}
		});
		
		sliderColorDeltaValue.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            		ActiveContoursControllerAdapter adapter=mgic.getActiveContoursController();
            		if (adapter!=null){
            			adapter.setObjectColorDeltaValue(new_val.intValue());
            		}
            		colorDeltaValue.setText(String.valueOf(new_val.intValue()));
                }
            });
		
		Button plusButton = new Button("+");
		plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			            public void handle(MouseEvent e) {
			            	ActiveContoursControllerAdapter adapter=mgic.getActiveContoursController();
		            		if (adapter!=null){
				            	if (adapter.getObjectColorDeltaValue()<=49){
				            		adapter.setObjectColorDeltaValue(adapter.getObjectColorDeltaValue()+1);
				            		colorDeltaValue.setText(String.valueOf(adapter.getObjectColorDeltaValue()));
				            
				            	}
			            	}
			            }
		        });
		plusButton.setFont(Font.font ("Verdana", 10));
		plusButton.setPrefSize(20,14);
		plusButton.setStyle("-fx-text-fill: green; -fx-font-size: 10;");
		plusButton.setAlignment(Pos.CENTER);
		
		Button minusButton = new Button("-");
		minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			            public void handle(MouseEvent e) {
			            	ActiveContoursControllerAdapter adapter=mgic.getActiveContoursController();
		            		if (adapter!=null){
				            	if (adapter.getObjectColorDeltaValue()>=1){
				            		adapter.setObjectColorDeltaValue(adapter.getObjectColorDeltaValue()-1);
				            		colorDeltaValue.setText(String.valueOf(adapter.getObjectColorDeltaValue()));
				            	}
		            		}
			            }
		        });
		
		minusButton.setFont(Font.font ("Verdana", 10));
		minusButton.setPrefSize(20,14);
		minusButton.setStyle("-fx-text-fill: green; -fx-font-size: 10;");
		minusButton.setAlignment(Pos.CENTER);
		
		HBox colorDeltaPanel = new HBox();
		colorDeltaPanel.getChildren().addAll(plusButton,colorDeltaValue,minusButton);
		colorDeltaPanel.setPrefSize(100,20);
		colorDeltaPanel.setSpacing(1.0);
		
		HBox firstRowColorDeltaPanel = new HBox();
		firstRowColorDeltaPanel.getChildren().addAll(title,colorDeltaPanel);
		firstRowColorDeltaPanel.setSpacing(5.0);
		
		panel = new VBox();
		panel.getChildren().addAll(firstRowColorDeltaPanel,sliderColorDeltaValue);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setPrefSize(215,50);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}
	
}