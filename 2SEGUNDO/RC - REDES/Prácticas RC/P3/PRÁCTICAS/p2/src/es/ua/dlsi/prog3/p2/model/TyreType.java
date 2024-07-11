package es.ua.dlsi.prog3.p2.model;
import java.util.Objects;

public class TyreType {
	private String descripton;
	private double min_pressure;
	private double max_pressure;
	
	public TyreType(String description, double min_pressure, double max_pressure) {
		
		if (description == null || description == "" || min_pressure < 0 || max_pressure < 0 || min_pressure > max_pressure) {
			throw new IllegalArgumentException();
		} else {
			this.descripton = description;
			this.min_pressure = min_pressure;
			this.max_pressure = max_pressure;
		}
		
	}
		
	
	public TyreType(TyreType tt) {
		this.descripton = tt.descripton;
		this.max_pressure = tt.max_pressure;
		this.min_pressure = tt.min_pressure;
	}
	
	public String toString() {return "TyreType " + descripton + " ["  + min_pressure + "," +  max_pressure + "]";}


	public double getMinPressure() {return min_pressure;}
	
	public double getMaxPressure() {return max_pressure;}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TyreType other = (TyreType) obj;
		return Objects.equals(descripton, other.descripton)
				&& Double.doubleToLongBits(max_pressure) == Double.doubleToLongBits(other.max_pressure)
				&& Double.doubleToLongBits(min_pressure) == Double.doubleToLongBits(other.min_pressure);
	}
	
}