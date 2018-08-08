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
import untref_tfi.controller.hardware.HardwareController;
import untref_tfi.controller.kinect.Kinect;
import untref_tfi.domain.XYZpoint;

public class MainGraphicInterfaceController {
	
	private Image kinectImage;
	private ImageView kinectImageView;
	private ImageView imageRosaView;
	private ImageView imageEjesView;
	private ImageView imageRosaIconView;
	private ImageCaptureController imageCapture;
	private HardwareController hwController;
	private ActiveContoursControllerAdapter actCountControler;
	private SelectedPixelPaneController pixelPanel;
	private SettingsPaneController settingsPanel;
	private VerticalAngleSelectionPaneController verticalAnglePanel;
	private HorizontalAngleSelectionPaneController horizontalAnglePanel;
	private AnglePaneController angleValuesPanel;
	private KinectAnglePositionPaneController kinectAnglePositionPanel;
	private SystemScreenMessagesPaneController systemScreenMessagesPanel;
	private RotationDelaySelectionPaneController delayPanel;
	private Scene mainScene;
	public static final int zeroXref=Kinect.screenWidth/2;  // 0Xref: 320
	public static final int zeroYref=Kinect.screenHeight/2; // 0Yref: 240
	private int selectedXpoint=zeroXref;
	private int selectedYpoint=zeroYref;
	private double selectedDepthPoint=0.0;
	private String selectedColorPoint="";
	private XYZpoint lastSelectedPixel=null;
	private XYZpoint previousSelectedPixel=null;
	private boolean depthImageSelected=false;
	private boolean autoTrackingSelected=false;
	private boolean outOfFocusPointsSelected=false;
	private boolean dynamicMousePointerSelection=false;
	private Color colorOOR= Color.GRAY;
	private boolean testMode=false;
			
	public MainGraphicInterfaceController(boolean isTest){
		
		try {
			initializeMGIC(isTest);
		} catch (Exception e) {
			initializeMGIC(true);
			//e.printStackTrace();
		}
	}
	
	public void initializeMGIC(boolean isTest){
		testMode=isTest;
		hwController = new HardwareController(this);
		powerOnCapture();
		createKinectImageView();
		createImageRosaView();
		createImageEjesView();
		createImageRosaIconView();
		angleValuesPanel = new AnglePaneController("Relative\nAngles");
		pixelPanel = new SelectedPixelPaneController("Point Clic",this);
		settingsPanel = new SettingsPaneController("Settings",this);
		verticalAnglePanel = new VerticalAngleSelectionPaneController("V_Elevate",hwController);
		horizontalAnglePanel = new HorizontalAngleSelectionPaneController("H_Rotate",hwController);
		kinectAnglePositionPanel = new KinectAnglePositionPaneController("Sensor Position",this);
		systemScreenMessagesPanel= new SystemScreenMessagesPaneController("Messages Panel",this);
		delayPanel=new RotationDelaySelectionPaneController("H_Rotation_Speed_Settings",this.getHardwareController());
	}
	
	public MainGraphicInterfaceController getMainGraphicInterfaceController(){
		return this;
	}

	private void powerOnCapture() {
		imageCapture = new ImageCaptureController(this);
		imageCapture.startImageCapture();
	}
	
	private void restartCapture() {
		imageCapture.stopImageCapture();
		powerOnCapture();
	}
	
	public boolean isTestMode(){
		return testMode;
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
	
	public boolean isAutotrackingSelected(){
		
		return this.autoTrackingSelected;
	}
	
	public void enableAutotrackingSelection(){
		this.autoTrackingSelected=true;
		restartCapture();
		actCountControler= new ActiveContoursControllerAdapter(this);
		if (actCountControler!=null){
			System.out.println("Active Contour creado");
		}
	}
	
	public void disableAutotrackingSelection(){
		this.autoTrackingSelected=false;
		restartCapture();
		actCountControler=null;
		System.out.println("Active Contour eliminado");
	}	
	
	public boolean isOutOfFocusPointsSelected(){
		
		return this.outOfFocusPointsSelected;
	}
	
	public void enableOutOfFocusPointsSelection(){
		this.outOfFocusPointsSelected=true;
	}
	
	public void disableOutOfFocusPointsSelection(){
		this.outOfFocusPointsSelected=false;
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
	
	public void setLastSelectedPixel(XYZpoint point){
		this.lastSelectedPixel=point;
	}
	
	public XYZpoint getPreviousSelectedPixel(){
		return this.previousSelectedPixel;
	}
	
	public void setPreviousSelectedPixel(XYZpoint point){
		this.previousSelectedPixel=point;
	}
	
	private void createKinectImageView(){
		
		kinectImageView = new ImageView(kinectImage);
		kinectImageView.setPreserveRatio(true);
		kinectImageView.setFitHeight(Kinect.screenHeight);
		kinectImageView.setFitWidth(Kinect.screenWidth);
		kinectImageView.boundsInLocalProperty();
		kinectImageView.setPickOnBounds(true);
		kinectImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	try {
            		orderSelectedPixel();
            		getAllInfoAboutXYCartesianSelectedPoint(e); 
            		updateDisplayPanels();
            		if (isAutotrackingSelected()){
            			actCountControler.acPointSelection(e);
            		}
            	} catch (Exception ex){
            		System.out.println("Modo Test - Operaciones OnMouseClicked deshabilitadas");
            	}
            }	
        });
		kinectImageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	if (dynamicMousePointerSelection){
	            	try {
	            		getAllInfoAboutXYCartesianSelectedPoint(e); 
	            		updateDisplayPanels();
	            		if (isAutotrackingSelected()){
	            			System.out.println("Auto Tracking Deshabilitado en Modo Dinamic Clic");
	            		}
	            	} catch (Exception ex){
	            		System.out.println("Modo Test - Operaciones OnMouseClicked deshabilitadas");
	            	}
            	}
            }	
        });
	}
	
	public ActiveContoursControllerAdapter getActiveContoursController(){
		return actCountControler;
	}
	
	public void updateDisplayPanels() {
		pixelPanel.setXYZvalues(lastSelectedPixel,lastSelectedPixel.getColorString());
    	angleValuesPanel.setAnglesValues(lastSelectedPixel.getAnglesCalculator());
	}
	
	private void getAllInfoAboutXYCartesianSelectedPoint(MouseEvent e) {
		
		selectedXpoint=(int) (e.getX() -zeroXref);			// Convert X to cartesian axe
    	selectedYpoint=(int) (e.getY() * -1) + zeroYref;	// Convert Y to cartesian axe
    	selectedDepthPoint = imageCapture.getXYMatrizProfundidad((int)e.getX(),(int)e.getY());
    	selectedColorPoint = imageCapture.getXYMatrizRGBColorCadena((int)e.getX(),(int)e.getY());
    	boolean outOfVerticalBoundsPoint=imageCapture.isOutOfVerticalBoundsPoint((int)e.getX(),(int)e.getY());
    	lastSelectedPixel=new XYZpoint(selectedXpoint,selectedYpoint,selectedDepthPoint,selectedColorPoint,this);
    	if (outOfVerticalBoundsPoint){
    		updateSystemMessagesPanel("Punto fuera de foco.");
    	}
	}

	private AnchorPane createAnchorPane(){
		
		Pane pixelPane=pixelPanel.getPane();
		Pane settingsPane=settingsPanel.getPane();
		Pane verticalAnglePane=verticalAnglePanel.getPane();
		Pane horizontalAnglePane=horizontalAnglePanel.getPane();
		Pane angleValuesPane=angleValuesPanel.getPane();
		Pane kinectAnglePositionPane=kinectAnglePositionPanel.getPane();
		Pane systemScreenMessagesPane=systemScreenMessagesPanel.getPane();
		Pane delayPane=delayPanel.getPane();
		List<Node> principalPaneChildrens = new ArrayList<Node>();
		principalPaneChildrens.addAll(Arrays.asList(imageRosaView,imageEjesView,kinectImageView,imageRosaIconView,
				pixelPane,settingsPane,verticalAnglePane,horizontalAnglePane,angleValuesPane,
				kinectAnglePositionPane,systemScreenMessagesPane,delayPane));
		
		AnchorPane anchorpane = new AnchorPane();
		anchorpane.getChildren().addAll(principalPaneChildrens);
		AnchorPane.setTopAnchor(imageRosaIconView, 30.0);
		AnchorPane.setLeftAnchor(imageRosaIconView, 130.0);
		AnchorPane.setTopAnchor(imageEjesView, 80.0);
		AnchorPane.setLeftAnchor(imageEjesView, 20.0);
		AnchorPane.setTopAnchor(imageRosaView, 4.0);
		AnchorPane.setBottomAnchor(imageRosaView, 4.0);
		AnchorPane.setLeftAnchor(imageRosaView, 128.0);
		AnchorPane.setTopAnchor(kinectImageView, 272.0);
		AnchorPane.setBottomAnchor(kinectImageView, 272.0);
		AnchorPane.setLeftAnchor(kinectImageView, 320.0);
		AnchorPane.setBottomAnchor(pixelPane, 40.0);
		AnchorPane.setLeftAnchor(pixelPane, 20.0);
		AnchorPane.setBottomAnchor(kinectAnglePositionPane, 40.0);
		AnchorPane.setLeftAnchor(kinectAnglePositionPane, 150.0);
		AnchorPane.setTopAnchor(settingsPane, 162.0);
		AnchorPane.setRightAnchor(settingsPane, 20.0);
		AnchorPane.setTopAnchor(verticalAnglePane, 402.0);
		AnchorPane.setRightAnchor(verticalAnglePane, 20.0);
		AnchorPane.setTopAnchor(angleValuesPane, 250.0);
		AnchorPane.setLeftAnchor(angleValuesPane, 20.0);
		AnchorPane.setBottomAnchor(horizontalAnglePane, 40.0);
		AnchorPane.setRightAnchor(horizontalAnglePane, 20.0);
		AnchorPane.setTopAnchor(systemScreenMessagesPane, 30.0);
		AnchorPane.setRightAnchor(systemScreenMessagesPane, 20.0);
		AnchorPane.setBottomAnchor(delayPane, 133.0);
		AnchorPane.setRightAnchor(delayPane, 20.0);
			
		return anchorpane;
	}
	
	private void createImageRosaIconView() {
		Image imageRosaDeLosVientos = new Image(getClass().getResourceAsStream("../../resource/images/rosa_de_los_vientos.jpg"));
		imageRosaIconView = new ImageView(imageRosaDeLosVientos);
		//imageRosaIconView.setPreserveRatio(true);
		imageRosaIconView.setFitHeight(150);
		imageRosaIconView.setFitWidth(150);
	}

	private void createImageRosaView() {
		Image imageRosaDeLosVientos = new Image(getClass().getResourceAsStream("../../resource/images/rosa_de_los_vientos.jpg"));
		imageRosaView = new ImageView(imageRosaDeLosVientos);
		imageRosaView.setPreserveRatio(true);
		imageRosaView.setFitHeight(1024);
		imageRosaView.setFitWidth(1280);
	}
	
	private void createImageEjesView() {
		Image imageEjes = new Image(getClass().getResourceAsStream("../../resource/images/ejes.jpg"));
		imageEjesView = new ImageView(imageEjes);
		imageEjesView.setPreserveRatio(true);
		imageEjesView.setFitHeight(180);
		imageEjesView.setFitWidth(130);
	}
	
	public void updateSensorPositionPanel(){
		kinectAnglePositionPanel.setHVvalues(hwController.getRotationAngle(),hwController.getElevationAngle());
	}
	
	public void cleanUpdateSensorPositionPanel(){
		hwController.cleanCounters();
		kinectAnglePositionPanel.clearValues();
	}
	
	public void updateSystemMessagesPanel(String message){
		systemScreenMessagesPanel.setMessage(message);
	}
	
	public void cleanUpdateSystemMessagesPanel(){
		systemScreenMessagesPanel.clearMessage();
		
	}
	
	public void orderSelectedPixel() {
		previousSelectedPixel=lastSelectedPixel;
	}
	
	public void swapSelectedPixel() {
		XYZpoint swap=previousSelectedPixel;
		previousSelectedPixel=lastSelectedPixel;
		lastSelectedPixel=swap;
	}
	
	public HardwareController getHardwareController(){
		return this.hwController;
	}
}
