package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Sesion implements Serializable {
	
	private static final long serialVersionUID = 1L;

//	@XmlID
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Se genera automáticamente un identificador único
	private Long id; 
	
	private Actividad actividad;
	private Sala sala;
	private String fecha;
	private int horaInicio;
	private int minutoInicio;
	private int horaFin;
	private int minutoFin;
	private int foroActual;
	
//	@XmlIDREF
	@OneToMany
	private List<Reserva> reservas = new ArrayList<>();

	public Sesion() {
		
	}
	
	public Sesion(Actividad actividad, Sala sala, String fecha, int horaInicio, int minutoInicio, int horaFin, int minutoFin) {
		this.actividad = actividad;
		this.sala = sala;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.minutoInicio = minutoInicio;
		this.horaFin = horaFin;
		this.minutoFin = minutoFin;
		this.foroActual = 0;

	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(int minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public int getMinutoFin() {
		return minutoFin;
	}

	public void setMinutoFin(int minutoFin) {
		this.minutoFin = minutoFin;
	}

	public int getForoActual() {
		return foroActual;
	}

	public void setForoActual(int foroActual) {
		this.foroActual = foroActual;
	}
	
	
	@Override
    public String toString() {
		String horaInicioTxt = String.format("%02d", horaInicio);
		String minutoInicioTxt = String.format("%02d", minutoInicio);
		String horaFinTxt = String.format("%02d", horaFin);
		String minutoFinTxt = String.format("%02d", minutoFin);

		
		
        return "Fecha: " + fecha + "    //    Hora de inicio:" + horaInicioTxt +":"+ minutoInicioTxt + "    //    Hora de finalización: " + horaFinTxt + ":" + minutoFinTxt + "    //    Sala: " + sala.getNombre();    
    }

}
