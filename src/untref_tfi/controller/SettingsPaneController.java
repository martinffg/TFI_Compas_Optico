package untref_tfi.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SettingsPaneController {
	
	private final VBox panel;
	private final MainGraphicInterfaceController mgic;
	
	public SettingsPaneController(String paneName,MainGraphicInterfaceController mainGrapIntCont){
		this.mgic=mainGrapIntCont;
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(110, 25);
		title.setTextFill(Paint.valueOf("#29446B"));
				
		String[] checkBoxNames = new String[]{"Dynam Calcs","OOF pixels","OOR pixels"};
		CheckBox[] cbs = new CheckBox[checkBoxNames.length];
		for (int i = 0; i < checkBoxNames.length; i++) {
			cbs[i] = new CheckBox(checkBoxNames[i]);
		}
		
		// Defino evento por cada checkbox
		cbs[0].setOnMouseClicked(getMouseEventHandler(cbs,0));
		cbs[1].setOnMouseClicked(getMouseEventHandler(cbs,1));
		cbs[2].setOnMouseClicked(getMouseEventHandler(cbs,2));
		
		VBox vbox = new VBox(cbs);
		Separator separator = new Separator();
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().add(checkBoxNames.length, separator);
		vbox.setSpacing(5.0);
		vbox.setPadding(new Insets(2,2,2,2));
		
			
		Label labelColor = new Label("OOR Color");
		labelColor.setFont(new Font(16));
		ColorPicker colorPicker = new ColorPicker(Color.GRAY);
		colorPicker.setPrefSize(110, 40);
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {	
			
            public void handle(ActionEvent e) {
            	Color colorOOR=colorPicker.getValue();
            	labelColor.setTextFill(colorOOR);
            	mgic.setColorOutOfRange(colorOOR); 
            }		
        });
		
		VBox vbox2 = new VBox(colorPicker);
		
		panel = new VBox();
		panel.getChildren().addAll(title,vbox,labelColor,vbox2);
		panel.setBackground(Background.EMPTY);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(110, 150);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(5.0);
		panel.setPadding(new Insets(2,2,2,2));
		
	}

	private EventHandler<MouseEvent> getMouseEventHandler(CheckBox[] cbs,int pos) {
		return new EventHandler<MouseEvent>() {
			
			private void evaluateDynamicMousePointerCheckBoxAction(CheckBox dmpCbs){
				
				if (dmpCbs.isSelected()) {
					mgic.enableDynamicMousePointer();
				}else{
					mgic.disableDynamicMousePointer();
				}
				
			}
			
			private void evaluateOOFCheckBoxAction(CheckBox oofCbs){
				
				if (oofCbs.isSelected()) {
					mgic.enableOutOfFocusPointsSelection();
				}else{
					mgic.disableOutOfFocusPointsSelection();
				}
				
			}
			
			private void evaluateOORCheckBoxAction(CheckBox oorCbs){
				
				if (oorCbs.isSelected()) {
					mgic.enableDepthImageSelection();
				}else{
					mgic.disableDepthImageSelection();
				}
				
			}
 
            public void handle(MouseEvent e) {
            	switch (pos) {
            		case 0: this.evaluateDynamicMousePointerCheckBoxAction(cbs[0]);
            		break;
            		case 1: this.evaluateOOFCheckBoxAction(cbs[1]);
            		break;
            		case 2: this.evaluateOORCheckBoxAction(cbs[2]);
            		break;
            		default:{}
            	}
  
            }		
        };
	}
	
	public VBox getPane() {
		return this.panel;
	}

}
