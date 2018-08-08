package untref_tfi.pkg_ActiveContours.eventhandlers.activecontours;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.ImageSetter;
import untref_tfi.pkg_ActiveContours.factory.FileImageChooserFactory;
import untref_tfi.pkg_ActiveContours.repository.ImageRepositoryImpl;
import untref_tfi.pkg_ActiveContours.service.ImageIOServiceImpl;

import java.util.List;

public class OpenMultiplesImageEventHandler implements EventHandler<ActionEvent> {

	private final ImageView imageView;
	private final List<Image> videoImages;

	public OpenMultiplesImageEventHandler(ImageView imageView, List<Image> videoImages) {
		this.imageView = imageView;
		this.videoImages = videoImages;
	}

	@Override
	public void handle(ActionEvent event) {
		FileChooser fileChooser = new FileImageChooserFactory().create("open multiples images");
		videoImages.clear();
		List<Image> images = new ImageIOServiceImpl(new ImageRepositoryImpl()).openImages(fileChooser);
		videoImages.addAll(images);
		ImageSetter.setWithImageSize(imageView, images.get(0));
	}
}