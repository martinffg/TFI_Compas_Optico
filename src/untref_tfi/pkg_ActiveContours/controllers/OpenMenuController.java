package untref_tfi.pkg_ActiveContours.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.settertype.SetterType;
import untref_tfi.pkg_ActiveContours.eventhandlers.OpenImageEventHandler;
import untref_tfi.pkg_ActiveContours.factory.FileImageChooserFactory;
import untref_tfi.pkg_ActiveContours.service.ImageIOService;

import java.util.function.Consumer;

public class OpenMenuController {

	private FileImageChooserFactory fileImageChooserFactory;
	private ImageIOService imageIOService;

	public OpenMenuController(FileImageChooserFactory fileImageChooserFactory, ImageIOService imageIOService) {
		this.fileImageChooserFactory = fileImageChooserFactory;
		this.imageIOService = imageIOService;
	}

	public MenuItem createOpenMenuItem(ImageView imageView, SetterType setterType) {
		MenuItem fileMenuItem = new MenuItem("open...");
		final FileChooser fileChooser = fileImageChooserFactory.create("open image");
		setOpenEvent(imageView, fileMenuItem, fileChooser, setterType);
		return fileMenuItem;
	}

	public MenuItem createOpenMenuItemWithSpecificEvent(ImageView imageView, SetterType setterType, Consumer<Object> consumer){
		MenuItem fileMenuItem = new MenuItem("open...");
		final FileChooser fileChooser = fileImageChooserFactory.create("open image");
		setOpenEvent(imageView, fileMenuItem, fileChooser, setterType, consumer);
		return fileMenuItem;
	}

	private void setOpenEvent(ImageView imageView, MenuItem fileMenuItem, FileChooser fileChooser, SetterType setterType,Consumer<Object> consumer) {
		fileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new OpenImageEventHandler(fileChooser, imageView, imageIOService, setterType).handle(event);
				consumer.accept(null);
			}
		});

	}

	private void setOpenEvent(final ImageView imageView, MenuItem fileMenuItem, final FileChooser fileChooser, SetterType setterType) {
		fileMenuItem.setOnAction(new OpenImageEventHandler(fileChooser, imageView, imageIOService, setterType));
	}
}