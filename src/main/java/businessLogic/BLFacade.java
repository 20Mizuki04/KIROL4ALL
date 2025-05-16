package businessLogic;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;


import domain.Sala;
import domain.Sesion;
import domain.Socio;
import domain.Actividad;
import domain.Encargado;
import domain.Factura;
import domain.Reserva;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	
	
	
	
	
	
	/**
	 * Este método registra un nuevo socio en la base de datos.
	 * Se comprueba que no exista ya ese socio en la base de datos, y si no existe se guarda.
	 * @param nombre
	 * @param correo
	 * @param contrasena
	 * @param cuentaOTarjeta
	 * @return true si se ha registrado con éxito, y false en caso contrario
	 */
	@WebMethod public boolean registrarSocio(String nombre, String correo, String contrasena, int cuentaOTarjeta);
	
	
	
	/**
	 * Este método registra un nuevo encargado en la base de datos.
	 * Se comprueba que no exista ya ese encargado en la base de datos, y si no existe se guarda.
	 * @param nombre
	 * @param correo
	 * @param contrasena
	 * @return true si se ha registrado con éxito, y false en caso contrario
	 */
	@WebMethod public boolean registrarEncargado(String nombre, String correo, String contrasena);
	
	
	/**
	 * Este método consulta las actividades que se encuentran en la base de datos.
	 * @return lista de actividades
	 */
	@WebMethod public List<Actividad> consultarActividades();
	
	
	/**
	 * Este método consulta las sesiones de la actividad dada que se encuentran en la base de datos.
	 * @param actividad
	 * @return lista de sesiones
	 */
	@WebMethod public List<Sesion> consultarSesiones(Actividad actividad);
	
	
	/**
	 * Este método busca un socio correspondiente al correo y contraseña dados en la base de datos.
	 * @param correo
	 * @param contrasena
	 * @return socio
	 */
	@WebMethod public Socio verificarSocio(String correo, String contrasena);
	
	/**
	 * Este método busca un encargado correspondiente al correo y contraseña dados en la base de datos.
	 * @param correo
	 * @param contrasena
	 * @return encargado
	 */
	@WebMethod public Encargado verificarEncargado(String correo, String contrasena);
	
	
	/**
	 * Este método consulta las actividades existentes en la base de datos.
	 * Si no hay ninguna actividad con el mismo nombre que la pasada por parámetro, 
	 * se guarda la información de la nueva actividad en la base de datos.
	 * @param nombre
	 * @return true si la actividad se ha creado y guardado con éxito y false en caso contrario (ya existe la actividad)
	 */
	@WebMethod public boolean crearActividad(String nombre, int gradoExigencia, float precio);
	
	
	/**
	 * Este método consulta las salas que se encuentran en la base de datos.
	 * @return lista de salas
	 */
	@WebMethod public List<Sala> consultarSalas();
	
	
	/**
	 * Este método consulta las sesiones existentes en la base de datos.
	 * Si la nueva sesión que se quiere crear no coincide con ninguna otra en el mismo momento y lugar,
	 * se crea y se guarada en la base de datos.
	 * @param actividad
	 * @param sala
	 * @param fecha
	 * @param horaInicio
	 * @param minutoInicio
	 * @param horaFin
	 * @param minutoFin
	 * @return true si la sesion no coincide con otra y se crea con éxito, false en caso contrario
	 */
	@WebMethod public boolean planearSesion(Actividad actividad, Sala sala, String fecha, int horaInicio, int minutoInicio, int horaFin, int minutoFin);
	
	
	/**
	 * Este método reserva una sesión dada a un socio dado.
	 * Se verifica que el número de reservas del socio no supere a su número de reservas máximas, que el socio no reserve la misma sesión 2 veces,
	 * y que el aforo actual de la sesión no esté completa (en caso contrario de este último, se mete al socio en una lista de espera)
	 * @param sesion
	 * @return String que dependiendo cual sea el problema será un texto diferente, si la reserva sale bien el texto será OK
	 */
	@WebMethod public String reservarSesion(Sesion sesion, Socio socio);

	

	/**
	 * Este método consulta en la base de datos 
	 * @param socio
	 * @return lista de reservas del socio
	 */
	@WebMethod public List<Reserva> consultarReservasSocio(Socio socio);
	
	
	/**
	 * Este método busca en la base de datos la reserva dada y la elimina.
	 * Además actualiza la lista de reservas.
	 * @param reserva
	 * @return true si la reserva dada se elimina, false en caso contrario
	 */
	@WebMethod public boolean cancelarReserva(Reserva reserva);
	
	
	/**
	 * Este método consulta en la base de datos los socios que hicieron más de 4 reservas la semana anterior (de lunes a domingo)
	 * @return lista de socios con más de 4 reservas
	 */
	@WebMethod public List<Socio> consultarSociosMasCuatroReservasSemanaPasada();
	
	
	/**
	 * Este método crea una factura al socio dado.
	 * En la factura solo se cobrará las reservas realizadas la semana anterior a partir de la quinta (incluida)
	 * @param socio
	 * @return factura
	 */
	@WebMethod public Factura crearFactura(Socio socio);
	
	
	/**
	 * Este método asocia la factura al socio.
	 * En caso de que el correo sea real y acabe en @ikasle.ehu.eus debe de llegar un correo a esa cuenta,
	 * en caso contrario se realiza una simulación.
	 * @param encargado
	 * @param socio
	 * @param factura
	 * @return true si se envía con exito, false en caso contrario
	 */
	@WebMethod public boolean enviarFactura(Encargado encargado, Socio socio, Factura factura);
	
	
	/**
	 * Este método consulta las facturas pendientes del socio dado
	 * @param socio
	 * @return lista de facturas pendientes
	 */
	@WebMethod public List<Factura> consultarFacturasSocio(Socio socio);
	
	
	/**
	 * Este método verifica que el socio pueda pagar la factura y realiza el proceso
	 * @param socio
	 * @param codigoLong
	 * @return "OK" si se realiza el pago con éxito, o un mensaje dependiendo del error.
	 */
	@WebMethod public String pagarFactura(Socio socio, Long codigoLong);
	

}
