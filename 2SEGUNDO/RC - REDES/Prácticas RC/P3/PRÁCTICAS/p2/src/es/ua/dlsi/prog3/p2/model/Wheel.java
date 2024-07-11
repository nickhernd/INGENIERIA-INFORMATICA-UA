package es.ua.dlsi.prog3.p2.model;
import es.ua.dlsi.prog3.p2.exceptions.NoTyreTypeException;
import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;

public class Wheel {
	private double pressure = 0;
	private TyreType tyreType;
	
	public Wheel() {this.pressure = 0;}
	
	public Wheel(TyreType tt) {this.tyreType = tt;}
	
	public Wheel(Wheel w) {this.pressure = w.pressure;}
	
	public void setTyreType(TyreType tt) {tyreType = tt;}
	
	public TyreType getTyreType() {return this.tyreType;}
	
	public void inflate(double inf) throws NoTyreTypeException, PressureWheelException {
		
		if (inf < 0 ) {
			throw new IllegalArgumentException();
		} else if (tyreType == null) {
			throw new NoTyreTypeException();
		} else if (tyreType.getMaxPressure() < inf || tyreType.getMinPressure() > inf) {
			throw new PressureWheelException(this.pressure);
		} else {
			this.pressure = inf;
		}
		
	}
}
