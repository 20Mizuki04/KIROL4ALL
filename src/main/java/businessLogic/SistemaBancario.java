package businessLogic;

import dataAccess.DataAccess;
import domain.Factura;
import domain.Socio;
import domain.TarjetaCuenta;

public class SistemaBancario {
	
	private DataAccess dbManager;
	
	
	private Socio socio;
	private TarjetaCuenta numCuentaOTarjeta;
	private double precio;
	
	
	public SistemaBancario(DataAccess dbManager) {
		this.dbManager = dbManager;
	}
	
	
	public String procesarPago(Socio socio, Factura factura) {
		TarjetaCuenta numCuentaTarjeta = socio.getNumCuentaOTarjeta();
		double precio = factura.getPrecio();
		double saldoActual = numCuentaTarjeta.getSaldo();
		
		//Si el precio de la factura supera el saldo del socio
		if(saldoActual < precio) {
			System.out.println("Precio: " + precio);
			System.out.println("Saldo: " + saldoActual);

			return "Saldo insuficiente";
		}
		else {
			try {
				double nuevoSaldo = saldoActual - precio;
				socio.getNumCuentaOTarjeta().setSaldo(nuevoSaldo);
				dbManager.actualizarSaldoDB(socio, nuevoSaldo);
				dbManager.actualizarEstadoFacturaDB(factura);
				
				return "OK";
			}
			catch(Exception ex) {
				return "Error sistema bancario";
			}
			
		}
		
	}


	public Socio getSocio() {
		return socio;
	}


	public void setSocio(Socio socio) {
		this.socio = socio;
	}


	public TarjetaCuenta getNumCuentaOTarjeta() {
		return numCuentaOTarjeta;
	}


	public void setNumCuentaOTarjeta(TarjetaCuenta numCuentaOTarjeta) {
		this.numCuentaOTarjeta = numCuentaOTarjeta;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
