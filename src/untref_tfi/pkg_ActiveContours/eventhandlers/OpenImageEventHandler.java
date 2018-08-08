package untref_tfi.pkg_ActiveContours.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.settertype.SetterType;
import untref_tfi.pkg_ActiveContours.service.ImageIOService;

import java.util.Optional;

public class OpenImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;
	private final SetterType setterType;

	public OpenImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService,
			SetterType setterType) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
		this.setterType = setterType;
	}

	public void handle(ActionEvent event) {
		Optional<Image> image = imageIOService.openImage(fileChooser);
		image.ifPresent(image1 -> setterType.setImage(imageView, image1));
	}
}