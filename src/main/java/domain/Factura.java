package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Factura {
	
//	@XmlID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //Se genera automáticamente un identificador único
	private Long codigo;
	private String fecha;
	private double precio;
	private boolean pagada;
	
	@XmlIDREF
	@ManyToOne
	private Socio socio;
//	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER)
	private List<Reserva> listaReservas = new ArrayList<>();


	
	
	public Factura() {
		DateTimeFormatter fechaYHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //Hacemos un formateador para hacer que sea un string con la fecha y hora de creación
	    this.fecha = LocalDateTime.now().format(fechaYHora); //Cogemos los datos de fecha y hora y le aplicamos el formateador
	    this.pagada = false;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isPagada() {
		return pagada;
	}

	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}
	

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}
	
	public String precioActividades(List<Reserva> reservas) {
		String precioActividades = "";
		for(Reserva r : reservas) {
			Actividad actividad = r.getSesion().getActividad();
			double precio = r.getSesion().getActividad().getPrecio();
			precioActividades = precioActividades + "\nActividad: " + actividad + ", Precio: " + precio;
		}
		return precioActividades;
	}
	
	
	
	@Override
	public String toString() {
		return "Código de la factura: " + this.codigo + "\nFecha de envío de la factura: " + this.fecha + 
				"\nNúmero de reservas adicionales: " + this.listaReservas.size() +	
				"\nReservas: " + this.listaReservas + this.precioActividades(listaReservas) + "\nTotal a pagar: " + this.precio;
	}
	

	

}
