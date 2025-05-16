package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Encargado;
import javax.swing.JButton;

public class MenuEncargadoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel jLabelSelectOption;

	private static Encargado encargado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuEncargadoGUI frame = new MenuEncargadoGUI(encargado);
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
	public MenuEncargadoGUI(Encargado encargado) {

		this.encargado = encargado;

		setTitle("Proyecto KIROL4ALL: MenuEncargadoGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Etiqueta seleccionar opción
		jLabelSelectOption = new JLabel("Bienvenido/a " + encargado.getNombre() + " elija una opción");
		jLabelSelectOption.setBounds(24, 10, 426, 48);
		contentPane.add(jLabelSelectOption);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);

		
		//Botón para AÑADIR NUEVA ACTIVIDAD
		JButton btnAnadirNuevaActividad = new JButton("AÑADIR NUEVA ACTIVIDAD");
		btnAnadirNuevaActividad.setBounds(57, 64, 337, 48);
		contentPane.add(btnAnadirNuevaActividad);
		btnAnadirNuevaActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNuevaActividadGUI anadirNuevaActividad = new AnadirNuevaActividadGUI(encargado);
				anadirNuevaActividad.setLocation(getLocation());
				anadirNuevaActividad.setVisible(true);
				dispose();
			}
		});
		
		

		//Botón para PLANIFICAR SESIÓN
		JButton btnPlanificarSesion = new JButton("PLANIFICAR SESIÓN");
		btnPlanificarSesion.setBounds(57, 122, 337, 48);
		contentPane.add(btnPlanificarSesion);
		btnPlanificarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlanificarSesionGUI planificarSesion = new PlanificarSesionGUI(encargado);
				planificarSesion.setLocation(getLocation());
				planificarSesion.setVisible(true);
				dispose();
			}
		});

		
		//Botón para ENVIAR FACTURA
		JButton btnEnviarFactura = new JButton("ENVIAR FACTURA");
		btnEnviarFactura.setBounds(57, 180, 337, 48);
		contentPane.add(btnEnviarFactura);
		btnEnviarFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnviarFacturaGUI enviarFactura = new EnviarFacturaGUI(encargado);
				enviarFactura.setLocation(getLocation());
				enviarFactura.setVisible(true);
				dispose();
			}
		});

		
		
		// Botón para cerrar la sesión y volver a MainGUI
		JButton btnCerrarSesionYVolver = new JButton("Cerrar sesión y volver");
		btnCerrarSesionYVolver.setBounds(286, 253, 164, 21);
		contentPane.add(btnCerrarSesionYVolver);
		btnCerrarSesionYVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI main = new MainGUI();
				main.setLocation(getLocation());
				main.setVisible(true);
				dispose();
			}
		});

	}
}
