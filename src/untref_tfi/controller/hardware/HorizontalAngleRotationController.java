package untref_tfi.controller.hardware;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import jssc.SerialPortException;

public class HorizontalAngleRotationController {
	
	private final int defaultMotorFullStepCount=200;        // cantidad de pasos default del motor
	private final double defaultOneStepDegree = 1.8; // medida en grados default para 1 paso del motor
	private final int microSteppingDivisor= 16;       // divisor de 1 paso - microstepping
	private final int motorFullStepCount = defaultMotorFullStepCount * microSteppingDivisor;  // cantidad de pasos totales con microstepping
	private final double oneStepDegree = (double)defaultOneStepDegree / microSteppingDivisor; // cada paso es 1/microSteppingDivisor del defaultOneStepDegree grados del motor
	private final int motorHalfStepCount = motorFullStepCount / 2;  // cantidad de pasos para 180°
	public static final int minorTimeWait=5;
	public static final int majorTimeWait=50;
	public static final int minorIncreasePercent=0;
	public static final int majorIncreasePercent=30;
	private int timeWaitThread=minorTimeWait; // tiempo de espera para lanzar cada iteracion de movimiento.
	private int increasePercent=minorIncreasePercent; // porcentaje de ascenso gradual de la velocidad de giro horizontal (descenso de delay)
	
	private PanamaHitek_Arduino arduino;

	public 	HorizontalAngleRotationController () {
		
		arduino = new PanamaHitek_Arduino();
		
		try {
			arduino.arduinoTX("COM3",9600);
			Thread.sleep(4000);  			// Tiempo de espera para que Arduino escuche comandos al abrir puerto COM3
	    	
	    } catch (ArduinoException | InterruptedException e){    	
	    	System.out.println("Error de conexion contra HW Arduino durante inicializacion");
	    } 
	    
	}
		
	public int getTimeWaitThread() {
		return timeWaitThread;
	}

	public void setTimeWaitThread(int timeWaitThread) {
		if (timeWaitThread==1){
			timeWaitThread=this.timeWaitThread+1;
		}
		if (timeWaitThread==-1){
			timeWaitThread=this.timeWaitThread-1;
		}
		this.timeWaitThread = validatedTimeWait(timeWaitThread);
	}

	public int getIncreasePercent() {
		return increasePercent;
	}

	public void setIncreasePercent(int increasePercent) {
		if (increasePercent==1){
			increasePercent=this.increasePercent+1;
		}
		if (increasePercent==-1){
			increasePercent=this.increasePercent-1;
		}
		this.increasePercent = validatedIncreasePercent(increasePercent);
	}

	public void movingForwardArduino(int steps){
		String mensaje = "1";
		if (stepsValidator(steps)) {		
			moveStepper(steps, mensaje); 
		}
	}

	public void movingBackwardArduino(int steps){
		String mensaje = "0";
		if (stepsValidator(steps)) {	
			moveStepper(steps, mensaje); 
		}
	}
	
	public void closeControllerConnection(){
		try {
			arduino.flushSerialPort();
			arduino.killArduinoConnection();
		} catch (SerialPortException | ArduinoException e) {
			e.printStackTrace();
		}
		
	}
	
	private void moveStepper(int totalSteps, String mensaje) {
		int stepsByPercent= (totalSteps * this.getIncreasePercent()) / 100;
		try {	
			for (int i=0;i<totalSteps;i++) {
				try {
					arduino.sendData(mensaje);
					Thread.sleep(timeWaitThreadCurveCalculatorFunction(totalSteps,stepsByPercent,i));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    } catch (ArduinoException | SerialPortException e){    	
	    	e.printStackTrace();
	    }
	}
	
	private boolean stepsValidator(int steps) {
		return (steps>0)&&(steps<=motorHalfStepCount);  // controlo que los pasos sean de 1 a motorHalfStepCount+Tolerancia cubriendo abanico de 0° a 180° de giro
	}

	public int getMotorFullStepCount() {
		return motorFullStepCount;
	}

	public double getOneStepDegree() {
		return oneStepDegree;
	}

	public int getMotorHalfStepCount() {
		return motorHalfStepCount;
	}
	
	private int validatedTimeWait(int timeWaitTarget){
		int resultado=this.getTimeWaitThread();
		if ((timeWaitTarget>=minorTimeWait)&&(timeWaitTarget<=majorTimeWait)){
			resultado=timeWaitTarget;
		}
		return resultado;
	}
	
	private int validatedIncreasePercent(int increasePercent){
		int resultado=this.getIncreasePercent();
		if ((increasePercent>=minorIncreasePercent)&&(increasePercent<=majorIncreasePercent)){
			resultado=increasePercent;
		}
		return resultado;
	}
	
	private int timeWaitThreadCurveCalculatorFunction(int totalSteps,int stepsByPercent,int stepNumber){
		int resultado=this.getTimeWaitThread();
		if ((stepNumber>=0)&&(stepNumber<totalSteps)&&(stepsByPercent>0)){
			if (stepNumber<=stepsByPercent){
				resultado=getAccelerationDelay(totalSteps,stepsByPercent,stepNumber);
			}
			if (stepNumber>=(totalSteps-stepsByPercent)){
				resultado=getDesaccelerationDelay(totalSteps,stepsByPercent,stepNumber);
			}
		}
		
		return resultado;
	}
	
	private int getAccelerationDelay(int totalSteps,int stepsByPercent,int stepNumber){
		int resultado=this.getTimeWaitThread();
		double derivateDelay=(double)(this.getTimeWaitThread()-majorTimeWait)/stepsByPercent;
		resultado = (int)Math.round(derivateDelay * stepNumber) +majorTimeWait;
		return resultado;
	}
		
	private int getDesaccelerationDelay(int totalSteps,int stepsByPercent,int stepNumber){
		int resultado=this.getTimeWaitThread();
		double derivateDelay=(double)(majorTimeWait-this.getTimeWaitThread())/stepsByPercent;
		int originOrdered = majorTimeWait - (int)Math.round(derivateDelay * totalSteps);
		resultado = (int)Math.round(derivateDelay * stepNumber) + originOrdered;
		return resultado;
	}
}