package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Encargado;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AnadirNuevaActividadGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldGradoExigencia;
	private JTextField textFieldPrecio;

	private static Encargado encargado; // Para luego poder volver a MenuEncargadoGUI

	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnadirNuevaActividadGUI frame = new AnadirNuevaActividadGUI(encargado);
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
	public AnadirNuevaActividadGUI(Encargado encargado) {

		this.encargado = encargado;

		setTitle("Proyecto KIROL4ALL: AnadirNuevaActividadGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Etiquetas
		JLabel lblInstrucciones = new JLabel("Para crear una nueva actividad introduzca los siguientes datos:");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(36, 25, 371, 19);
		contentPane.add(lblInstrucciones);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(36, 73, 173, 13);
		contentPane.add(lblNombre);

		JLabel lblGradoExigencia = new JLabel("Grado de exigencia (1-5):");
		lblGradoExigencia.setBounds(36, 96, 173, 13);
		contentPane.add(lblGradoExigencia);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(36, 119, 173, 13);
		contentPane.add(lblPrecio);

		// Contenedores
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(219, 70, 146, 19);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		textFieldGradoExigencia = new JTextField();
		textFieldGradoExigencia.setBounds(219, 93, 146, 19);
		contentPane.add(textFieldGradoExigencia);
		textFieldGradoExigencia.setColumns(10);

		textFieldPrecio = new JTextField();
		textFieldPrecio.setBounds(219, 116, 146, 19);
		contentPane.add(textFieldPrecio);
		textFieldPrecio.setColumns(10);

		// Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(36, 145, 371, 36);
		lblError.setForeground(Color.RED);
		contentPane.add(lblError);

		logNeg = MainGUI.getBusinessLogic();

		// Botón de crear la actividad
		JButton btnCrearActividad = new JButton("Crear actividad");
		btnCrearActividad.setBounds(119, 191, 209, 27);
		contentPane.add(btnCrearActividad);
		btnCrearActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				String gradoExigenciaText = textFieldGradoExigencia.getText();
				String precioText = textFieldPrecio.getText();
				int gradoExigencia;
				float precio;
				
				lblError.setForeground(Color.RED);

				//Si hay algún campo sin rellenar
				if(nombre.isEmpty() || gradoExigenciaText.isEmpty() || precioText.isEmpty()) {
					lblError.setText("Es obligatorio rellenar todos los campos");
				}
				else {
					// Verificar que el gradoExigencia sea un número entero
					try {
						gradoExigencia = Integer.parseInt(textFieldGradoExigencia.getText());
						// Si el grado de exigencia no está dentro del rango de 1-5
						if (gradoExigencia < 1 || gradoExigencia > 5) {
							lblError.setText("El grado de exigencia debe de estar entre 1 y 5");
							return;
						}
					} catch (NumberFormatException ex) {
						lblError.setText("El formato del grado de exigencia debe de ser un número entero");
						return;
					}

					// Verificar que el precio sea un número decimal
					try {
						precio = Float.parseFloat(textFieldPrecio.getText());
						if (precio < 0) {
							lblError.setText("El precio no puede ser negativo");
							return;
						}
					} catch (NumberFormatException ex) {
						lblError.setText("El formato del precio debe de ser un número decimal");
						return;
					}

					boolean actividadCreadaExito = logNeg.crearActividad(nombre, gradoExigencia, precio);

					
					//Si la actividad existe
					if (!actividadCreadaExito) {
						lblError.setText("Esta actividad ya existe");
					} 
					//Si la actividad no existe, se crea y se guarda
					else {
						lblError.setForeground(Color.GREEN);
						lblError.setText("La actividad ha sido creada y guardada con éxito");
						textFieldNombre.setText("");
						textFieldGradoExigencia.setText("");
						textFieldPrecio.setText("");
					}
				}
				
			}

		});

		// Botón de volver a MenuEncargadoGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(349, 251, 79, 21);
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
