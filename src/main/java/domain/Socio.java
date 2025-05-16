package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Socio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlID
	@Id
	private String correo;
	private String nombre;
	private String contrasena;
	private int reservasMax;
	
//	@XmlIDREF
	@OneToOne(cascade = CascadeType.ALL)
	private TarjetaCuenta numCuentaOTarjeta;
	
//	@XmlIDREF
	@OneToMany
	private List<Reserva> reservas = new ArrayList<>();
	
//	@XmlIDREF
	@OneToMany
	private List<Factura> facturas = new ArrayList<>();

	public Socio() {
	}

	public Socio(String nombre, String correo, String contrasena, int numCuentaOTarjeta) {
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.numCuentaOTarjeta = new TarjetaCuenta(this, numCuentaOTarjeta);
		this.setReservasMax(10); //Cada socio tendrá un máximo de 10 reservas por semana
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

	public TarjetaCuenta getNumCuentaOTarjeta() {
		return numCuentaOTarjeta;
	}

	public void setNumCuentaOTarjeta(TarjetaCuenta numCuentaOTarjeta) {
		this.numCuentaOTarjeta = numCuentaOTarjeta;
	}

	public int getReservasMax() {
		return reservasMax;
	}

	public void setReservasMax(int reservasMax) {
		this.reservasMax = reservasMax;
	}
	
	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	
	@Override
    public String toString() {
        return "Socio: " + this.nombre + "    //    Correo: " + this.correo;    
    }
	
}
