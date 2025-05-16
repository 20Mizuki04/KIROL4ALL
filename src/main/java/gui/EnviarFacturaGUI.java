package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Encargado;
import domain.Factura;
import domain.Reserva;
import domain.Socio;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class EnviarFacturaGUI extends JFrame {

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
					EnviarFacturaGUI frame = new EnviarFacturaGUI(encargado);
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
	public EnviarFacturaGUI(Encargado encargado) {
		
		this.encargado = encargado;
		
		logNeg = MainGUI.getBusinessLogic();

		
		setTitle("Proyecto KIROL4ALL: EnviarFacturaGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Etiquetas
		JLabel lblInstrucciones = new JLabel("Seleccione un socio para generar la factura y si lo desea enviarla");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(129, 10, 385, 24);
		contentPane.add(lblInstrucciones);
		
		JLabel lblResumenFactura = new JLabel("Resumen de la factura:");
		lblResumenFactura.setBounds(28, 259, 613, 13);
		contentPane.add(lblResumenFactura);
		
		
		
		//Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setBounds(28, 426, 511, 51);
		contentPane.add(lblError);
		
		
		//ScrollPane para la lista de los socios con más de 4 reservas
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 40, 606, 141);
		contentPane.add(scrollPane);
		
		//Lista de sesiones reservadas
		DefaultListModel<Socio> listModel = new DefaultListModel<>();
		JList<Socio> listaSocios = new JList<>(listModel);
		scrollPane.setViewportView(listaSocios);
		
		//Metemos la lista de socios que hayan hecho más de 4 reservas la semana pasada
		List<Socio> sociosMas4Reservas = logNeg.consultarSociosMasCuatroReservasSemanaPasada();
		for(Socio s : sociosMas4Reservas) {
			listModel.addElement(s);
		}
		
		
		
		//ScrollPane para el resumen de la factura
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 282, 606, 134);
		contentPane.add(scrollPane_1);

		//Área de texto del resumen de la factura
		JTextArea textAreaResumenFactura = new JTextArea();
		scrollPane_1.setViewportView(textAreaResumenFactura);
		textAreaResumenFactura.setEditable(false);
		
		
		
		
			
		
		//Botón para generar la factura
		JButton btnGenerarYEnviarFactura = new JButton("Generar y enviar factura");
		btnGenerarYEnviarFactura.setBounds(28, 191, 606, 45);
		contentPane.add(btnGenerarYEnviarFactura);
		btnGenerarYEnviarFactura.setEnabled(false);
		
		
		//Si la lista de socios de más de 4 reservas está vacía
		if(listModel.isEmpty()) {
			lblError.setText("Actualmente no hay socios con más de 4 reservas");
		}
		else {
			btnGenerarYEnviarFactura.setEnabled(true);
		}

		
		//Botón para generar la factura continuación
		btnGenerarYEnviarFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblError.setForeground(Color.RED);
				
				Socio socio = listaSocios.getSelectedValue(); //Socio seleccionado
				
				//Si no se ha seleccionado un socio
				if(socio == null) {
					lblError.setText("Debe seleccionar un socio para generar la factura");
				}
				//Si se ha seleccionado un socio
				else {
					//Creamos la factura
					Factura factura = logNeg.crearFactura(socio);
					textAreaResumenFactura.setText(factura.toString());
					boolean exitoEnvio = logNeg.enviarFactura(encargado, socio, factura);
					if(exitoEnvio) {
						lblError.setForeground(Color.GREEN);
						lblError.setText("La factura se ha creado y enviado con éxito");
						//Actualizamos la lista de socios
						listModel.removeElement(socio);
						if(listModel.isEmpty()) {
							lblError.setForeground(Color.BLACK);
							lblError.setText("Actualmente no hay socios con más de 4 reservas");
							btnGenerarYEnviarFactura.setEnabled(false);
						}
						else {
							btnGenerarYEnviarFactura.setEnabled(true);
						}

					}
				}
			}
		});
		
		//Botón de volver a MenuEncargadoGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(549, 446, 85, 21);
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
