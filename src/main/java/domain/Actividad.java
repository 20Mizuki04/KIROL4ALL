package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Actividad implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@XmlID
	@Id
	private String nombre;
	private int gradoExigencia; //grado de exigencia de 1 a 5
	private double precio;
	
	public Actividad() {
		
	}
	
	public Actividad(String nombre, int gradoExigencia, double precio) {
		this.nombre = nombre;
		this.gradoExigencia = gradoExigencia;
		this.precio = precio;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getGradoExigencia() {
		return gradoExigencia;
	}

	public void setGradoExigencia(int gradoExigencia) {
		this.gradoExigencia = gradoExigencia;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
	 @Override
	    public String toString() {
	        return nombre;    // Hacemos toString para que muestre el nombre de la actividad
	    }

}
