package businessLogic;
import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Sala;
import domain.Sesion;
import domain.Socio;
import domain.Actividad;
import domain.Encargado;
import domain.Factura;
import domain.Reserva;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    

	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    
    
    
    
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public boolean registrarSocio(String nombre, String correo, String contrasena, int cuentaOTarjeta) {
    	dbManager.open();
    	boolean exitoRegistro;//Boolean para saber si hemos realizado el registro o no
    	//Comprobamos si existe en la base de datos
    	boolean existeUsuario = dbManager.buscarUsuarioDB(correo);
    	//Si existe el usuario
    	if(existeUsuario) {
    		exitoRegistro = false;
    	}
    	//Si no existe el usuario, lo creamos y guardamos
    	else {
    		Socio socio = new Socio(nombre, correo, contrasena,  cuentaOTarjeta);
    		dbManager.guardarSocioDB(socio);
    		exitoRegistro = true;
    	}
    	dbManager.close();
    	return exitoRegistro;
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public boolean registrarEncargado(String nombre, String correo, String contrasena) {
    	dbManager.open();
    	boolean exitoRegistro;//Boolean para saber si hemos realizado el registro o no
    	//Comprobamos si existe en la base de datos
    	boolean existeUsuario = dbManager.buscarUsuarioDB(correo);
    	//Si existe el usuario
    	if(existeUsuario) {
    		exitoRegistro = false;
    	}
    	//Si no existe el usuario, lo creamos y guardamos
    	else {
    		Encargado encargado = new Encargado(nombre, correo, contrasena);
    		dbManager.guardarEncargadoDB(encargado);
    		exitoRegistro = true;
    	}
    	dbManager.close();
    	return exitoRegistro;
    	
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public List<Actividad> consultarActividades(){
    	dbManager.open();
    	List<Actividad> listaActividades = dbManager.consultarActividadesDB();
    	dbManager.close();
    	return listaActividades;
    }

    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public List<Sesion> consultarSesiones(Actividad actividad){
    	dbManager.open();
    	List<Sesion> listaSesiones = dbManager.consultarSesionesDB(actividad);
    	dbManager.close();
    	return listaSesiones;
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public Socio verificarSocio(String correo, String contrasena) {
    	dbManager.open();
    	Socio socio = dbManager.verificarSocioDB(correo, contrasena);
    	dbManager.close();
    	return socio;
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public Encargado verificarEncargado(String correo, String contrasena) {
    	dbManager.open();
    	Encargado encargado = dbManager.verificarEncargadoDB(correo, contrasena);
    	dbManager.close();
    	return encargado;
    }
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public boolean crearActividad(String nombre, int gradoExigencia, float precio) {
    	dbManager.open();
    	boolean exitoCrearActividad;
    	Actividad a = dbManager.buscarActividadDB(nombre);
    	//Si la actividad existe
    	if(a != null) {
    		exitoCrearActividad = false;
    	}
    	//Si la actividad no existe la creamos y guardamos en la base de datos
    	else {
    		Actividad actividad = new Actividad(nombre, gradoExigencia, precio);
    		dbManager.guardarActividadDB(actividad);
    		exitoCrearActividad = true;
    	}
    	dbManager.close();
    	return exitoCrearActividad;
    	
    }
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod
    public List<Sala> consultarSalas(){
    	dbManager.open();
    	List<Sala> listaSalas = dbManager.consultarSalasDB();
    	dbManager.close();
    	return listaSalas;
    }


    
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod 
    public boolean planearSesion(Actividad actividad, Sala sala, String fecha, int horaInicio, int minutoInicio, int horaFin, int minutoFin) {
    	dbManager.open();
    	boolean exitoCrearSesion;
    	boolean sesionCoincidente = dbManager.buscarSesionCoincidenteDB(actividad, sala, fecha, horaInicio, minutoInicio, horaFin, minutoFin);
    	//Si coincide la sesion a crear con una ya existente
    	if(sesionCoincidente) {
    		exitoCrearSesion = false;
    	}
    	//Si no coincide la sesion a crear con ninguna ya existente, se guarda la sesión
    	else {
    		Sesion sesion = new Sesion(actividad, sala, fecha, horaInicio, minutoInicio, horaFin, minutoFin);
    		dbManager.guardarSesionDB(sesion);
    		exitoCrearSesion = true;
    	}
    	return exitoCrearSesion;
    }
    
    
    /**
     * {@inheritDoc}
     */
	@WebMethod public String reservarSesion(Sesion sesion, Socio socio) {
		dbManager.open();
		int numReservasSocio = dbManager.consultarNumReservasSocioDB(socio);
		
		//Si el número de reservas del socio supera a su número de reservas máximas
		if(numReservasSocio >= socio.getReservasMax()) {
			dbManager.close();
			return "Supera maximo reservas socio";
		}
		//Si intenta reservar la misma sesión 2 veces
		boolean repetido = dbManager.reservaRepetidaDB(socio, sesion);
		if(repetido) {
			dbManager.close();
			return "Reserva sesion repetida";
		}
		else {
			//Si el aforo actual de la sesión está completa, se mete al socio en una lista de espera
			if(sesion.getForoActual() >= sesion.getSala().getAforoMax()) {
				Reserva r = new Reserva(socio, sesion, "EN ESPERA");
				dbManager.guardarReservaDB(r);
				dbManager.close();
				return "Lista reservas llena";
			}
			//Si va bien, se crea y se guarda la reserva
			else {
				Reserva reserva = new Reserva(socio, sesion, "CONFIRMADA");
				dbManager.aumentarNumForoActualSesionDB(sesion);	
				dbManager.guardarReservaDB(reserva);
				dbManager.close();
				String idReserva = reserva.getId().toString();
				return idReserva;
			}
		}
		
	}


	/**
     * {@inheritDoc}
     */
    @WebMethod 
	public List<Reserva> consultarReservasSocio(Socio socio){
		dbManager.open();
		List<Reserva> listaReservas = dbManager.consultarReservasDB(socio);
		dbManager.close();
		return listaReservas;
	}
	
	
    /**
     * {@inheritDoc}
     */
    @WebMethod 
	public boolean cancelarReserva(Reserva reserva) {
		dbManager.open();
		boolean exitoCancelacion = dbManager.eliminarReservaDB(reserva);
		dbManager.restarNumForoActualSesionDB(reserva.getSesion());
		dbManager.asignarReservaListaEsperaDB(reserva);
		dbManager.close();
		return exitoCancelacion;

	}
	
	
	//CALCULAMOS FECHAS: LUNES Y DOMINGO DE LA SEMANA ANTERIOR
	
	DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Formateador
	LocalDate hoy = LocalDate.now(); //Fecha de hoy
	
	int diasDesdeLunesEstaSemana = hoy.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue(); // Número de día de la semana del 1 al 7 menos el valor del lunes(1)
	LocalDate esteLunes = hoy.minusDays(diasDesdeLunesEstaSemana);
	
	LocalDate lunesSemanaPasada = esteLunes.minusDays(7);
	LocalDate domingoSemanaPasada = esteLunes.minusDays(1);
	
	String fechaLunes = lunesSemanaPasada.format(fecha);
	String fechaDomingo = domingoSemanaPasada.format(fecha);
	
	
	
	/**
     * {@inheritDoc}
     */
    @WebMethod 
	public List<Socio> consultarSociosMasCuatroReservasSemanaPasada() {
		dbManager.open();
		List<Socio> socios = dbManager.consultarSociosMasCuatroReservasSemanaPasadaDB(fechaLunes, fechaDomingo);
		dbManager.close();
		return socios;
	}
	
	
    /**
     * {@inheritDoc}
     */
    @WebMethod 
	public Factura crearFactura(Socio socio) {
				
		dbManager.open();
		
		//Obtenemos la lista de reservas a partir de la reserva 5 (La 5ta incluida)
		List<Reserva> listaReservas = dbManager.consultarReservasSocioSemanaPasadaDB(socio, fechaLunes, fechaDomingo);
		//Calculamos el precio de la factura
		double precio = 0;
		for(Reserva r : listaReservas) {
			precio = r.getSesion().getActividad().getPrecio() + precio;
			}
		Factura factura = new Factura();
		factura.setPrecio(precio);
		factura.setSocio(socio);
		factura.setListaReservas(listaReservas);
		dbManager.guardarFacturaDB(factura);
		
		
		dbManager.close();
		
		return factura;
	}
	
	
	
    /**
     * {@inheritDoc}
     */
    @WebMethod 
	public boolean enviarFactura(Encargado encargado, Socio socio, Factura factura) {
		dbManager.open();
		boolean exitoEnvio = dbManager.enviarFacturaDB(encargado, socio, factura);
		dbManager.close();
		return exitoEnvio;
	}
	
    /**
     * {@inheritDoc}
     */
    @WebMethod 
	public List<Factura> consultarFacturasSocio(Socio socio){
		dbManager.open();
		List<Factura> listaFacturas = dbManager.consultarFacturasSocioDB(socio);
		dbManager.close();
		return listaFacturas;
	}
	
    /**
     * {@inheritDoc}
     */
    @WebMethod 
	public String pagarFactura(Socio socio, Long codigoLong) {
		
		String exitoPago;
		
		dbManager.open();
		Factura factura = dbManager.verificarFacturaDB(socio, codigoLong);
		//Si el código dado no corresponde a una de las facturas del socio
		if(factura == null) {
			exitoPago = "Codigo mal";
		}
		//Si el código dado corresponde a una de las facturas del socio
		else {
			SistemaBancario sistemaBancario = new SistemaBancario(this.dbManager);
			exitoPago = sistemaBancario.procesarPago(socio, factura);
		}
		dbManager.close();
		return exitoPago;
	}



	
	
	
	
	
	
	
	
	
	
	


}

