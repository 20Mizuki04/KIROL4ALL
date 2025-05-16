package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import domain.Actividad;
import javax.swing.JList;
import domain.Sesion;
import domain.Socio;

import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ReservarSesionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private static Socio socio;

	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservarSesionGUI frame = new ReservarSesionGUI(socio);
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
	public ReservarSesionGUI(Socio socio) {

		this.socio = socio;

		setTitle("Proyecto KIROL4ALL: ReservarSesionGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		// Etiquetas
		JLabel lblActividad = new JLabel("Actividad:");
		lblActividad.setBounds(198, 51, 103, 13);
		contentPane.add(lblActividad);

		JLabel lblInstrucciones = new JLabel("Seleccione una actividad para ver sus sesiones disponibles, y seleccione una sesión para poder reservarla");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(32, 10, 692, 21);
		contentPane.add(lblInstrucciones);

		
		
		// Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setBounds(32, 346, 597, 40);
		contentPane.add(lblError);
		lblError.setForeground(Color.RED);
		
		

		// Scrollpane para la lista de sesiones
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 93, 692, 167);
		contentPane.add(scrollPane);

		// Lista de sesiones
		DefaultListModel<Sesion> listModel = new DefaultListModel<>();
		JList<Sesion> listSesiones = new JList<>(listModel);
		scrollPane.setViewportView(listSesiones);
		

		
		
		logNeg = MainGUI.getBusinessLogic();

		// ComboBox de la lista de actividades
		JComboBox<Actividad> comboBoxActividades = new JComboBox<Actividad>();
		comboBoxActividades.setBounds(266, 47, 222, 21);
		List<Actividad> lista = logNeg.consultarActividades(); // Obtenemos una lista de las actividades
		for (Actividad a : lista) { // Añadimos cada actividad en el comboBox
			comboBoxActividades.addItem(a);
		}
		contentPane.add(comboBoxActividades);

		
		
		// Botón de reservar sesión
		JButton btnReservarSesion = new JButton("Reservar sesión");
		btnReservarSesion.setBounds(32, 285, 692, 40);
		contentPane.add(btnReservarSesion);
		btnReservarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblError.setForeground(Color.RED);
				
				Sesion sesion = listSesiones.getSelectedValue();
				//Si no selecciona una sesión
				if(sesion == null) {
					lblError.setText("Debe de seleccionar una sesión para poder hacer la reserva");
				}				
				//Si selecciona sesión y le da a reservar
				else {
					String exitoReserva = logNeg.reservarSesion(sesion, socio);
					//Si el número de reservas del socio supera a su número de reservas máximas
					if(exitoReserva.equalsIgnoreCase("Supera maximo reservas socio")) {
						lblError.setText("Ha alcanzado el límite de reservas semanales, no puede reservar más sesiones");
					}
					//Si intenta reservar la misma sesión 2 veces
					else if(exitoReserva.equalsIgnoreCase("Reserva sesion repetida")) {
						lblError.setText("Ya tiene esta sesión reservada");
					}
					//Si el aforo actual de la sesión está completa, se mete al socio en una lista de espera
					else if(exitoReserva.equalsIgnoreCase("Lista reservas llena")) {
						lblError.setText("Actualmente esta sesión no dispone de plazas libres, ha entrado en una lista de espera");
					}
					else {
						lblError.setForeground(Color.GREEN);
						lblError.setText("La reserva se ha realizado con éxito, el id de su reserva es: " + exitoReserva);
					}
				}
			}
		});

		
		
		// Botón de buscar actividades
		JButton btnBuscarActividades = new JButton("Buscar");
		btnBuscarActividades.setBounds(498, 47, 85, 21);
		contentPane.add(btnBuscarActividades);
		btnBuscarActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				Actividad actividad = (Actividad) comboBoxActividades.getSelectedItem(); // Actividad seleccionada
				// Si se ha seleccionado una actividad
				if (actividad != null) {
					List<Sesion> lista = logNeg.consultarSesiones(actividad); // Obtenemos la lista de sesiones de la
																				// actividad
					listModel.clear();
					// Si no hay sesiones para la actividad
					if (lista.isEmpty()) {
						lblError.setText("No hay sesiones disponibles para esta actividad");
					}
					// Si hay sesiones para la actividad
					else {
						for (Sesion s : lista) { // Añadimos cada sesion en el Jlist
							listModel.addElement(s);
						}
					}
				}

			}
		});

		
		
		// Botón de volver a MenuSocioGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(639, 365, 85, 21);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuSocioGUI main = new MenuSocioGUI(socio);
				main.setLocation(getLocation());
				main.setVisible(true);
				dispose();
			}
		});
	}

}
