package untref_tfi.pkg_ActiveContours.service.activecontours;

import java.util.List;

import javafx.scene.image.Image;
import untref_tfi.pkg_ActiveContours.domain.activecontours.Contour;
import untref_tfi.pkg_ActiveContours.domain.ImagePosition;

public interface ActiveContoursService {
	Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2);

	Contour adjustContours(Contour contour, Double colorDelta);

	Contour applyContourToNewImage(Contour contour, Image image);

	Contour adjustContoursAutomatically(Contour contour, Double colorDelta, Double reductionTolerance, int expandSize);
	
	ImagePosition calculateCentroid(List<ImagePosition> curve);
}
