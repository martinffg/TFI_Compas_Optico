package untref_tfi.pkg_ActiveContours.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import untref_tfi.pkg_ActiveContours.repository.ImageRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImageIOServiceImpl implements ImageIOService {
	private ImageRepository imageRepository;

	public ImageIOServiceImpl(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	@Override
	public Optional<Image> openImage(FileChooser fileChooser) {
		Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(null));
		return file.map(file1 -> imageRepository.findImage(file1));
	}

	@Override
	public List<Image> openImages(FileChooser fileChooser) {
		List<File> imagesPath = fileChooser.showOpenMultipleDialog(null);
		return imagesPath.stream().map(imagePath -> imageRepository.findImageWithFormat(imagePath)).collect(Collectors.toList());
	}
}