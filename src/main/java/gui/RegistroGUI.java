package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;



public class RegistroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldCorreo;
	private JTextField textFieldNumCuentaOTarjeta;
	private JPasswordField passwordFieldContraseña;
	private JPasswordField passwordFieldConfirmarContraseña;
	private JRadioButton rdbtnSocio;
	private JRadioButton rdbtnEncargado;
	
	private BLFacade logNeg ;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroGUI frame = new RegistroGUI();
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
	public RegistroGUI() {
		setTitle("Proyecto KIROL4ALL: RegistroGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Etiquetas
		JLabel lblIntroducirDatos = new JLabel("Introduzca los siguientes datos:");
		lblIntroducirDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroducirDatos.setBounds(146, 10, 185, 27);
		contentPane.add(lblIntroducirDatos);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(31, 157, 164, 13);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setBounds(31, 187, 164, 13);
		contentPane.add(lblCorreo);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(31, 222, 164, 13);
		contentPane.add(lblContraseña);
		
		JLabel lblConfirmarContraseña = new JLabel("Confirmar contraseña:");
		lblConfirmarContraseña.setBounds(31, 259, 164, 13);
		contentPane.add(lblConfirmarContraseña);
		
		JLabel lblNumCuentaOTarjeta = new JLabel("<html>Número de cuenta bancaria <br>o número de tarjeta:");
		lblNumCuentaOTarjeta.setBounds(31, 282, 185, 38);
		contentPane.add(lblNumCuentaOTarjeta);
		
		JLabel lblQuieroSer = new JLabel("Quiero ser:");
		lblQuieroSer.setBounds(31, 111, 111, 13);
		contentPane.add(lblQuieroSer);
		
		JLabel lblNota = new JLabel("<html> En caso de pago con cuenta bancaria introducir solo los números a partir de ES  <br>" + "En caso de ser encargado no rellenar el campo del método de pago");
		lblNota.setHorizontalAlignment(SwingConstants.CENTER);
		lblNota.setBounds(31, 37, 427, 50);
		contentPane.add(lblNota);
		
		
		//Contenedores
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(223, 157, 191, 19);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setBounds(223, 187, 191, 19);
		contentPane.add(textFieldCorreo);
		textFieldCorreo.setColumns(10);
		
	
		passwordFieldContraseña = new JPasswordField();
		passwordFieldContraseña.setBounds(223, 222, 191, 19);
		contentPane.add(passwordFieldContraseña);
		
		passwordFieldConfirmarContraseña = new JPasswordField();
		passwordFieldConfirmarContraseña.setBounds(223, 259, 191, 19);
		contentPane.add(passwordFieldConfirmarContraseña);
		
		textFieldNumCuentaOTarjeta = new JTextField();
		textFieldNumCuentaOTarjeta.setBounds(223, 298, 191, 19);
		contentPane.add(textFieldNumCuentaOTarjeta);
		textFieldNumCuentaOTarjeta.setColumns(10);
		textFieldNumCuentaOTarjeta.setEnabled(false); //Deshabilitar por si es encargado
		
		
		//Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(31, 341, 427, 37);
		lblError.setForeground(Color.RED);
		contentPane.add(lblError);
		
		
		//Botones de seleccionar el rol
		rdbtnSocio = new JRadioButton("Socio");
		buttonGroup.add(rdbtnSocio);
		rdbtnSocio.setBounds(217, 107, 103, 21);
		contentPane.add(rdbtnSocio);
		rdbtnSocio.setSelected(false); // deseleccionar
		rdbtnSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumCuentaOTarjeta.setEnabled(true);
			}
		});
		
		rdbtnEncargado = new JRadioButton("Encargado");
		buttonGroup.add(rdbtnEncargado);
		rdbtnEncargado.setBounds(333, 107, 103, 21);
		contentPane.add(rdbtnEncargado);
		rdbtnEncargado.setSelected(false);; // deseleccionar
		rdbtnEncargado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNumCuentaOTarjeta.setEnabled(false);
				textFieldNumCuentaOTarjeta.setText("");
			}
		});
		
		
		
		//Botón de confirmar registro: Registrarse
		JButton btnRegistro = new JButton("Registrarse");
		btnRegistro.setBounds(132, 388, 232, 21);
		contentPane.add(btnRegistro);
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				String correo = textFieldCorreo.getText();
				String contraseña = new String(passwordFieldContraseña.getPassword());
				String confirmarContraseña = new String(passwordFieldConfirmarContraseña.getPassword());
				String cuentaOTarjetaText = textFieldNumCuentaOTarjeta.getText();
				int cuentaOTarjeta = 0;
				
				lblError.setForeground(Color.RED);
				
				
				//Si no se ha seleccionado un rol
				if(!rdbtnSocio.isSelected() && !rdbtnEncargado.isSelected()) {
					lblError.setText("Debe elegir un rol: socio o encargado");
				}
				//Si el ususario quiere ser Socio y no rellena todos los campos  //Si el usuario es quiere ser Encargado y no rellena todos los campos (a excepción del método de pago)
				else if((rdbtnSocio.isSelected() && (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty() || cuentaOTarjetaText.isEmpty())) ||
						rdbtnEncargado.isSelected() && (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty())) {
					lblError.setText("Es obligatorio rellenar todos los campos");
				}
				//Si el correo no acaba en @gmail.com, @hotmail.com, @outlook.com, @yahoo.es, @ikasle.ehu.eus
				else if(!correo.endsWith("@gmail.com") && !correo.endsWith("@hotmail.com") && !correo.endsWith("@outlook.com") && !correo.endsWith("@yahoo.es") && !correo.endsWith("@ikasle.ehu.eus") ) {
					lblError.setText("<html>El correo debe acabar en : @gmail.com, @hotmail.com,<br> @outlook.com, @yahoo.es, @ikasle.ehu.eus");
				}
				
				//Si la contraseña y confirmación de contraseña no coinciden
				else if(!contraseña.equals(confirmarContraseña)) {
					lblError.setText("Las contraseñas no coinciden");
				}
				
				else {
					//Si es un socio, verificar que se ha introducido únicamente números en el método de pago
					if(rdbtnSocio.isSelected()) {
						try {
							cuentaOTarjeta = Integer.parseInt(textFieldNumCuentaOTarjeta.getText());
						} catch (NumberFormatException ex) {
					        lblError.setText("La cuenta/tarjeta debe contener únicamente dígitos");
					        return;
					    }
					}

					logNeg = MainGUI.getBusinessLogic();
					boolean registrado;
					
					//Si el usuario quiere ser Socio
					if(rdbtnSocio.isSelected()) {
						registrado = logNeg.registrarSocio(nombre, correo, contraseña, cuentaOTarjeta);
					}
					//Si el usuario quiere ser Encargado
					else {
						registrado = logNeg.registrarEncargado(nombre, correo, contraseña);
					}
					
					
					//Si el usuario ya existe
					if(!registrado) {
						lblError.setText("Este usuario ya existe");
					}
					//Si el usuario no existe, se registra con éxito
					else {
						lblError.setForeground(Color.GREEN);
						lblError.setText("El registro se ha completado con éxito");
						textFieldNombre.setText("");
						textFieldCorreo.setText("");
						passwordFieldContraseña.setText("");
						passwordFieldConfirmarContraseña.setText("");
						textFieldNumCuentaOTarjeta.setText("");
						
					}
					
				}
				
			}
		});
		
		
		
		//Botón de volver a MainGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(398, 420, 79, 21);
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
