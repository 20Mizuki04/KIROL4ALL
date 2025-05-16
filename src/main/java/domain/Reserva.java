package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@XmlID
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Se genera automáticamente un identificador único
	private Long id; 
	private String estado;
	private String fechaCreacion; //Confirmada o en espera
	
	@XmlIDREF
	@ManyToOne
	private Socio socio;
//	@XmlIDREF
	@ManyToOne
	private Sesion sesion;
//	@XmlIDREF
	@ManyToOne
	private Factura factura;
	
	public Reserva() {
		
	}
	
	public Reserva(Socio socio, Sesion sesion, String estado) {
		this.socio = socio;
		this.sesion = sesion;
		this.estado = estado;
		DateTimeFormatter fechaYHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //Hacemos un formateador para hacer que sea un string con la fecha y hora de creación
	    this.fechaCreacion = LocalDateTime.now().format(fechaYHora); //Cogemos los datos de fecha y hora y le aplicamos el formateador
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}
	
	@Override
    public String toString() {
        return "Id: " + this.id + "    //    Estado:" + this.estado + "    //    Fecha de creación de reserva: " + this.fechaCreacion +  "    //   Datos de la sesión:  " + this.sesion + "    ";    
    }

	
	

}
