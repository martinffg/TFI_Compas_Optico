package untref_tfi.controller;
 

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private double objectColorDeltaValue=50.0;
	private int imageCounter=0;
	private ImagePosition centroideCurva;
	
	public ActiveContoursControllerAdapter(MainGraphicInterfaceController mgic) {
		firstPixelPaneController = new PixelSelectionController(FIRST_PIXEL);
		secondPixelPaneController = new PixelSelectionController(SECOND_PIXEL);
		imageView = mgic.getKinectImageView();
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
			System.out.println("Segundo Punto Elegido. \n Imagen nro: "+imageCounter);
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
			
			imageCounter++;
			
			System.out.println("Imagen nro: "+imageCounter+" Centroide en ("+centroideCurva.getColumn()+";"+centroideCurva.getRow()+")");
						
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
}
