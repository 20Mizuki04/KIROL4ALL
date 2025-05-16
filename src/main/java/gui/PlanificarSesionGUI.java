package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Actividad;
import domain.Encargado;
import domain.Sala;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;

import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;

public class PlanificarSesionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private static Encargado encargado;

	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlanificarSesionGUI frame = new PlanificarSesionGUI(encargado);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlanificarSesionGUI(Encargado encargado) {
		setTitle("Proyecto KIROL4ALL: PlanificarSesionGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 642, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Etiquetas
		JLabel lblInstrucciones = new JLabel("Para planificar una sesión introduzca los siguientes datos:");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(44, 24, 520, 13);
		contentPane.add(lblInstrucciones);

		JLabel lblSala = new JLabel("Sala:");
		lblSala.setBounds(44, 128, 91, 13);
		contentPane.add(lblSala);

		JLabel lblActividad = new JLabel("Actividad:");
		lblActividad.setBounds(44, 84, 91, 13);
		contentPane.add(lblActividad);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(318, 84, 91, 13);
		contentPane.add(lblFecha);

		JLabel lblHoraInicio = new JLabel("Hora de inicio:");
		lblHoraInicio.setBounds(44, 182, 90, 13);
		contentPane.add(lblHoraInicio);

		JLabel lblHoraFin = new JLabel("Hora de finalización:");
		lblHoraFin.setBounds(186, 182, 142, 13);
		contentPane.add(lblHoraFin);

		// Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(44, 277, 531, 36);
		contentPane.add(lblError);
		lblError.setForeground(Color.RED);

		logNeg = MainGUI.getBusinessLogic();

		// ComboBox de la lista de todas las actividades
		JComboBox<Actividad> comboBoxActividades = new JComboBox<Actividad>();
		comboBoxActividades.setBounds(133, 80, 156, 21);
		List<Actividad> listaAct = logNeg.consultarActividades();
		for (Actividad a : listaAct) {
			comboBoxActividades.addItem(a);
		}
		contentPane.add(comboBoxActividades);

		// ComboBox de la lista de todas las salas
		JComboBox<Sala> comboBoxSalas = new JComboBox<Sala>();
		comboBoxSalas.setBounds(133, 124, 156, 21);
		List<Sala> listaSalas = logNeg.consultarSalas();
		for (Sala s : listaSalas) {
			comboBoxSalas.addItem(s);
		}
		contentPane.add(comboBoxSalas);

		// Calendario
		JDateChooser calendario = new JDateChooser();
		calendario.setBounds(403, 80, 172, 25);
		calendario.setMinSelectableDate(new Date()); // Para no seleccionar días anteriores a hoy
		contentPane.add(calendario);

		// Hora de inicio
		// Horas (del 0 al 23)
		JComboBox<String> comboBoxHoraInicio = new JComboBox<>();
		for (int h = 0; h < 24; h++) {
			comboBoxHoraInicio.addItem(String.format("%02d", h)); // Le aplicamos a la hora el formato de 0 a la
																	// izquierda si es un solo dígito, y ocupando un
																	// ancho de 2 posiciones
		}
		comboBoxHoraInicio.setBounds(44, 205, 50, 25);
		contentPane.add(comboBoxHoraInicio);
		// Minutos (del 0 al 55 de 5 en 5)
		JComboBox<String> comboBoxMinutoInicio = new JComboBox<>();
		for (int m = 0; m < 60; m += 5) {
			comboBoxMinutoInicio.addItem(String.format("%02d", m));// Le aplicamos a los minutos el formato de 0 a la
																	// izquierda si es un solo dígito, y ocupando un
																	// ancho de 2 posiciones
		}
		comboBoxMinutoInicio.setBounds(100, 205, 50, 25);
		contentPane.add(comboBoxMinutoInicio);

		// Hora de fin
		// Horas (del 0 al 23)
		JComboBox<String> comboBoxHoraFin = new JComboBox<>();
		for (int h = 0; h < 24; h++) {
			comboBoxHoraFin.addItem(String.format("%02d", h)); // Le aplicamos a la hora el formato de 0 a la izquierda
																// si es un solo dígito, y ocupando un ancho de 2
																// posiciones
		}
		comboBoxHoraFin.setBounds(186, 205, 50, 25);
		contentPane.add(comboBoxHoraFin);
		// Minutos (del 0 al 55 de 5 en 5)
		JComboBox<String> comboBoxMinutoFin = new JComboBox<>();
		for (int m = 0; m < 60; m += 5) {
			comboBoxMinutoFin.addItem(String.format("%02d", m));// Le aplicamos a los minutos el formato de 0 a la
																// izquierda si es un solo dígito, y ocupando un ancho
																// de 2 posiciones
		}
		comboBoxMinutoFin.setBounds(242, 205, 50, 25);
		contentPane.add(comboBoxMinutoFin);

		// Botón de crear/planificar la sesión
		JButton btnCrearSesion = new JButton("Crear sesión");
		btnCrearSesion.setBounds(94, 323, 419, 36);
		contentPane.add(btnCrearSesion);
		btnCrearSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Actividad actividad = (Actividad) comboBoxActividades.getSelectedItem();
				Sala sala = (Sala) comboBoxSalas.getSelectedItem();
				Date fechaDate;
				String fechaString;
				int horaInicio = Integer.parseInt((String) comboBoxHoraInicio.getSelectedItem());
				int minutoInicio = Integer.parseInt((String) comboBoxMinutoInicio.getSelectedItem());
				int horaFin = Integer.parseInt((String) comboBoxHoraFin.getSelectedItem());
				int minutoFin = Integer.parseInt((String) comboBoxMinutoFin.getSelectedItem());
				
				lblError.setForeground(Color.RED);

				try {
					fechaDate = calendario.getDate();
					fechaString = new SimpleDateFormat("dd/MM/yyyy").format(fechaDate);
				} catch (NullPointerException ex) {
					lblError.setText("Debe seleccionar una fecha en el calendario");
					return;
				}

				boolean crearSesionExito = logNeg.planearSesion(actividad, sala, fechaString, horaInicio, minutoInicio,
						horaFin, minutoFin);

				//Si la hora y minuto de inicio es igual a la hora y minuto de fin
				if(horaInicio == horaFin && minutoInicio == minutoFin) {
					lblError.setText("La hora de inicio y finalización de la sesión deben de ser diferentes");
				}
				//Si la hora de fin es anterior a la hora inicio
				else if(horaFin < horaInicio || horaInicio == horaFin && minutoFin < minutoInicio) {
					lblError.setText("La sesión no puede finalizar antes de la hora de inicio");
				}
				// Si existe otra sesión que coincida con la nueva
				else if (!crearSesionExito) {
					lblError.setText("No se ha podido crear la nueva sesión, ya existe otra");
				}
				// Si no existe otra sesión que coincida con la nueva, se crea con éxito
				else {
					lblError.setForeground(Color.GREEN);
					lblError.setText("La sesión ha sido creada y guardada con éxito");
				}

			}
		});

		// Botón de volver a MenuEncargadoGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(539, 398, 79, 21);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuEncargadoGUI menuEncargado = new MenuEncargadoGUI(encargado);
				menuEncargado.setLocation(getLocation());
				menuEncargado.setVisible(true);
				dispose();
			}
		});

	}
}
