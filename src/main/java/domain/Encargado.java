package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Encargado implements Serializable {
	
	@XmlID
	@Id
	private String correo;
	private String nombre;
	private String contrasena;
	
	public Encargado() {
		
	}
	
	public Encargado(String nombre, String correo, String contrasena) {
		this.correo = correo;
		this.nombre = nombre;
		this.contrasena = contrasena;
	}
	
	
	//Getters y setters
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
