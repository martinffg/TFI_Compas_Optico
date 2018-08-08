package untref_tfi.pkg_ActiveContours.service.activecontours;

import untref_tfi.pkg_ActiveContours.domain.ImagePosition;
import untref_tfi.pkg_ActiveContours.domain.activecontours.ActiveContourCurves;

import java.util.List;

public interface ActiveContourCurveDetectorService {
	ActiveContourCurves calculateCurves(List<ImagePosition> lIn);
}