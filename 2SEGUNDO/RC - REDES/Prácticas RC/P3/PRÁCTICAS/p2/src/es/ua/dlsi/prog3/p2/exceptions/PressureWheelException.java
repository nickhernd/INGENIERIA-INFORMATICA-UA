package es.ua.dlsi.prog3.p2.exceptions;

public class PressureWheelException extends Exception {
	private double pressure;
	
	public PressureWheelException(double pressure) {
		this.pressure = pressure;
	}
	
	public String getMessage() {return "Pressure of " + pressure + " BAR"; }
}
