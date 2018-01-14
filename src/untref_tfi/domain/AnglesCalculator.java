package untref_tfi.domain;

import untref_tfi.controller.XYZpoint;

public class AnglesCalculator {
	
	private XYZpoint pointXYZ;
	private double theta = 0;   // 999 si no es posible calcular
	private double phi = 0;   // 999 si no es posible calcular
	private double gamma = 0;   // 999 si no es posible calcular
	private boolean isThetaCalculable=true;
	private boolean isPhiCalculable=true;
	private boolean isGammaCalculable=true;
		
	public AnglesCalculator(XYZpoint xyzPoint){
		this.pointXYZ = xyzPoint;
		calculateTheta();
		calculatePhi();
		calculateGamma();
	}

	public XYZpoint getPointXYZ() {
		return pointXYZ;
	}

	public boolean isThetaCalculable(){
		return isThetaCalculable;
	}
	
	public boolean isPhiCalculable(){
		return isPhiCalculable;
	}
	
	public boolean isGammaCalculable(){
		return isGammaCalculable;
	}
	
	public boolean isPointOnTheXYorigin(){
		return (pointXYZ.getXvalue()==0)&&(pointXYZ.getYvalue()==0);
	}
	
	public double getTheta() {
		return theta;
	}

	public double getPhi() {
		return phi;
	}
	
	public double getGamma() {
		return gamma;
	}

	private void calculateTheta(){
		int x = pointXYZ.getXvalue();
	    int y = pointXYZ.getYvalue();
	    
	    if ((x!=0)&&(y!=0)) {
	    	theta= Math.toDegrees(Math.atan((double)y/x))+delta(x,y);
	    } else {
	    	calculateThetaOnXYaxes(x,y);
	    }	
	}
	
	private int delta(int x,int y){
		int delta=0;  // si x>0 e y>0  (condicion ideal)
		
		if (x<0) {
			delta=180;
		} else {
			if (y<0) {
				delta=360;
			}
		}
		
		return delta;
	}
	
	private void calculateThetaOnXYaxes(int x,int y){
		
		if (!isPointOnTheXYorigin()){
			calculateThetaOnXorYaxes(x,y);
		} else {  // por estar en el origen
			this.theta=999;
			this.isThetaCalculable=false;
		}
	}
	
	private void calculateThetaOnXorYaxes(int x,int y){
		if (x==0){
			calculateThetaIfXisZeroYdifZero(y);
		} 
		
		if (y==0){
			calculateThetaIfYisZeroXdifZero(x);
		} 
	}
	
	private void calculateThetaIfXisZeroYdifZero(int y){
		if (y<0){
			theta=270;
		} else {
			theta=90;
		}
	}
	
	private void calculateThetaIfYisZeroXdifZero(int x){
		if (x>0) { 
			theta=0;
		} else {
			theta=180;
		}
	}
	
	private void calculatePhi(){
		double z = pointXYZ.getZlength();
		double x = pointXYZ.getXlength();
	    
	    if (z>0.0){
	    	this.phi=Math.toDegrees(Math.atan((double)x/z));
	    } else {
	    	this.phi=999;
			this.isPhiCalculable=false;
	    }	
	}
	
	private void calculateGamma(){
		double z = pointXYZ.getZlength();
		double y = pointXYZ.getYlength();
	    
	    if (z>0.0){
	    	this.gamma=Math.toDegrees(Math.atan((double)y/z));
	    } else {
	    	this.gamma=999;
			this.isGammaCalculable=false;
	    }	
	}
}
