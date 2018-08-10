package untref_tfi.pkg_ActiveContours.controllers;
 

import untref_tfi.pkg_ActiveContours.domain.ImagePosition;

public class PixelSelectionController {

	private int yValue=999;
	private int xValue=999;
	private String name;

	public PixelSelectionController(String pixelName) {
		this.name=pixelName;
	}
	
	public String getName(){
		return name;
	}
	
	public void setValues(int x, int y) {
		xValue=x;
		yValue=y;
	}

	public void clearValues() {
		yValue=999;
		xValue=999;
	}
	
	public boolean setedValues(){
		return (yValue!=999) && (xValue!=999);
	}

	public ImagePosition getPosition(){
		return new ImagePosition(yValue,xValue);
	}
}