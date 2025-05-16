package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Reserva;
import domain.Sesion;
import domain.Socio;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class ConsultarCancelarReservaGUI extends JFrame {

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
					ConsultarCancelarReservaGUI frame = new ConsultarCancelarReservaGUI(socio);
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
	public ConsultarCancelarReservaGUI(Socio socio) {
		setTitle("Proyecto KIROL4ALL: ConsultarCancelarReservaGUI");

		this.socio = socio;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		//Etiquetas
		JLabel lblInstrucciones = new JLabel("Seleccione una reserva para cancelarla");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(126, 10, 360, 13);
		contentPane.add(lblInstrucciones);
		
		
		//Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setBounds(32, 284, 454, 42);
		contentPane.add(lblError);
		
		
		logNeg = MainGUI.getBusinessLogic();
		
		
		//Scrollpane para la lista de sesiones reservadas
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 50, 557, 167);
		contentPane.add(scrollPane);

		//Lista de sesiones reservadas
		DefaultListModel<Reserva> listModel = new DefaultListModel<>();
		JList<Reserva> listaReservas = new JList<>(listModel);
		scrollPane.setViewportView(listaReservas);
		
		List<Reserva> lista = logNeg.consultarReservasSocio(socio);
		for(Reserva r :lista) {
			listModel.addElement(r);
		}
		
		
		
		
		
		//Botón para cancelar reserva
		JButton btnCancelarReserva = new JButton("Cancelar reserva");
		btnCancelarReserva.setBounds(32, 234, 557, 40);
		contentPane.add(btnCancelarReserva);
		btnCancelarReserva.setEnabled(false);
		
		
		
		//Si la lista de reservas esta vacía
		if(listModel.isEmpty()) {
			lblError.setForeground(Color.BLACK);
			lblError.setText("Actualmente no tiene ninguna reserva asignada");
		}
		//Si la lista de reservas no esta vacía, habilitamos el botón de cancelar
		else {
			btnCancelarReserva.setEnabled(true);
		}
		
		
		
		
		//Botón de cancelar reserva continuación
		btnCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblError.setForeground(Color.RED);
				
				Reserva reserva = listaReservas.getSelectedValue();
				
				//Si no ha seleccionado ninguna reserva
				if(reserva == null) {
					lblError.setText("Debe de seleccionar una reserva para poder cancelarla");
				}
				else {
					boolean exitoCancelacion = logNeg.cancelarReserva(reserva);
					if(exitoCancelacion) {
						listModel.clear();
						List<Reserva> lista = logNeg.consultarReservasSocio(socio);
						for(Reserva r :lista) {
							listModel.addElement(r);
						}
						lblError.setForeground(Color.GREEN);
						lblError.setText("La reserva ha sido cancelada con éxito");
						
						if(listModel.isEmpty()) {
							lblError.setForeground(Color.BLACK);
							lblError.setText("Actualmente no tiene ninguna reserva asignada");
							btnCancelarReserva.setEnabled(false);
						}
					}
				}
			}
		});
		
		
		
		
		// Botón de volver a MenuSocioGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(504, 305, 85, 21);
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
