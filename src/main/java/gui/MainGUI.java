package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;



//import domain.Driver;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
 //   private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonIniciarSesion;
	private JButton jButtonRegistro;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JButton jButtonConsultarSesion;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();

		//driver=d;
		
		setTitle("Proyecto KIROL4ALL: MainGUI");
		this.setSize(495, 290);

		
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		setContentPane(jContentPane);

		
		//Etiqueta de Elegir una opción
		jLabelSelectOption = new JLabel("Bienvenido/a elija una opción");
		jLabelSelectOption.setBounds(0, 0, 481, 63);
		jContentPane.add(jLabelSelectOption);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
	
		

		
		//Botón de INICIAR SESIÓN
		jButtonIniciarSesion = new JButton("INICIAR SESIÓN");
		jButtonIniciarSesion.setBounds(58, 65, 372, 49);
		jContentPane.add(jButtonIniciarSesion);
		jButtonIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame iniciarSesion = new IniciarSesionGUI();
				iniciarSesion.setLocation(getLocation());
				iniciarSesion.setVisible(true);
				dispose();
			}
		});
		
		
		//Botón de REGISTRARSE
		jButtonRegistro = new JButton("REGISTRARSE");
		jButtonRegistro.setBounds(58, 124, 372, 49);
		jContentPane.add(jButtonRegistro);
		jButtonRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame registro = new RegistroGUI();
				registro.setLocation(getLocation()); //Coge la localización de la ventana y coloca la ventana registro en la misma posición
				registro.setVisible(true);
				dispose(); //Cierra la ventana
			}
		});
		
		
		//Botón de CONSULTAR SESIÓN
		jButtonConsultarSesion = new JButton("CONSULTAR SESIÓN");
		jButtonConsultarSesion.setBounds(58, 183, 372, 49);
		jContentPane.add(jButtonConsultarSesion);
		jButtonConsultarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame consultarSesion = new ConsultarSesionGUI();
				consultarSesion.setLocation(getLocation());
				consultarSesion.setVisible(true);
				dispose();
			}
		});
		
		
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
	
		});
	}
	
	
/*	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonRegistro.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonIniciarSesion.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getName());
	}
	*/
} // @jve:decl-index=0:visual-constraint="0,0"

