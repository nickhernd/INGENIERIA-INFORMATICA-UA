/**
 * @author Jaime Hernández
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p4.model;

/**
 * Esta clase representa un poligono sin especificar y hereda de Form2D
 */
public abstract class AbstractPolygon extends Form2D {
    public double angle; // Ángulo del polígono.

    /**
	 * Constructor que inicializa la posicion a no definida y el angulo de rotacion a 0
	 */
    protected AbstractPolygon() {
        super();
        this.angle = 0;
    }

    /**
	 * Constructor que inicializa los atributos con los datos pasados por parametro
	 * 
	 * @param c coordenada que representa la posicion del AbstractPolygon
	 * @param d numero que representa el angulo de rotacion
	 * @throws IllegalArgumentException si los parametros no son validos
	 */
    protected AbstractPolygon(Coordinate c, double angle) throws IllegalArgumentException {
        super(c);

        if (angle < 0 || angle > 360) {
            throw new IllegalArgumentException();
        }

        this.angle = angle;
    }

    /**
	 * Constructor que inicializa los atributos a los del AbstractPolygon pasado por parametro
	 * 
	 * @param p AbstractPolygon del que copiar
	 */
    protected AbstractPolygon(AbstractPolygon ap) {
        super(ap.getPosition());
        this.angle = ap.getAngle();
    }

    /**
	 * Getter para obtener el angulo de rotacion
	 * 
	 * @return un numero real que representa el angulo de rotacion
	 */
    public double getAngle() {
        return this.angle;
    }

    /**
	 * Rota el poligono la cantidad de grados pasados por parametros
	 * 
	 * @param rot numero real que representa la cantidad de grados a rotar
	 * @throws IllegalArgumentException si los parametros no son validos
	 */
    public void rotate(double rot) throws IllegalArgumentException {
        if (rot <= -360 || rot >= 360) {
            throw new IllegalArgumentException();
        }

        rot = rot + this.angle;

        if (rot < 0) {
            rot = 360 + rot;
        } else if (rot > 360) {
            rot = rot - 360;
        }
    }

    /**
	 * Metodo para comparar dos poligonos
	 * 
	 * @param obj poligono a comparar
	 * @return devuelve cierto si son iguales, falso en caso contrario
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractPolygon other = (AbstractPolygon) obj;
        if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle)) return false;
        return true;
    }
    
    /**
	 * Metodo para imprimir la informacion del poligono
	 * 
	 * @return un string con la informacion del poligono
	 */
	@Override
    public String toString() {
        return "(" + super.getPosition().getX() + "," + super.getPosition().getY() + ")" + ",angle=" + this.angle;
    }
}

