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
import untref_tfi.domain.AnglesCalculator;

public class AnglePaneController {

	private static final String EMPTY_VALUE = "";
	private final VBox panel;
	private final TextField thetaTextField;
	private final TextField phiTextField;
	private final TextField gammaTextField;
	
	public AnglePaneController(String paneName) {
		
		Label title = new Label(paneName);
		title.setFont(Font.font ("Verdana", 20));
		title.setAlignment(Pos.TOP_CENTER);
		title.setMaxSize(120, 25);
		title.setTextFill(Paint.valueOf("#29446B"));
		
		Label thetaTitle = new Label("[ X -> Y ]");
		thetaTitle.setFont(Font.font ("Verdana", 20));
		thetaTitle.setAlignment(Pos.TOP_CENTER);
		thetaTitle.setMaxSize(120, 25);
		thetaTitle.setTextFill(Paint.valueOf("#29446B"));
		
		Image thetaImage = new Image(getClass().getResourceAsStream("../../resource/images/theta.jpg"));
		ImageView thetaImageView = new ImageView(thetaImage);
		//thetaImageView.setPreserveRatio(true);
		thetaImageView.setFitHeight(35);
		thetaImageView.setFitWidth(35);
		
		thetaTextField = new TextField(EMPTY_VALUE);
		thetaTextField.setEditable(false);
		thetaTextField.setMaxSize(73, 35);
		thetaTextField.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		thetaTextField.setAlignment(Pos.CENTER);
		
		HBox thetaPanel = new HBox();
		thetaPanel.getChildren().addAll(thetaImageView, thetaTextField);
		thetaPanel.setStyle("-fx-background-color: #6DF1D8;");
		thetaPanel.setMinSize(73, 40);
		thetaPanel.setAlignment(Pos.CENTER);
		thetaPanel.setSpacing(2.0);
		
		Label phiTitle = new Label("[ Z -> X ]");
		phiTitle.setFont(Font.font ("Verdana", 20));
		phiTitle.setAlignment(Pos.TOP_CENTER);
		phiTitle.setMaxSize(120, 25);
		phiTitle.setTextFill(Paint.valueOf("#29446B"));
				
		Image phiImage = new Image(getClass().getResourceAsStream("../../resource/images/phi.jpg"));
		ImageView phiImageView = new ImageView(phiImage);
		//phiImageView.setPreserveRatio(true);
		phiImageView.setFitHeight(35);
		phiImageView.setFitWidth(35);
		
		phiTextField = new TextField(EMPTY_VALUE);
		phiTextField.setEditable(false);
		phiTextField.setMaxSize(73, 35);
		phiTextField.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		phiTextField.setAlignment(Pos.CENTER);
		
		HBox phiPanel = new HBox();
		phiPanel.getChildren().addAll(phiImageView, phiTextField);
		phiPanel.setStyle("-fx-background-color: #6DF1D8;");
		phiPanel.setMinSize(73, 40);
		phiPanel.setAlignment(Pos.CENTER);
		phiPanel.setSpacing(2.0);
		
		Label gammaTitle = new Label("[ Z -> Y ]");
		gammaTitle.setFont(Font.font ("Verdana", 20));
		gammaTitle.setAlignment(Pos.TOP_CENTER);
		gammaTitle.setMaxSize(120, 25);
		gammaTitle.setTextFill(Paint.valueOf("#29446B"));
		
		Image gammaImage = new Image(getClass().getResourceAsStream("../../resource/images/gamma.jpg"));
		ImageView gammaImageView = new ImageView(gammaImage);
		//gammaImageView.setPreserveRatio(true);
		gammaImageView.setFitHeight(35);
		gammaImageView.setFitWidth(35);
		
		gammaTextField = new TextField(EMPTY_VALUE);
		gammaTextField.setEditable(false);
		gammaTextField.setMaxSize(73, 35);
		gammaTextField.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		gammaTextField.setAlignment(Pos.CENTER);
		
		HBox gammaPanel = new HBox();
		gammaPanel.getChildren().addAll(gammaImageView, gammaTextField);
		gammaPanel.setStyle("-fx-background-color: #6DF1D8;");
		gammaPanel.setMinSize(73, 40);
		gammaPanel.setAlignment(Pos.CENTER);
		gammaPanel.setSpacing(2.0);
		
		VBox anglesPane = new VBox();
		anglesPane.getChildren().addAll(thetaTitle,thetaPanel,phiTitle,phiPanel,gammaTitle,gammaPanel);
		anglesPane.setStyle("-fx-background-color: #6DF1D8;");
		anglesPane.setMinSize(120, 90);
		anglesPane.setAlignment(Pos.CENTER);
		anglesPane.setSpacing(2.0); 
		
		panel = new VBox();
		panel.getChildren().addAll(title, anglesPane);
		panel.setStyle("-fx-background-color: #6DF1D8; -fx-border-color: #29446B; -fx-border-width:2px; -fx-border-style: solid;");
		panel.setMinSize(120, 90);
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(2.0);
		panel.setPadding(new Insets(2,2,2,2));
	}

	public VBox getPane() {
		return this.panel;
	}

	public boolean setedValues() {
		return !EMPTY_VALUE.equals(thetaTextField.getText()) 
				&& !EMPTY_VALUE.equals(phiTextField.getText())
				&& !EMPTY_VALUE.equals(gammaTextField.getText());
	}

	private void setAnglesPhiValues(String theta, String phi, String gamma) {
		thetaTextField.setText(theta);
		phiTextField.setText(phi);
		gammaTextField.setText(gamma);
	}

	public void clearValues() {
		thetaTextField.setText(EMPTY_VALUE);
		phiTextField.setText(EMPTY_VALUE);
		gammaTextField.setText(EMPTY_VALUE);
	}
	
	public void calculateAnglesFromPoint(XYZpoint selectedXYZpoint) {
		
    	AnglesCalculator angCalculator = new AnglesCalculator(selectedXYZpoint);
    	String theta="N/A";
    	String phi="N/A";
    	String gamma="N/A";
    	if (angCalculator.isThetaCalculable()){
    		theta= String.format("%.1f", angCalculator.getTheta())+"°";
    	}
    	if (angCalculator.isPhiCalculable()){
    		phi= String.format("%.1f", angCalculator.getPhi())+"°";
    	}
    	if (angCalculator.isGammaCalculable()){
    		gamma= String.format("%.1f", angCalculator.getGamma())+"°";
    	}
    	this.setAnglesPhiValues(theta, phi, gamma);
	}
}