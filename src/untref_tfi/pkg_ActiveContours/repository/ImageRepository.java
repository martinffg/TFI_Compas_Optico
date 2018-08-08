package untref_tfi.pkg_ActiveContours.repository;

import javafx.scene.image.Image;
import java.io.File;

public interface ImageRepository {
	Image findImage(File file);

	Image findImageWithFormat(File file);

	void storeImage(Image image, File file);
}
