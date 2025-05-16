package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Sala {
	
	@XmlID
	@Id
	private String nombre;
	private int aforoMax;
	
	public Sala() {
		
	}
	
	public Sala(String nombre, int aforoMax) {
		this.nombre = nombre;
		this.aforoMax = aforoMax;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAforoMax() {
		return aforoMax;
	}

	public void setAforoMax(int aforoMax) {
		this.aforoMax = aforoMax;
	}
	
	@Override
    public String toString() {
        return nombre;    // Hacemos toString para que muestre el nombre de la sala
    }



}
