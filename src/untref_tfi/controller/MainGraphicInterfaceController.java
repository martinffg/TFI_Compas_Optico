package untref_tfi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainGraphicInterfaceController {
	
	private Image kinectImage;
	private ImageView kinectImageView;
	private ImageView imageRosaView;
	private ImageView imageAngulosView;
	private ImageView imageRosaIconView;
	private ImageCaptureController imageCapture;
	private SelectedPixelPaneController pixelPanel;
	private Scene mainScene;
	private static final int maxWidth=640;
	private static final int maxlength=480;
	private static final int zeroXref=maxWidth/2;  // 0Xref: 320
	private static final int zeroYref=maxlength/2; // 0Yref: 240
	private int selectedXpoint=zeroXref;
	private int selectedYpoint=zeroYref;
	private boolean depthImageSelected=true;
	
	public MainGraphicInterfaceController(boolean isTest){
		
		imageCapture = new ImageCaptureController(this,isTest);
		imageCapture.startImageCapture();
		createKinectImageView();
		createImageRosaView();
		createImageAngulosView();
		createImageRosaIconView();
		pixelPanel= new SelectedPixelPaneController("Point Information");		
	}
	
	public Scene getMainScene(){
		
		AnchorPane anchorpane = createAnchorPane();
		StackPane mainPane = new StackPane();
		mainPane.getChildren().add(anchorpane);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: #C7F2FE");
		mainScene = new Scene(mainPane);
		return mainScene;
	}
	
	public ImageCaptureController getImageCaptureController(){
		return imageCapture;
	}
		
	public void setImageController(ImageCaptureController imageController) {
		imageCapture = imageController;
	}

	public Image getKinectImage() {
		return kinectImage;
	}

	public void setKinectImage(Image image) {
		kinectImage = image;
	}

	public ImageView getKinectImageView() {
		return kinectImageView;
	}

	public void setKinectImageView(ImageView imageView) {
		kinectImageView = imageView;
	}

	public ImageView getImageRosaView() {
		return imageRosaView;
	}

	public ImageView getImageRosaIconView() {
		return imageRosaIconView;
	}
	
	public boolean isDepthImageSelected(){
		
		return this.depthImageSelected;
	}
	
	private void createKinectImageView(){
		
		kinectImageView = new ImageView(kinectImage);
		kinectImageView.setPreserveRatio(true);
		kinectImageView.setFitHeight(480);
		kinectImageView.setFitWidth(640);
		kinectImageView.boundsInLocalProperty();
		kinectImageView.setPickOnBounds(true);
		kinectImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
 
            public void handle(MouseEvent e) {

            	convertXYclicToCartesianSelectedPoint(e);
            	
            	pixelPanel.setXYvalues(selectedXpoint,selectedYpoint,
            			imageCapture.getXYMatrizProfundidad((int)e.getX(),(int)e.getY()),
            			imageCapture.getXYMatrizRGBColorCadena((int)e.getX(),(int)e.getY()));
       
            }

			
        });
	}
	
	private void convertXYclicToCartesianSelectedPoint(MouseEvent e) {
		selectedXpoint=(int) (e.getX() -zeroXref);
    	selectedYpoint=(int) (e.getY() * -1) + zeroYref;
	}

	private AnchorPane createAnchorPane(){
		
		Pane pixelPane=pixelPanel.getPane();
		List<Node> principalPaneChildrens = new ArrayList<Node>();
		principalPaneChildrens.addAll(Arrays.asList(imageRosaView,imageAngulosView,kinectImageView,imageRosaIconView,pixelPane));
		AnchorPane anchorpane = new AnchorPane();
		anchorpane.getChildren().addAll(principalPaneChildrens);
		AnchorPane.setTopAnchor(imageRosaIconView, 40.0);
		AnchorPane.setRightAnchor(imageRosaIconView, 20.0);	
		AnchorPane.setTopAnchor(imageAngulosView, 40.0);
		AnchorPane.setLeftAnchor(imageAngulosView, 20.0);
		AnchorPane.setTopAnchor(imageRosaView, 4.0);
		AnchorPane.setBottomAnchor(imageRosaView, 4.0);
		AnchorPane.setLeftAnchor(imageRosaView, 128.0);
		AnchorPane.setTopAnchor(kinectImageView, 272.0);
		AnchorPane.setBottomAnchor(kinectImageView, 272.0);
		AnchorPane.setLeftAnchor(kinectImageView, 320.0);
		AnchorPane.setBottomAnchor(pixelPane, 40.0);
		AnchorPane.setRightAnchor(pixelPane, 20.0);
		
		return anchorpane;
	}
	
	private void createImageRosaIconView() {
		Image imageRosaDeLosVientos = new Image(getClass().getResourceAsStream("../../resource/images/rosa_de_los_vientos.jpg"));
		imageRosaIconView = new ImageView(imageRosaDeLosVientos);
		imageRosaIconView.setPreserveRatio(true);
		imageRosaIconView.setFitHeight(180);
		imageRosaIconView.setFitWidth(280);
	}

	private void createImageRosaView() {
		Image imageRosaDeLosVientos = new Image(getClass().getResourceAsStream("../../resource/images/rosa_de_los_vientos.jpg"));
		imageRosaView = new ImageView(imageRosaDeLosVientos);
		imageRosaView.setPreserveRatio(true);
		imageRosaView.setFitHeight(1024);
		imageRosaView.setFitWidth(1280);
	}
	
	private void createImageAngulosView() {
		Image imageAngulos = new Image(getClass().getResourceAsStream("../../resource/images/angulos.jpg"));
		imageAngulosView = new ImageView(imageAngulos);
		imageAngulosView.setPreserveRatio(true);
		imageAngulosView.setFitHeight(160);
		imageAngulosView.setFitWidth(200);
	}
	

}
