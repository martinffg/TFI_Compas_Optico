package untref_tfi.pkg_ActiveContours.controllers.nodeutils.settertype;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import untref_tfi.pkg_ActiveContours.controllers.nodeutils.ImageSetter;

public class SetterAdjustToView implements SetterType{

	@Override
	public void setImage(ImageView imageView, Image image){
		ImageSetter.set(imageView, image);
	}
}