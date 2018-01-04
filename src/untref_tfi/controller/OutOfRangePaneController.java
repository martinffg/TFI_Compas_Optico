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
import javafx.scene.text.Font;

public class OutOfRangePaneController {
	
	private final VBox panel;
	private final MainGraphicInterfaceController mgic;
	
	public OutOfRangePaneController(String paneName,MainGraphicInterfaceController mainGrapIntCont){
		this.mgic=mainGrapIntCont;
		Label title = new Label(paneName);
		title.setFont(new Font(16));
		
		String[] checkBoxNames = new String[]{"OOR pixels"};
		CheckBox[] cbs = new CheckBox[checkBoxNames.length];
		for (int i = 0; i < checkBoxNames.length; i++) {
			cbs[i] = new CheckBox(checkBoxNames[i]);
		}
		
		// Defino evento por cada checkbox
		cbs[0].setOnMouseClicked(getMouseEventHandler(cbs));
		
		VBox vbox = new VBox(cbs);
		Separator separator = new Separator();
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(checkBoxNames.length, separator);
		
			
		Label labelColor = new Label("OOR Color");
		labelColor.setFont(new Font(16));
		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setPrefSize(110, 40);
		//colorPicker.setOnAction((e) -> );

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			
            public void handle(ActionEvent e) {
            	Color colorOOR=colorPicker.getValue();
            	labelColor.setTextFill(colorOOR);
            	mgic.setColorOutOfRange((float) colorOOR.getRed(),(float) colorOOR.getGreen(),(float) colorOOR.getBlue(),(float) colorOOR.getOpacity());    
            }		
        });
		
		VBox vbox2 = new VBox(colorPicker);
		
		panel = new VBox();
		panel.getChildren().addAll(title,vbox,labelColor,vbox2);
		panel.setSpacing(5);
		panel.setBackground(Background.EMPTY);
		panel.setPadding(new Insets(5));
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(110, 150);
		panel.setAlignment(Pos.CENTER);
		
	}

	private EventHandler<MouseEvent> getMouseEventHandler(CheckBox[] cbs) {
		return new EventHandler<MouseEvent>() {
			
			private void evaluateOORCheckBoxAction(CheckBox oorCbs){
				
				if (oorCbs.isSelected()) {
					mgic.enableDepthImageSelection();
				}else{
					mgic.disableDepthImageSelection();
				}
				
			}
 
            public void handle(MouseEvent e) {

            	this.evaluateOORCheckBoxAction(cbs[0]);
  
            }		
        };
	}
	
	public VBox getPane() {
		return this.panel;
	}

}
