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
import javafx.scene.paint.Color;
import untref_tfi.controller.hardware.HorizontalAngleRotationController;
import untref_tfi.controller.hardware.HorizontalAngleSelectionPaneController;
import untref_tfi.controller.hardware.VerticalAngleSelectionPaneController;


public class MainGraphicInterfaceController {
	
	private Image kinectImage;
	private ImageView kinectImageView;
	private ImageView imageRosaView;
	private ImageView imageAngulosView;
	private ImageView imageRosaIconView;
	private ImageCaptureController imageCapture;
	private SelectedPixelPaneController pixelPanel;
	private SettingsPaneController outOfRangePanel;
	private VerticalAngleSelectionPaneController verticalAnglePanel;
	private HorizontalAngleSelectionPaneController horizontalAnglePanel;
	private HorizontalAngleRotationController horizontalAngleController;
	private AnglePaneController angleValuesPanel;
	private KinectAnglePositionPaneController kinectAnglePositionPanel;
	private Scene mainScene;
	public static final int maxWidth=640;
	public static final int maxLength=480;
	private static final int zeroXref=maxWidth/2;  // 0Xref: 320
	private static final int zeroYref=maxLength/2; // 0Yref: 240
	private int selectedXpoint=zeroXref;
	private int selectedYpoint=zeroYref;
	private double selectedZPoint=0.0;
	private String selectedColorPoint="";
	private XYZpoint lastSelectedPixel=null;
	private XYZpoint previousSelectedPixel=null;
	private boolean depthImageSelected=false;
	private boolean dynamicMousePointerSelection=false;
	private Color colorOOR= Color.GRAY;
	private int elevationAngle=0;
	private double rotationAngle=0.0;
	
	public MainGraphicInterfaceController(boolean isTest){
		try {
			initializeMGIC(isTest);
		} catch (Exception e) {
			initializeMGIC(true);
		}
	}
	
	public void initializeMGIC(boolean isTest){
		imageCapture = new ImageCaptureController(this,isTest);
		imageCapture.startImageCapture();
		createKinectImageView();
		createImageRosaView();
		createImageAngulosView();
		createImageRosaIconView();
		angleValuesPanel = new AnglePaneController("Results");
		pixelPanel = new SelectedPixelPaneController("Point Info",this);
		outOfRangePanel = new SettingsPaneController("Settings",this);
		verticalAnglePanel = new VerticalAngleSelectionPaneController("V_Elevate",this);
		horizontalAngleController = new HorizontalAngleRotationController();
		horizontalAnglePanel = new HorizontalAngleSelectionPaneController("H_Rotate",horizontalAngleController,this);
		kinectAnglePositionPanel = new KinectAnglePositionPaneController("Sensor Position");
		this.setElevationAngle(0);
		this.setRotationAngle(0.0);
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
	
	public void enableDepthImageSelection(){
		this.depthImageSelected=true;
	}
	
	public void disableDepthImageSelection(){
		this.depthImageSelected=false;
	}
	
	public void enableDynamicMousePointer(){
		this.dynamicMousePointerSelection=true;
	}
	
	public void disableDynamicMousePointer(){
		this.dynamicMousePointerSelection=false;
	}
	
	public Color getColorOOR(){
		
		return this.colorOOR;

	}
	
	public void setColorOutOfRange(Color color){
		this.colorOOR=color;
	}
	
	public XYZpoint getLastSelectedPixel(){
		return this.lastSelectedPixel;
	}
	
	public XYZpoint getPreviousSelectedPixel(){
		return this.previousSelectedPixel;
	}
	
	public int getElevationAngle(){
		return this.elevationAngle;
	}
	
	public void setElevationAngle(int elevation){
		this.elevationAngle=elevation;
		kinectAnglePositionPanel.setHVvalues(rotationAngle, elevationAngle);
	}
	
	public double getRotationAngle(){
		return this.rotationAngle;
	}
	
	public void setRotationAngle(double rotation){
		this.rotationAngle+=rotation;
		kinectAnglePositionPanel.setHVvalues(rotationAngle, elevationAngle);
	}
	
	public HorizontalAngleSelectionPaneController getHorizontalAnglePanel(){
		return this.horizontalAnglePanel;
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
            	try {
            		swapSelectedPixel();
            		getAllInfoAboutXYCartesianSelectedPoint(e); 
            		updateDisplayPanels(e);
            		/*
            		if (lastSelectedPixel != null) {	
            			System.out.println("Ultimo: "+lastSelectedPixel.getXlength()
            			+" "+lastSelectedPixel.getYlength()
            			+" "+lastSelectedPixel.getZlength());
            		}
            		*/	
            	} catch (Exception ex){
            		System.out.println("Modo Test - Funcion deshabilitada");
            	}
            }	
        });
		kinectImageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	if (dynamicMousePointerSelection){
	            	try {
	            		getAllInfoAboutXYCartesianSelectedPoint(e); 
	            		updateDisplayPanels(e);
	            	} catch (Exception ex){
	            		System.out.println("Modo Test - Funcion deshabilitada");
	            	}
            	}
            }	
        });
	}
	
	private void updateDisplayPanels(MouseEvent e) {
		pixelPanel.setXYZvalues(lastSelectedPixel,selectedColorPoint);
    	angleValuesPanel.setAnglesValues(lastSelectedPixel.getAnglesCalculator());
	}
	
	private void getAllInfoAboutXYCartesianSelectedPoint(MouseEvent e) {
		selectedXpoint=(int) (e.getX() -zeroXref);			// Convert X to cartesian axe
    	selectedYpoint=(int) (e.getY() * -1) + zeroYref;	// Convert Y to cartesian axe
    	//System.out.println(e.getX());
    	//System.out.println(e.getY());
    	selectedZPoint = imageCapture.getXYMatrizProfundidad((int)e.getX(),(int)e.getY());
    	selectedColorPoint = imageCapture.getXYMatrizRGBColorCadena((int)e.getX(),(int)e.getY());
    	lastSelectedPixel=new XYZpoint(selectedXpoint,selectedYpoint,selectedZPoint);
	}

	private AnchorPane createAnchorPane(){
		
		Pane pixelPane=pixelPanel.getPane();
		Pane outOfRangePane=outOfRangePanel.getPane();
		Pane verticalAnglePane=verticalAnglePanel.getPane();
		Pane horizontalAnglePane=horizontalAnglePanel.getPane();
		Pane angleValuesPane=angleValuesPanel.getPane();
		Pane kinectAnglePositionPane=kinectAnglePositionPanel.getPane();
		List<Node> principalPaneChildrens = new ArrayList<Node>();
		principalPaneChildrens.addAll(Arrays.asList(imageRosaView,imageAngulosView,kinectImageView,imageRosaIconView,pixelPane,outOfRangePane,
				verticalAnglePane,horizontalAnglePane,angleValuesPane,kinectAnglePositionPane));
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
		AnchorPane.setLeftAnchor(pixelPane, 20.0);
		AnchorPane.setBottomAnchor(kinectAnglePositionPane, 40.0);
		AnchorPane.setLeftAnchor(kinectAnglePositionPane, 155.0);
		AnchorPane.setTopAnchor(outOfRangePane, 240.0);
		AnchorPane.setRightAnchor(outOfRangePane, 20.0);
		AnchorPane.setBottomAnchor(verticalAnglePane, 140.0);
		AnchorPane.setRightAnchor(verticalAnglePane, 20.0);
		AnchorPane.setTopAnchor(angleValuesPane, 210.0);
		AnchorPane.setLeftAnchor(angleValuesPane, 20.0);
		AnchorPane.setBottomAnchor(horizontalAnglePane, 40.0);
		AnchorPane.setRightAnchor(horizontalAnglePane, 20.0);
	
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
	
	public void updateSensorPositionPanel(){
		kinectAnglePositionPanel.setHVvalues(rotationAngle, elevationAngle);
	}
	
	private void swapSelectedPixel() {
	
		previousSelectedPixel=lastSelectedPixel;
		if (previousSelectedPixel != null) {		
			/*
			System.out.println("Previo: "+previousSelectedPixel.getXlength()
			+" "+previousSelectedPixel.getYlength()
			+" "+previousSelectedPixel.getZlength()); 
			*/
		}		
	}
}
