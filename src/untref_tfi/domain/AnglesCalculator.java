package untref_tfi.domain;

import untref_tfi.controller.XYZpoint;

public class AnglesCalculator {
	
	private XYZpoint pointXYZ;
	private double theta = 0;   // 999 si no es posible calcular
	private double phi = 0;   // 999 si no es posible calcular
	private boolean isThetaCalculable=true;
	private boolean isPhiCalculable=true;
		
	public AnglesCalculator(XYZpoint xyzPoint){
		this.pointXYZ = xyzPoint;
		calculateTheta();
		calculatePhi();
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
	
	public boolean isPointOnTheXYorigin(){
		return (pointXYZ.getXvalue()==0)&&(pointXYZ.getYvalue()==0);
	}
	
	public double getTheta() {
		return theta;
	}

	public double getPhi() {
		return phi;
	}

	private void calculateTheta(){
		int x = pointXYZ.getXvalue();
	    int y = pointXYZ.getYvalue();
	    
	    if ((x!=0)&&(y!=0)) {
	    	theta= Math.atan((double)y/x);
	    } else {
	    	calculateThetaOnXYaxes(x,y);
	    }	
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
		double z = pointXYZ.getZvalue();
	    int x = pointXYZ.getXvalue();
	    
	    if ((z!=0)&&(x!=0)) {
	    	calculatePhiOutOfZXaxes(z,x);
	    } else {
	    	calculatePhiOnZXaxes(z);
	    }	
	}
	
	private void calculatePhiOutOfZXaxes(double z,int x){
		double phiValue = Math.atan(Math.abs((double)x/z));
		
		if (x>0) {
			phi = phiValue;
		} else {
			phi = -phiValue;
		}
	}
	
	private void calculatePhiOnZXaxes(double z){
		if (!isPointOnTheXYorigin()&&(z>0.0)){
			phi=0;                   // z_depth no puede ser 0, ya que 0 es el valor de no medicion
		} else {  // por estar en el origen
			this.phi=999;
			this.isPhiCalculable=false;
		}
	}
}
