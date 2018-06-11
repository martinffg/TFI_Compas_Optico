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
	private final int timeWaitThread=5; // tiempo de espera para lanzar cada iteracion de movimiento.
	
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
	
	private void moveStepper(int steps, String mensaje) {
		try {	
			for (int i=0;i<steps;i++) {
				try {
					arduino.sendData(mensaje);
					Thread.sleep(timeWaitThread);
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
}