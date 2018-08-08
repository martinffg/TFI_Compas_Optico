package untref_tfi.pkg_ActiveContours.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.util.List;
import java.util.Optional;

public interface ImageIOService {
	
	Optional<Image> openImage(FileChooser fileChooser);

	List<Image> openImages(FileChooser fileChooser);
}
