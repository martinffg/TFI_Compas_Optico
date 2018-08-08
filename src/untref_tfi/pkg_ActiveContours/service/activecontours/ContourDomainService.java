package untref_tfi.pkg_ActiveContours.service.activecontours;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import untref_tfi.pkg_ActiveContours.domain.ImagePosition;
import untref_tfi.pkg_ActiveContours.domain.activecontours.Contour;


public interface ContourDomainService {
	Contour createContour(ImagePosition imagePosition, ImagePosition imagePosition2, Image image);

	Contour createContourWithColorAverage(ImagePosition imagePosition, ImagePosition imagePosition2, Image image, Color objectColorAverage);
}
