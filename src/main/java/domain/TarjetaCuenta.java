package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class TarjetaCuenta {
	
//	@XmlID
	@Id
	private int numero;
	private double saldo;

	@XmlIDREF
	@OneToOne
	private Socio socio;
	
	
	public TarjetaCuenta() {
		
	}
	

	public TarjetaCuenta(Socio socio, int numero) {
		this.socio = socio;
		this.numero = numero;
		this.saldo = 100; //Cada socio tendrá inicialmente en la cuenta 100€
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	
}
