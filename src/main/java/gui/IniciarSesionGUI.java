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
import domain.Socio;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class IniciarSesionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textFieldCorreo;
	
	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IniciarSesionGUI frame = new IniciarSesionGUI();
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
	public IniciarSesionGUI() {
		setTitle("Proyecto KIROL4ALL: IniciarSesionGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Etiquetas
		JLabel lblInstrucciones = new JLabel("Introduzca los siguientes datos:");
		lblInstrucciones.setBounds(100, 10, 261, 13);
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInstrucciones);
		
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setBounds(45, 56, 139, 13);
		contentPane.add(lblCorreo);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(45, 83, 111, 13);
		contentPane.add(lblContrasena);
		
		
		
		//Contenedores
		passwordField = new JPasswordField();
		passwordField.setBounds(178, 79, 200, 21);
		contentPane.add(passwordField);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setBounds(178, 48, 200, 21);
		contentPane.add(textFieldCorreo);
		textFieldCorreo.setColumns(10);
		

		
		//Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(21, 136, 405, 39);
		contentPane.add(lblError);
		
		
		logNeg = MainGUI.getBusinessLogic();
		
		//Botón de iniciar sesión
		JButton btnIniciarSesion = new JButton("Iniciar sesión");
		btnIniciarSesion.setBounds(104, 185, 240, 21);
		contentPane.add(btnIniciarSesion);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String correo = textFieldCorreo.getText();
				String contrasena =  new String(passwordField.getPassword());
				
				lblError.setForeground(Color.RED);

				//Si no se rellenan los campos
				if(correo.isEmpty() || contrasena.isEmpty()) {
					lblError.setText("Es obligatorio rellenar todos los campos");
				}
				else {
					Encargado encargado = logNeg.verificarEncargado(correo, contrasena);
					Socio socio = logNeg.verificarSocio(correo, contrasena);
							
					//Si el socio o encargado (correo) no existe o la contraseña no es correcta
					if(encargado == null && socio == null) {
						lblError.setText("El correo o contraseña introducidos son incorrectos");
					}
					//Si va bien 
					else {
						
						//Si es encargado se inica sesión y se abre MenuEncargadoGUI
						if(encargado != null) {
							MenuEncargadoGUI menuEncargado = new MenuEncargadoGUI(encargado);
							menuEncargado.setLocation(getLocation());
							menuEncargado.setVisible(true);
							dispose();
						}
						//Si es socio se inicia sesión y se abre MenuSocioGUI
						else {
							MenuSocioGUI menuSocio = new MenuSocioGUI(socio);
							menuSocio.setLocation(getLocation());
							menuSocio.setVisible(true);
							dispose();
						}
						
					}
				}
			}
		});
		
		
		
		//Botón de volver a MainGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(341, 232, 85, 21);
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
