package es.ua.dlsi.prog3.p2.model;
import java.util.ArrayList;

import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;
import es.ua.dlsi.prog3.p2.exceptions.TooManyWheelsException;
import es.ua.dlsi.prog3.p2.exceptions.WrongTyreTypeException;

public class Car {
	private final int MAX_WHEEL = 4;
	private ArrayList<Wheel> wheels;
	
	public Car() {this.wheels = new ArrayList<Wheel>();}
	
	public Car(Car c) {this.wheels = new ArrayList<Wheel>(c.wheels);}	
	
	public void addWheel(Wheel w) throws TooManyWheelsException, WrongTyreTypeException{
		
		if(wheels.size() == 0) {wheels.add(w);}
		
		if(wheels.size() > MAX_WHEEL)  {
			throw new TooManyWheelsException();
		} if (w.getTyreType() != wheels.get(0).getTyreType()) {
			throw new WrongTyreTypeException();
		}
		
		wheels.add(w);
	}
	
	public ArrayList<Wheel> getWheels() {return new ArrayList<Wheel>(this.wheels);}
	
	public void changeTyres(TyreType tt, double pressure) throws PressureWheelException {
		
		for(int i = 0; i < wheels.size(); i++) {
			
			if(tt != null) { 
				wheels.get(i).setTyreType(tt);
			} else {
				throw new IllegalArgumentException();
			}
			
			try {
				wheels.get(i).inflate(pressure);
			} catch (PressureWheelException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException();
			}
			
		}
	}
}
