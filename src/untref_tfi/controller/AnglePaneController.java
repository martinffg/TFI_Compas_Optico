package untref_tfi.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class AnglePaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextField thetaTextField;
	private final TextField phiTextField;
	
	public AnglePaneController(String paneName) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMinSize(150, 25);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Image thetaImage = new Image(getClass().getResourceAsStream("../../resource/images/theta.jpg"));
		ImageView thetaImageView = new ImageView(thetaImage);
		//thetaImageView.setPreserveRatio(true);
		thetaImageView.setFitHeight(35);
		thetaImageView.setFitWidth(35);
		
		thetaTextField = new TextField(EMPTY_VALUE);
		thetaTextField.setEditable(false);
		thetaTextField.setMaxSize(70, 30);
		thetaTextField.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		thetaTextField.setAlignment(Pos.CENTER);
		
		VBox thetaPanel = new VBox();
		thetaPanel.getChildren().addAll(thetaImageView, thetaTextField);
		thetaPanel.setStyle("-fx-background-color: #6DF1D8;");
		thetaPanel.setMinSize(75, 90);
		thetaPanel.setAlignment(Pos.CENTER);
		thetaPanel.setSpacing(2.0);
		
		Image phiImage = new Image(getClass().getResourceAsStream("../../resource/images/phi.jpg"));
		ImageView phiImageView = new ImageView(phiImage);
		//phiImageView.setPreserveRatio(true);
		phiImageView.setFitHeight(35);
		phiImageView.setFitWidth(35);
		
		phiTextField = new TextField(EMPTY_VALUE);
		phiTextField.setEditable(false);
		phiTextField.setMaxSize(70, 30);
		phiTextField.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		phiTextField.setAlignment(Pos.CENTER);
		
		VBox phiPanel = new VBox();
		phiPanel.getChildren().addAll(phiImageView, phiTextField);
		phiPanel.setStyle("-fx-background-color: #6DF1D8;");
		phiPanel.setMinSize(75, 90);
		phiPanel.setAlignment(Pos.CENTER);
		phiPanel.setSpacing(2.0);
		
		HBox anglesPane = new HBox();
		anglesPane.getChildren().addAll(thetaPanel, phiPanel);
		anglesPane.setStyle("-fx-background-color: #6DF1D8;");
		anglesPane.setMinSize(150, 90);
		anglesPane.setAlignment(Pos.CENTER);
		anglesPane.setSpacing(2.0); 
		
		panel = new VBox();
		panel.getChildren().addAll(title, anglesPane);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(150, 90);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(thetaTextField.getText()) && !EMPTY_VALUE.equals(phiTextField.getText());
	}

	public void setXYvalues(double theta, double phi) {
		thetaTextField.setText(String.valueOf(theta));
		phiTextField.setText(String.valueOf(phi));
	}

	public void clearValues() {
		thetaTextField.setText(EMPTY_VALUE);
		phiTextField.setText(EMPTY_VALUE);
	}
}