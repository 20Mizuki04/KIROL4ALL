package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Socio;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class MenuSocioGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel jLabelSelectOption;
	
	private static Socio socio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuSocioGUI frame = new MenuSocioGUI(socio);
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
	public MenuSocioGUI(Socio socio) {
		
		this.socio = socio;
		
		setTitle("Proyecto KIROL4ALL: MenuSocioGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Etiqueta seleccionar opción
		jLabelSelectOption = new JLabel("Bienvenido/a " + socio.getNombre() + " elija una opción");
		jLabelSelectOption.setBounds(10, 0, 426, 48);
		contentPane.add(jLabelSelectOption);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//Botón para RESERVAR SESIÓN
		JButton btnReservarSesion = new JButton("RESERVAR SESIÓN");
		btnReservarSesion.setBounds(57, 63, 334, 48);
		contentPane.add(btnReservarSesion);
		btnReservarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservarSesionGUI reservarSesion = new ReservarSesionGUI(socio);
				reservarSesion.setLocation(getLocation());
				reservarSesion.setVisible(true);
				dispose();
			}
		});
		
		
		//Botón para CONSULTAR / CANCELAR RESERVA
		JButton btnConsultarCancelarReserva = new JButton("CONSULTAR / CANCELAR RESERVA");
		btnConsultarCancelarReserva.setBounds(57, 121, 334, 49);
		contentPane.add(btnConsultarCancelarReserva);
		btnConsultarCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarCancelarReservaGUI consultarCancelarReserva = new ConsultarCancelarReservaGUI(socio);
				consultarCancelarReserva.setLocation(getLocation());
				consultarCancelarReserva.setVisible(true);
				dispose();
			}
		});
		
		
		//Botón para CONSULTAR / PAGAR FACTURA
		JButton btnConsultarPagarFactura = new JButton("CONSULTAR / PAGAR FACTURA");
		btnConsultarPagarFactura.setBounds(57, 184, 334, 48);
		contentPane.add(btnConsultarPagarFactura);
		btnConsultarPagarFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarPagarFacturaGUI consultarPagarFactura = new ConsultarPagarFacturaGUI(socio);
				consultarPagarFactura.setLocation(getLocation());
				consultarPagarFactura.setVisible(true);
				dispose();
			}
		});
		
		
		//Botón para cerrar la sesión y volver a MainGUI
		JButton btnCerrarSesionYVolver = new JButton("Cerrar sesión y volver");
		btnCerrarSesionYVolver.setBounds(272, 258, 164, 21);
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
