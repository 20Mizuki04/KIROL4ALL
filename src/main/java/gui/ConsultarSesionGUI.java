package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.BLFacade;
import domain.Actividad;
import domain.Sesion;

import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class ConsultarSesionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultarSesionGUI frame = new ConsultarSesionGUI();
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
	public ConsultarSesionGUI() {
		setTitle("Proyecto KIROL4ALL: ConsultarSesionGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Etiquetas
		JLabel lblInstrucciones = new JLabel("Seleccione una actividad para ver sus sesiones disponibles");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(149, 10, 365, 29);
		contentPane.add(lblInstrucciones);
		
		JLabel lblActividad = new JLabel("Actividad:");
		lblActividad.setBounds(149, 53, 80, 13);
		contentPane.add(lblActividad);
		
		
		//Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(69, 236, 435, 29);
		contentPane.add(lblError);
		
		
		
		
		
		//Scrollpane para la lista de sesiones
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 96, 559, 130);
		contentPane.add(scrollPane);
		
		//Lista de sesiones
		DefaultListModel<Sesion> listModel = new DefaultListModel<>();
		JList<Sesion> listSesiones = new JList<>(listModel);
		scrollPane.setViewportView(listSesiones);
		listSesiones.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				lblError.setText("Para poder reservar una sesión se necesita ser socio");
			}
			
		});
		
		
		
		
		
		
		logNeg = MainGUI.getBusinessLogic();

		
		//ComboBox de la lista de actividades
		JComboBox<Actividad> comboBoxActividades = new JComboBox<Actividad>();
		comboBoxActividades.setBounds(217, 49, 198, 21);
		List<Actividad> lista = logNeg.consultarActividades(); //Obtenemos una lista de las actividades
		for(Actividad a : lista) { //Añadimos cada actividad en el comboBox
			comboBoxActividades.addItem(a);
		}
		contentPane.add(comboBoxActividades);
		
		
		//Botón de buscar actividades
		JButton btnBuscarActividades = new JButton("Buscar");
		btnBuscarActividades.setBounds(429, 49, 85, 21);
		contentPane.add(btnBuscarActividades);
		btnBuscarActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				lblError.setText("");
				Actividad actividad =  (Actividad) comboBoxActividades.getSelectedItem(); //Actividad seleccionada
				//Si se ha seleccionado una actividad
				if(actividad != null) {
					List<Sesion> lista = logNeg.consultarSesiones(actividad); //Obtenemos la lista de sesiones de la actividad
					listModel.clear();
					//Si no hay sesiones para la actividad
					if(lista.isEmpty()) {
						lblError.setText("No hay sesiones disponibles para esta actividad");;
					}
					//Si hay sesiones para la actividad
					else {
						for(Sesion s : lista) { //Añadimos cada sesion en el Jlist
							listModel.addElement(s);
						}
					}
				}
				

			}
		});
		
		//Botón de volver a MainGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(514, 244, 85, 21);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI main = new MainGUI();
				main.setLocation(getLocation());
				main.setVisible(true);
				dispose();
			}
		});
		

		
		
		
		
		
		
		
		
		
		
		
	}
}
