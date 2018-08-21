package untref_tfi.controller;
 

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import untref_tfi.domain.XYZpoint;
import untref_tfi.pkg_ActiveContours.controllers.PixelSelectionController;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.ImageSetter;
import untref_tfi.pkg_ActiveContours.domain.ImagePosition;
import untref_tfi.pkg_ActiveContours.domain.activecontours.Contour;
import untref_tfi.pkg_ActiveContours.service.activecontours.ActiveContoursService;
import untref_tfi.pkg_ActiveContours.service.activecontours.ActiveContoursServiceImpl;
import static untref_tfi.pkg_ActiveContours.domain.utils.ImageValuesTransformer.toInt;
import java.awt.image.BufferedImage;

public class ActiveContoursControllerAdapter {

	private static final String FIRST_PIXEL = "first pixel   ";
	private static final String SECOND_PIXEL = "second pixel  ";
	private final PixelSelectionController firstPixelPaneController;
	private final PixelSelectionController secondPixelPaneController;
	private final ActiveContoursService activeContoursService;
	private ImageView imageView;
	private Image imageToShow;
	private final Contour[] contour;
	private double objectColorDeltaValue=30.0;
	private int imageCounter=0;
	private ImagePosition centroideCurva;
	XYZpoint centroidPoint;
	private MainGraphicInterfaceController mgic;
		
	public ActiveContoursControllerAdapter(MainGraphicInterfaceController mGraphIntCont) {
		firstPixelPaneController = new PixelSelectionController(FIRST_PIXEL);
		secondPixelPaneController = new PixelSelectionController(SECOND_PIXEL);
		mgic=mGraphIntCont;
		imageView = mGraphIntCont.getKinectImageView();
		imageCounter=0;
		contour = new Contour[1];
		activeContoursService = new ActiveContoursServiceImpl();
		
	}
	
	public void acPointSelection(MouseEvent event){
		imageToShow = imageView.getImage();
		int x = toInt(event.getX());
		int y = toInt(event.getY());

		if (!firstPixelPaneController.setedValues()) {
			firstPixelPaneController.setValues(x, y);
			System.out.println("Primer Punto Elegido");
		} else if (!secondPixelPaneController.setedValues()) {
			secondPixelPaneController.setValues(x, y);
			contour[0] = activeContoursService.initializeActiveContours(imageToShow,firstPixelPaneController.getPosition(),secondPixelPaneController.getPosition());
			System.out.println("Segundo Punto Elegido y Contorno inicializado");
		} else {
			firstPixelPaneController.clearValues();
			secondPixelPaneController.clearValues();
			imageCounter=0;
			contour[0] = null;
			System.out.println("Se limpio seleccion de puntos y contour, contador imagenes en 0");
		}

		if (contour[0] != null) {
			contour[0] = activeContoursService.adjustContours(contour[0], objectColorDeltaValue);
			ImageSetter.setWithImageSize(imageView, contour[0].getImageWithContour());
			imageCounter++;
			
			centroideCurva=activeContoursService.calculateCentroid(contour[0].getlIn());
			//System.out.println("Centroide Curva en ("+centroideCurva.getColumn()+";"+centroideCurva.getRow()+");");
			centroidPoint= imagePositionToXYZpointConverter(centroideCurva);
			mgic.setCentroidPoint(centroidPoint);
			
			if (centroidPoint!=null) {
				mgic.orderSelectedPixel();
				mgic.setLastSelectedPixel(centroidPoint);
				mgic.updateDisplayPanels();
				System.out.println("Imagen nro: "+imageCounter+" Centroide en ("+centroidPoint.getXvalue()
				+";"+centroidPoint.getYvalue()+";"+centroidPoint.getKinectDepth()+"); Color: "+centroidPoint.getColorString()
				+" Delta Color: "+objectColorDeltaValue);
			} else {
				System.out.println("CENTROIDE NO DETECTABLE EN EL INICIO.");
			}
		}
	}
	
	public boolean areBothPointsSetted(){
		return (firstPixelPaneController.setedValues()&&secondPixelPaneController.setedValues());
	}

	public BufferedImage reproduceImageWithContour(BufferedImage imagenKinect) {
		
		Image imagenFXinput=SwingFXUtils.toFXImage(imagenKinect, null);
		BufferedImage imagenBIoutput;
		
				
		if (contour[0] != null) {  
			contour[0] = activeContoursService.applyContourToNewImage(contour[0],imagenFXinput);
			contour[0] = activeContoursService.adjustContours(contour[0], objectColorDeltaValue);
			
			centroideCurva=activeContoursService.calculateCentroid(contour[0].getlIn());
			//System.out.println("Centroide Curva en ("+centroideCurva.getColumn()+";"+centroideCurva.getRow()+");");
			centroidPoint= imagePositionToXYZpointConverter(centroideCurva);
			mgic.setCentroidPoint(centroidPoint);
			
			imageCounter++;
			
			if (centroidPoint!=null) {
				mgic.orderSelectedPixel();
				mgic.setLastSelectedPixel(centroidPoint);
				mgic.updateDisplayPanels();
				System.out.println("Imagen nro: "+imageCounter+" Centroide en ("+centroidPoint.getXvalue()
				+";"+centroidPoint.getYvalue()+";"+centroidPoint.getKinectDepth()+"); Color: "+centroidPoint.getColorString()
				+" Delta Color: "+objectColorDeltaValue);
			} else {
				System.out.println("CENTROIDE VACIO, PERDIDA DE CONTORNO");
			}
						
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		if (contour[0] != null) {
			imagenBIoutput=SwingFXUtils.fromFXImage (contour[0].getImageWithContour(),null);
		} else {
			imagenBIoutput=SwingFXUtils.fromFXImage (imageView.getImage(),null);
		}
		
		return imagenBIoutput;
	}
	
	public XYZpoint imagePositionToXYZpointConverter(ImagePosition centroide){
		
		XYZpoint convertedPoint=null;
		
		if (centroide.isValidImagePosition()){
			int centroidXvalue=centroide.getColumn() - MainGraphicInterfaceController.zeroXref;
			int centroidYvalue=(centroide.getRow() * -1) + MainGraphicInterfaceController.zeroYref;
			Double centroidDepthValue = mgic.getImageCaptureController().getXYMatrizProfundidad(centroide.getColumn(),centroide.getRow());
			String centroidColorValue = mgic.getImageCaptureController().getXYMatrizRGBColorCadena(centroide.getColumn(),centroide.getRow());
			convertedPoint=new XYZpoint(centroidXvalue,centroidYvalue,centroidDepthValue,centroidColorValue,mgic);	
		} 
		
		return convertedPoint;
	}

	public double getObjectColorDeltaValue() {
		return objectColorDeltaValue;
	}

	public void setObjectColorDeltaValue(double objectColorDeltaValue) {
		this.objectColorDeltaValue = objectColorDeltaValue;
	}
}
