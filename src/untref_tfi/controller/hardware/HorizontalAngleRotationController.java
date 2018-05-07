package untref_tfi.controller.hardware;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import jssc.SerialPortException;


public class HorizontalAngleRotationController {
	
	private PanamaHitek_Arduino arduino;

	public 	HorizontalAngleRotationController () {
		
		arduino = new PanamaHitek_Arduino();
		
		try {
			arduino.arduinoTX("COM3",9600);
			Thread.sleep(4000);  			// Tiempo de espera para que Arduino escuche comandos al abrir puerto COM3
	    	
	    } catch (ArduinoException | InterruptedException e){    	
	    	//e.printStackTrace();
	    	System.out.println("Error de conexion contra HW Arduino durante inicializacion");
	    } 
	    
	}
	
	public void movingForwardArduino(int steps){
		String mensaje = "1";
		if (stepsValidator(steps)) {		
			System.out.println("Pasos enviados a Arduino: "+steps);
			moveStepper(steps, mensaje); 
		}
	}

	public void movingBackwardArduino(int steps){
		String mensaje = "0";
		if (stepsValidator(steps)) {	
			System.out.println("Pasos enviados a Arduino: "+steps);
			System.out.println("Mensaje enviado a Arduino: "+mensaje);
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
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    } catch (ArduinoException | SerialPortException e){    	
	    	e.printStackTrace();
	    }
	}
	
	private boolean stepsValidator(int steps) {
		return (steps>0)&&(steps<=100);  // controlo que los pasos sean de 1 a 100 cubriendo abanico de 0° a 180° de giro
	}	
}