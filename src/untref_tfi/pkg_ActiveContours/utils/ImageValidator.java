package untref_tfi.pkg_ActiveContours.utils;

import javafx.scene.image.Image;
import untref_tfi.pkg_ActiveContours.domain.ImagePosition;
import untref_tfi.pkg_ActiveContours.domain.TemporalColor;

import static untref_tfi.pkg_ActiveContours.domain.utils.ImageValuesTransformer.toInt;

public class ImageValidator {

	public static boolean existPosition(Image image, int row, int column) {
		boolean columnIsValid = column < toInt(image.getWidth()) && 0 <= column;
		boolean rowIsValid = row < toInt(image.getHeight()) && 0 <= row;
		return columnIsValid && rowIsValid;
	}

	public static boolean existPosition(TemporalColor[][] temporalColors, int row, int column) {
		boolean columnIsValid = column < toInt(temporalColors[0].length) && 0 <= column;
		boolean rowIsValid = row < toInt(temporalColors.length) && 0 <= row;
		return columnIsValid && rowIsValid;
	}

	public static boolean existPosition(Image image, ImagePosition imagePosition) {
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		return existPosition(image, row, column);
	}
}