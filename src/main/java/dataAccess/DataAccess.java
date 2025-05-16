package dataAccess;

import java.io.File;

import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sun.org.apache.bcel.internal.generic.Select;

import businessLogic.EnviarCorreo;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Actividad;
import domain.Encargado;
import domain.Factura;
import domain.Reserva;
import domain.Sala;
import domain.Sesion;
import domain.Socio;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();

				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();

		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	

	public void open() {

		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	/**
	 * Este método busca un usuaio por su correo dado
	 * 
	 * @param correo del usuario que se busca
	 * @return true si existe, o false si no existe
	 */
	public boolean buscarUsuarioDB(String correo) {

		TypedQuery<Socio> q = db.createQuery("SELECT s FROM Socio s WHERE s.correo = :correo", Socio.class); // Hacemos
																												// la
																												// consulta
		q.setParameter("correo", correo); // Asignamos el valor de la variable correo al de la consulta "correo"
		if (q.getResultList().isEmpty()) { // No existe un socio con ese correo
			TypedQuery<Encargado> q2 = db.createQuery("SELECT e FROM Encargado e WHERE e.correo = :correo",
					Encargado.class); // Hacemos la consulta
			q2.setParameter("correo", correo); // Asignamos el valor de la variable correo al de la consulta "correo"
			if (q2.getResultList().isEmpty()) { // No existe un encargado con ese correo
				return false; // No existe ningún usuario con ese correo
			}
		}
		return true; // Existe un usuario con ese correo

	}

	/**
	 * Este método guarda un socio en la base de datos
	 * 
	 * @param socio
	 */
	public void guardarSocioDB(Socio socio) {
		db.getTransaction().begin();
		db.persist(socio);
		db.getTransaction().commit();
	}

	/**
	 * Este método guarda un encargado en la base de datos
	 * 
	 * @param encargado
	 */
	public void guardarEncargadoDB(Encargado encargado) {
		db.getTransaction().begin();
		db.persist(encargado);
		db.getTransaction().commit();
	}

	/**
	 * Este método consulta todas las actividades registradas en la base de datos.
	 * Ordena por nombre.
	 * 
	 * @return lista de actividades
	 */
	public List<Actividad> consultarActividadesDB() {
		TypedQuery<Actividad> q = db.createQuery("SELECT a FROM Actividad a ORDER BY a.nombre ASC", Actividad.class);
		return q.getResultList();

	}

	/**
	 * Este método consulta en la base de datos todas las sesiones asociadas a la
	 * actividad dada. Ordena primero por fecha y luego por hora de inicio.
	 * 
	 * @param actividad
	 * @return lista de sesiones
	 */
	public List<Sesion> consultarSesionesDB(Actividad actividad) {

		TypedQuery<Sesion> q = db.createQuery(
				"SELECT s FROM Sesion s WHERE s.actividad = :actividad ORDER BY s.fecha ASC, s.horaInicio ASC",
				Sesion.class);
		q.setParameter("actividad", actividad);
		return q.getResultList();

	}

	/**
	 * Este método verifica las credenciales de un socio en la base de datos.
	 * 
	 * @param correo
	 * @param contrasena
	 * @return socio si existe, null si no existe
	 */
	public Socio verificarSocioDB(String correo, String contrasena) {
		try {
			TypedQuery<Socio> q = db.createQuery(
					"SELECT s FROM Socio s WHERE s.correo = :correo AND s.contrasena = :contrasena", Socio.class);
			q.setParameter("correo", correo);
			q.setParameter("contrasena", contrasena);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Este método verifica las credenciales de un encargado en la base de datos.
	 * 
	 * @param correo
	 * @param contrasena
	 * @return encargado si existe, null si no existe
	 */
	public Encargado verificarEncargadoDB(String correo, String contrasena) {
		try {
			TypedQuery<Encargado> q = db.createQuery(
					"SELECT e FROM Encargado e WHERE e.correo = :correo AND e.contrasena = :contrasena",
					Encargado.class);
			q.setParameter("correo", correo);
			q.setParameter("contrasena", contrasena);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Este método busca una actividad en la base de datos pasado su nombre como
	 * parámetro.
	 * 
	 * @param nombre
	 * @return actividad si existe, null si no existe
	 */
	public Actividad buscarActividadDB(String nombre) {
		return db.find(Actividad.class, nombre);
	}

	/**
	 * Este método guarda una actividad en la base de datos
	 * 
	 * @param actividad
	 */
	public void guardarActividadDB(Actividad actividad) {
		db.getTransaction().begin();
		db.persist(actividad);
		db.getTransaction().commit();
	}

	/**
	 * Este método recupera las salas de la BD, ordenadas por nombre
	 * @return lista de salas
	 */
	public List<Sala> consultarSalasDB() {
		TypedQuery<Sala> q = db.createQuery("SELECT s FROM Sala s ORDER BY s.nombre ASC", Sala.class);
		return q.getResultList();
	}

	/**
	 * Este método comprueba si existe alguna sesión que coincida o se solape con la de los paramétros dados
	 * @param actividad
	 * @param sala
	 * @param fecha
	 * @param horaInicio
	 * @param minutoInicio
	 * @param horaFin
	 * @param minutoFin
	 * @return true si hay alguna sesion que se solape, false en caso contrario
	 */
	public boolean buscarSesionCoincidenteDB(Actividad actividad, Sala sala, String fecha, int horaInicio,
			int minutoInicio, int horaFin, int minutoFin) {
		boolean coincide;
		TypedQuery<Long> q = db
				.createQuery("SELECT COUNT(s) FROM Sesion s WHERE s.sala   = :sala AND s.fecha  = :fecha  "
						+ "AND ( (:hIni * 60 + :mIni) < (s.horaFin * 60 + s.minutoFin) "
						+ "AND (:hFin * 60 + :mFin) > (s.horaInicio * 60 + s.minutoInicio) )", Long.class);
		q.setParameter("sala", sala);
		q.setParameter("fecha", fecha);
		q.setParameter("hFin", horaFin);
		q.setParameter("mFin", minutoFin);
		q.setParameter("hIni", horaInicio);
		q.setParameter("mIni", minutoInicio);

		// Si existe una sesión que se solape con la nueva
		if (q.getSingleResult() != 0) {
			coincide = true;
		}
		// Si no existe una sesion que se solape con la nueva
		else {
			coincide = false;
		}
		return coincide;
	}

	/**
	 * Este método guarda la sesión dada en la base de datos
	 * @param sesion
	 */
	public void guardarSesionDB(Sesion sesion) {
		db.getTransaction().begin();
		db.persist(sesion);
		db.getTransaction().commit();
	}

	/**
	 * Este método consulta el número de reservas confirmadas realizadas por el socio dado
	 * @param socio
	 * @return número de reservas confirmadas
	 */
	public int consultarNumReservasSocioDB(Socio socio) {
		TypedQuery<Long> q = db.createQuery(
				"SELECT COUNT(r) FROM Reserva r WHERE r.socio = :socio AND r.estado = 'CONFIRMADA' ", Long.class);
		q.setParameter("socio", socio);
		return q.getSingleResult().intValue();
	}

	/**
	 * Este método comprueba si existe un socio dado tiene una reserva hecha con la sesón dada
	 * @param socio
	 * @param sesion
	 * @return true si existe una reserva con esa sesión de ese socio, y flase en caso contrario
	 */
	public boolean reservaRepetidaDB(Socio socio, Sesion sesion) {
		TypedQuery<Long> q = db.createQuery(
				"SELECT COUNT(r) FROM Reserva r WHERE r.socio = :socio AND r.sesion = :sesion", Long.class);
		q.setParameter("socio", socio);
		q.setParameter("sesion", sesion);
		if (q.getSingleResult().intValue() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Este método actualiza el foro actual aumentando en uno de la sesion dada.
	 * @param sesion
	 */
	public void aumentarNumForoActualSesionDB(Sesion sesion) {
		Sesion s = db.find(Sesion.class, sesion.getId());
		int foroActual = s.getForoActual() + 1;
		db.getTransaction().begin();
		s.setForoActual(foroActual);
		db.getTransaction().commit();

	}

	/**
	 * Este método guarda en la BD la reserva dada
	 * @param reserva
	 */
	public void guardarReservaDB(Reserva reserva) {
		db.getTransaction().begin();
		db.persist(reserva);
		db.getTransaction().commit();
	}

	/**
	 * Este método consulta la lista de reservas del socio dado
	 * @param socio
	 * @return lista de reservas del socio
	 */
	public List<Reserva> consultarReservasDB(Socio socio) {
		TypedQuery<Reserva> q = (TypedQuery<Reserva>) db.createQuery(
				"SELECT r FROM Reserva r WHERE r.socio = :socio ORDER BY r.fechaCreacion ASC", Reserva.class);
		q.setParameter("socio", socio);
		return q.getResultList();
	}

	/**
	 * Este método elimina de la BD la reserva dada
	 * @param reserva
	 * @return true si se elimina con éxito, false en caso contrario
	 */
	public boolean eliminarReservaDB(Reserva reserva) {
		Reserva r = db.find(Reserva.class, reserva.getId());
		if (r != null) {

			db.getTransaction().begin();
			db.remove(r);
			db.getTransaction().commit();
			return true;
		}
		return false;
	}

	/**
	 * Este método actualiza el foro actual disminuyendo en uno de la sesion dada.
	 * @param sesion
	 */
	public void restarNumForoActualSesionDB(Sesion sesion) {
		Sesion s = db.find(Sesion.class, sesion.getId());
		int foroActual = s.getForoActual() - 1;
		db.getTransaction().begin();
		s.setForoActual(foroActual);
		db.getTransaction().commit();

	}

	/**
	 * Este método asigna la primera reserva (la más antigua) y cambia su estado a confirmado y aumanta el foro actual
	 * @param reserva
	 */
	public void asignarReservaListaEsperaDB(Reserva reserva) {
		// Cogemos la sesión de la reserva dada
		Sesion sesion = reserva.getSesion();
		// Cogemos las reservas que tienen esa sesión asignada
		TypedQuery<Reserva> q = db.createQuery(
				"SELECT r FROM Reserva r WHERE r.sesion = :sesion ORDER BY r.fechaCreacion ASC", Reserva.class);
		q.setParameter("sesion", sesion);

		if (!q.getResultList().isEmpty()) {
			Reserva siguiente = q.getResultList().get(0);
			db.getTransaction().begin();
			siguiente.setEstado("CONFIRMADA");
			Sesion s = db.find(Sesion.class, sesion.getId());
			int foroActual = s.getForoActual() + 1;
			s.setForoActual(foroActual);
			db.getTransaction().commit();

		}

	}

	/**
	 * Este método obtiene los socios que han realizado más de 4 reservas confirmadas la semana pasada (de lunes a domingo)
	 * @param fechaLunes
	 * @param fechaDomingo
	 * @return lista de socios con más de 4 reservas en la semana pasada
	 */
	public List<Socio> consultarSociosMasCuatroReservasSemanaPasadaDB(String fechaLunes, String fechaDomingo) {
		TypedQuery<Socio> q = db.createQuery(
				"SELECT r.socio FROM Reserva r WHERE r.fechaCreacion BETWEEN :fechaLunes AND :fechaDomingo "
						+ "AND r.estado = 'CONFIRMADA' " + "GROUP BY r.socio HAVING COUNT(r) > 4",
				Socio.class);
		q.setParameter("fechaLunes", fechaLunes);
		q.setParameter("fechaDomingo", fechaDomingo);
		return q.getResultList();
	}


	/**
	 * Este método obtiene las reservas confirmadas por el socio dado dentro del rango de la semana anterior,
	 * ordenandolas por fecha y saltando las 4 primeras para cobrar de la cuarta en adelante
	 * @param socio
	 * @param fechaLunes
	 * @param fechaDomingo
	 * @return lista de reservas confirmadas semana anterior 
	 */
	public List<Reserva> consultarReservasSocioSemanaPasadaDB(Socio socio, String fechaLunes, String fechaDomingo) {
		TypedQuery<Reserva> q = db.createQuery("SELECT r FROM Reserva r WHERE r.socio = :socio AND "
				+ "r.fechaCreacion BETWEEN :fechaLunes AND :fechaDomingo AND "
				+ "r.estado = 'CONFIRMADA' ORDER BY r.fechaCreacion ASC", Reserva.class);
		q.setParameter("socio", socio);
		q.setParameter("fechaLunes", fechaLunes);
		q.setParameter("fechaDomingo", fechaDomingo);

		q.setFirstResult(4); // Saltamos las 4 primeras para coger a partir de la 5ta reserva

		return q.getResultList();

	}

	
	/**
	 * Este método guarda en la base de datos la factura dada
	 */
	public void guardarFacturaDB(Factura factura) {
		db.getTransaction().begin();
		db.persist(factura);
		db.getTransaction().commit();
	}

	/**
	 * Este método asocia la factura al socio.
	 * En caso de que el correo sea real y acabe en @ikasle.ehu.eus debe de llegar un correo a esa cuenta,
	 * en caso contrario se realiza una simulación.
	 * @param encargado
	 * @param socio
	 * @param factura
	 * @return true si se envia con exito, y false en caso contrario
	 */
	public boolean enviarFacturaDB(Encargado encargado, Socio socio, Factura factura) {
		db.getTransaction().begin();
		EnviarCorreo correo = new EnviarCorreo(encargado, socio, factura); // Creamos enviamos el correo
		// Si en la vida real no hace conexión
		if (correo.enviarCorreo().equalsIgnoreCase("ERROR")) {
			System.out.println(
					"Se ha intentado enviar el correo, pero no se ha podido establecer conexión o el correo no es real");
		} else {
			System.out.println("Se ha establecido conexión y enviado el correo");
		}

		Factura f = db.find(Factura.class, factura.getCodigo());
		f.setSocio(socio);
		socio.getFacturas().add(f);
		db.getTransaction().commit();
		return true;
	}
	
	
	/**
	 * Este método obtiene las facturas pendientes del socio dado
	 * @param socio
	 * @return lista de facturas pendientes
	 */
	public List<Factura> consultarFacturasSocioDB(Socio socio){
		TypedQuery<Factura> q = db.createQuery("SELECT f FROM Factura f WHERE f.socio = :socio AND f.pagada = FALSE", Factura.class);
		q.setParameter("socio", socio);
		return q.getResultList();
	}
	
	
	/**
	 * Este método obtiene la factura correspondiente al código dado y que no ha sido pagada
	 * @param socio
	 * @param codigoLong
	 * @return factura si el codigo es correcto, null en caso contrario
	 */
	public Factura verificarFacturaDB(Socio socio, Long codigoLong) {
		 try {
			 TypedQuery<Factura> q = db.createQuery("SELECT f FROM Factura f WHERE f.codigo = :codigoLong AND f.socio = :socio AND f.pagada = FALSE", Factura.class);
		        q.setParameter("codigoLong", codigoLong);
		        q.setParameter("socio", socio);
		        return q.getSingleResult();
		    } catch (NoResultException ex) {
		        return null;
		    }
		
	}
	
	/**
	 * Este método actualiza el saldo de la tarjeta del socio dado en la BD
	 * @param socio
	 * @param nuevoSaldo
	 */
	public void actualizarSaldoDB(Socio socio, double nuevoSaldo) {
		Socio s = db.find(Socio.class, socio.getCorreo());
		db.getTransaction().begin();
		s.getNumCuentaOTarjeta().setSaldo(nuevoSaldo);
		db.getTransaction().commit();
	}
	
	/**
	 * Este método actualiza el estado de la factura dada maracndola como pagada en la BD
	 * @param factura
	 */
	public void actualizarEstadoFacturaDB(Factura factura) {
		Factura f = db.find(Factura.class, factura.getCodigo());
		db.getTransaction().begin();
		f.setPagada(true);
		db.getTransaction().commit();
	}
	
	
	
	
	
	
	
	
	


	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}
			
			
			
			// Crear socios
			Socio socio1 = new Socio("Aitor", "socio1@gmail.com", "contra1", 12345678); // nombre, correo, contraseña,
																						// cuentaOTarjeta
			Socio socio2 = new Socio("Ane", "socio2@gmail.com", "contra2", 246813579);
			Socio socio3 = new Socio("Maria", "socio3@gmail.com", "contra3", 987654321);
			Socio socio4 = new Socio("Iker", "socio4@gmail.com", "contra4", 11223344);
			Socio socio5 = new Socio("Oier", "socio5@gmail.com", "contra5", 55667788);
			Socio socio6 = new Socio("June", "socio6@gmail.com", "contra6", 99887766);

			Socio yo = new Socio("Ilargi", "imatilde001@ikasle.ehu.eus", "a", 11111111);
			db.persist(yo);

			// Crear actividades
			Actividad actividad1 = new Actividad("Pilates", 3, 10.0); // nombre, gradoExigencia, precio
			Actividad actividad2 = new Actividad("Zumba", 2, 5.0);
			Actividad actividad3 = new Actividad("Natacion", 4, 15.0);
			Actividad actividad4 = new Actividad("Yoga", 1, 8.0);
			Actividad actividad5 = new Actividad("CrossFit", 5, 20.0);
			Actividad actividad6 = new Actividad("Boxeo", 4, 12.0);

			Actividad actividad7 = new Actividad("Spinning", 3, 12.5); // esta actividad no tiene sesiones

			// Crear salas
			Sala sala1 = new Sala("Sala1", 1);// nombre, aforoMaximo
			Sala sala2 = new Sala("Sala2", 15);
			Sala sala3 = new Sala("Sala3", 5);
			Sala sala4 = new Sala("Sala4", 20);
			Sala sala5 = new Sala("Sala5", 12);
			Sala sala6 = new Sala("Sala6", 25);

			// Crear sesiones

			Sesion sesion1 = new Sesion(actividad1, sala1, "01/05/2025", 9, 0, 11, 0);
			Sesion sesion2 = new Sesion(actividad2, sala2, "01/05/2025", 9, 0, 11, 0);
			Sesion sesion3 = new Sesion(actividad3, sala3, "01/05/2025", 9, 0, 11, 0);
			Sesion sesion4 = new Sesion(actividad4, sala4, "02/05/2025", 12, 0, 13, 30);
			Sesion sesion5 = new Sesion(actividad5, sala5, "02/05/2025", 17, 0, 18, 0);
			Sesion sesion6 = new Sesion(actividad6, sala6, "03/05/2025", 19, 0, 20, 30);
			Sesion sesion7 = new Sesion(actividad1, sala4, "03/05/2025", 8, 0, 9, 0);
			Sesion sesion8 = new Sesion(actividad2, sala5, "04/05/2025", 10, 0, 11, 0);
			Sesion sesion9 = new Sesion(actividad3, sala6, "04/05/2025", 18, 30, 19, 30);
			Sesion sesion10 = new Sesion(actividad4, sala1, "05/05/2025", 8, 30, 9, 30);
			Sesion sesion11 = new Sesion(actividad5, sala2, "05/05/2025", 10, 0, 11, 0);
			Sesion sesion12 = new Sesion(actividad6, sala3, "05/05/2025", 12, 0, 13, 0);
			Sesion sesion13 = new Sesion(actividad1, sala4, "06/05/2025", 9, 0, 10, 0);
			Sesion sesion14 = new Sesion(actividad2, sala5, "06/05/2025", 11, 0, 12, 0);
			Sesion sesion15 = new Sesion(actividad3, sala6, "06/05/2025", 17, 30, 18, 30);
			Sesion sesion16 = new Sesion(actividad4, sala2, "07/05/2025", 8, 0, 9, 0);
			Sesion sesion17 = new Sesion(actividad5, sala3, "07/05/2025", 10, 30, 11, 30);
			Sesion sesion18 = new Sesion(actividad6, sala1, "07/05/2025", 14, 0, 15, 0);
			Sesion sesion19 = new Sesion(actividad1, sala5, "08/05/2025", 9, 15, 10, 15);
			Sesion sesion20 = new Sesion(actividad2, sala6, "08/05/2025", 11, 45, 12, 45);

			db.persist(socio1);
			db.persist(socio2);
			db.persist(socio3);
			db.persist(socio4);
			db.persist(socio5);
			db.persist(socio6);

			db.persist(actividad1);
			db.persist(actividad2);
			db.persist(actividad3);
			db.persist(actividad4);
			db.persist(actividad5);
			db.persist(actividad6);
			db.persist(actividad7);

			db.persist(sala1);
			db.persist(sala2);
			db.persist(sala3);
			db.persist(sala4);
			db.persist(sala5);
			db.persist(sala6);

			db.persist(sesion1);
			db.persist(sesion2);
			db.persist(sesion3);
			db.persist(sesion4);
			db.persist(sesion5);
			db.persist(sesion6);
			db.persist(sesion7);
			db.persist(sesion8);
			db.persist(sesion9);
			db.persist(sesion10);
			db.persist(sesion11);
			db.persist(sesion12);
			db.persist(sesion13);
			db.persist(sesion14);
			db.persist(sesion15);
			db.persist(sesion16);
			db.persist(sesion17);
			db.persist(sesion18);
			db.persist(sesion19);
			db.persist(sesion20);

			// ── Socio 1: 4 reservas confirmadas la semana pasada ──
			Reserva r1a = new Reserva(socio1, sesion1, "CONFIRMADA");
			r1a.setFechaCreacion("05/05/2025");
			db.persist(r1a);

			Reserva r1b = new Reserva(socio1, sesion2, "CONFIRMADA");
			r1b.setFechaCreacion("06/05/2025");
			db.persist(r1b);

			Reserva r1c = new Reserva(socio1, sesion3, "CONFIRMADA");
			r1c.setFechaCreacion("07/05/2025");
			db.persist(r1c);

			Reserva r1d = new Reserva(socio1, sesion4, "CONFIRMADA");
			r1d.setFechaCreacion("08/05/2025");
			db.persist(r1d);

			// ── Socio 2: 5 reservas la semana pasada (4 confirmadas + 1 en espera) ──
			Reserva r2a = new Reserva(socio2, sesion5, "CONFIRMADA");
			r2a.setFechaCreacion("05/05/2025");
			db.persist(r2a);

			Reserva r2b = new Reserva(socio2, sesion6, "CONFIRMADA");
			r2b.setFechaCreacion("06/05/2025");
			db.persist(r2b);

			Reserva r2c = new Reserva(socio2, sesion7, "CONFIRMADA");
			r2c.setFechaCreacion("07/05/2025");
			db.persist(r2c);

			Reserva r2d = new Reserva(socio2, sesion8, "CONFIRMADA");
			r2d.setFechaCreacion("08/05/2025");
			db.persist(r2d);

			Reserva r2e = new Reserva(socio2, sesion9, "EN ESPERA");
			r2e.setFechaCreacion("09/05/2025");
			db.persist(r2e);

			// ── Socio 3: 5 reservas confirmadas la semana pasada ──
			Reserva r3a = new Reserva(socio3, sesion10, "CONFIRMADA");
			r3a.setFechaCreacion("05/05/2025");
			db.persist(r3a);

			Reserva r3b = new Reserva(socio3, sesion11, "CONFIRMADA");
			r3b.setFechaCreacion("06/05/2025");
			db.persist(r3b);

			Reserva r3c = new Reserva(socio3, sesion12, "CONFIRMADA");
			r3c.setFechaCreacion("07/05/2025");
			db.persist(r3c);

			Reserva r3d = new Reserva(socio3, sesion13, "CONFIRMADA");
			r3d.setFechaCreacion("08/05/2025");
			db.persist(r3d);

			Reserva r3e = new Reserva(socio3, sesion14, "CONFIRMADA");
			r3e.setFechaCreacion("09/05/2025");
			db.persist(r3e);
			
			Reserva r3f = new Reserva(socio3, sesion15, "CONFIRMADA");
			r3f.setFechaCreacion("10/05/2025");
			db.persist(r3f);
			
			Reserva r3g = new Reserva(socio3, sesion16, "CONFIRMADA");
			r3g.setFechaCreacion("11/05/2025");
			db.persist(r3g);


			// ── Socio yo : 5 reservas confirmadas la semana pasada ──
			Reserva r = new Reserva(yo, sesion10, "CONFIRMADA");
			r.setFechaCreacion("05/05/2025");
			db.persist(r);

			Reserva r1 = new Reserva(yo, sesion11, "CONFIRMADA");
			r1.setFechaCreacion("06/05/2025");
			db.persist(r1);

			Reserva r2 = new Reserva(yo, sesion12, "CONFIRMADA");
			r2.setFechaCreacion("07/05/2025");
			db.persist(r2);

			Reserva r3 = new Reserva(yo, sesion13, "CONFIRMADA");
			r3.setFechaCreacion("08/05/2025");
			db.persist(r3);

			Reserva r4 = new Reserva(yo, sesion14, "CONFIRMADA");
			r4.setFechaCreacion("09/05/2025");
			db.persist(r4);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
