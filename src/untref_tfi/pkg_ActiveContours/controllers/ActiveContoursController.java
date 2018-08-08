package untref_tfi.pkg_ActiveContours.controllers;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.ImageSetter;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.settertype.FullSetter;
import untref_tfi.pkg_ActiveContours.domain.activecontours.Contour;
import untref_tfi.pkg_ActiveContours.eventhandlers.activecontours.OpenMultiplesImageEventHandler;
import untref_tfi.pkg_ActiveContours.factory.FileImageChooserFactory;
import untref_tfi.pkg_ActiveContours.interfacebuilders.ImageViewBuilder;
import untref_tfi.pkg_ActiveContours.repository.ImageRepository;
import untref_tfi.pkg_ActiveContours.repository.ImageRepositoryImpl;
import untref_tfi.pkg_ActiveContours.service.ImageIOService;
import untref_tfi.pkg_ActiveContours.service.ImageIOServiceImpl;
import untref_tfi.pkg_ActiveContours.service.activecontours.ActiveContoursService;
import untref_tfi.pkg_ActiveContours.service.activecontours.ActiveContoursServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static untref_tfi.pkg_ActiveContours.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursController {

	private static final String FIRST_PIXEL = "first pixel   ";
	private static final String SECOND_PIXEL = "second pixel  ";
	private final OpenMenuController openMenuController;
	private final PixelPaneController firstPixelPaneController;
	private final PixelPaneController secondPixelPaneController;
	private final ActiveContoursService activeContoursService;
	private ImageIOService imageIOService;
	private ImageRepository imageRepository;
	private FileImageChooserFactory fileImageChooserFactory;
	private Image imageToShow;

	public ActiveContoursController() {
		imageRepository = new ImageRepositoryImpl();
		imageIOService = new ImageIOServiceImpl(imageRepository);
		fileImageChooserFactory = new FileImageChooserFactory();
		openMenuController = new OpenMenuController(fileImageChooserFactory, imageIOService);		

		firstPixelPaneController = new PixelPaneController(FIRST_PIXEL);
		secondPixelPaneController = new PixelPaneController(SECOND_PIXEL);
		activeContoursService = new ActiveContoursServiceImpl();
		
		generateActiveContoursWindows();
	}

	private void generateActiveContoursWindows() {
		Stage stage = new Stage();
		stage.setTitle("active contours for one image");
		VBox pane = new VBox();
		pane.setMinWidth(700);
		pane.setMinHeight(800);

		ImageView imageView = new ImageViewBuilder("resource/images/default.jpg").withPreserveRatio(true).withAutosize().withVisible(true).withImageSize().build();
		imageToShow = imageView.getImage();
		final Contour[] contour = new Contour[1];

		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int x = toInt(event.getX());
				int y = toInt(event.getY());

				if (!firstPixelPaneController.setedValues()) {
					firstPixelPaneController.setValues(x, y);
				} else if (!secondPixelPaneController.setedValues()) {
					secondPixelPaneController.setValues(x, y);
					contour[0] = activeContoursService.initializeActiveContours(imageView.getImage(), firstPixelPaneController.getPosition(),
							secondPixelPaneController.getPosition());
					ImageSetter.setWithImageSize(imageView, contour[0].getImageWithContour());
				} else {
					firstPixelPaneController.clearValues();
					secondPixelPaneController.clearValues();
					contour[0] = null;
					ImageSetter.setWithImageSize(imageView, imageToShow);
				}
			}
		});

		Menu openImage = new Menu("open");
		MenuItem openOneImage = openMenuController.createOpenMenuItemWithSpecificEvent(imageView, new FullSetter(), o -> {
			contour[0] = null;
			firstPixelPaneController.clearValues();
			secondPixelPaneController.clearValues();
			imageToShow = imageView.getImage();
		});
		openOneImage.setText("open one image");
		MenuItem openMultipleImages = new MenuItem("open multiples images");
		List<Image> videoImages = new ArrayList<>();
		openMultipleImages.setOnAction(new OpenMultiplesImageEventHandler(imageView, videoImages));
		openImage.getItems().addAll(openOneImage, openMultipleImages);
		MenuBar menuBar = new MenuBar(openImage);

		Label objectColorDelta = new Label("object color delta");
		TextField objectColorDeltaValue = new TextField();
		objectColorDeltaValue.setMaxWidth(60);
		HBox cordinates = new HBox();

		VBox firstPixelPane = firstPixelPaneController.getPane();
		VBox secondPixelPane = secondPixelPaneController.getPane();
		Button applyContours = new Button(" apply contours");
		applyContours.setOnAction(event -> {
			if (contour[0] != null) {
				contour[0] = activeContoursService.adjustContours(contour[0], Double.valueOf(objectColorDeltaValue.getText()));
				ImageSetter.setWithImageSize(imageView, contour[0].getImageWithContour());
			}
		});

		Label timeByFrame = new Label("time by frame(millis)");
		TextField timeByFrameValue = new TextField();
		timeByFrameValue.setMaxWidth(40);

		Label contourReduceTolerance = new Label("reduction tolerance");
		TextField contourReduceToleranceValue = new TextField();
		contourReduceToleranceValue.setMaxWidth(40);

		Label expandSize = new Label("expand size");
		TextField expandSizeValue = new TextField();
		contourReduceToleranceValue.setMaxWidth(40);

		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				reproduceVideoWithContour(contour, videoImages, imageView, objectColorDeltaValue, timeByFrameValue, contourReduceToleranceValue,
						expandSizeValue);
			}
		};

		Timer timer = new Timer();
		timer.schedule(timerTask, 3000, 10000);

		cordinates.getChildren()
				.addAll(firstPixelPane, secondPixelPane, timeByFrame, timeByFrameValue, contourReduceTolerance, contourReduceToleranceValue,
						expandSize, expandSizeValue);
		pane.getChildren().addAll(menuBar, imageView, cordinates, objectColorDelta, objectColorDeltaValue, applyContours);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	private void reproduceVideoWithContour(Contour[] contour, List<Image> videoImages, ImageView imageView, TextField objectColorDeltaValue,
			TextField timeByFrameValue, TextField contourReduceToleranceValue, TextField expandSizeValue) {
		if (contour[0] != null && hasSetted(objectColorDeltaValue.getText()) && hasSetted(contourReduceToleranceValue.getText()) && hasSetted(
				expandSizeValue.getText())) {
			final double[] accumulateTime = { 0 };
			videoImages.forEach(new Consumer<Image>() {
				@Override
				public void accept(Image image) {
					System.out.println("executing");
					double time = System.currentTimeMillis();
					contour[0] = activeContoursService.applyContourToNewImage(contour[0], image);
					contour[0] = activeContoursService.adjustContoursAutomatically(contour[0], Double.valueOf(objectColorDeltaValue.getText()),
							Double.valueOf(contourReduceToleranceValue.getText()), Integer.valueOf(expandSizeValue.getText()));
					ImageSetter.setWithImageSize(imageView, contour[0].getImageWithContour());
					accumulateTime[0] += (System.currentTimeMillis() - time);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			timeByFrameValue.setText(String.valueOf(accumulateTime[0] / videoImages.size()));
		}
	}

	private boolean hasSetted(String value) {
		try {
			Double.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}