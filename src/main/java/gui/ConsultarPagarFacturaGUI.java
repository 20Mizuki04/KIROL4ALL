package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Factura;
import domain.Socio;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsultarPagarFacturaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCodigo;
	
	
	private static Socio socio;
	
	private BLFacade logNeg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultarPagarFacturaGUI frame = new ConsultarPagarFacturaGUI(socio);
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
	public ConsultarPagarFacturaGUI(Socio socio) {
		
		this.socio = socio;
		
		logNeg = MainGUI.getBusinessLogic();
		
		setTitle("Proyecto KIROL4ALL: ConsultarPagarFacturaGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Etiquetas
		JLabel lblInstrucciones = new JLabel(
				"Consulte e introduzca el código de la factura y pulse pagar factura para abonarla");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(10, 10, 577, 38);
		contentPane.add(lblInstrucciones);

		JLabel lblListaFactura = new JLabel("Lista de facturas pendientes:");
		lblListaFactura.setBounds(10, 58, 192, 13);
		contentPane.add(lblListaFactura);

		JLabel lblIntroducirCodigo = new JLabel("Código de la factura a pagar: ");
		lblIntroducirCodigo.setBounds(10, 300, 242, 13);
		contentPane.add(lblIntroducirCodigo);

		
		
		// Etiqueta de errores
		JLabel lblError = new JLabel("");
		lblError.setBounds(10, 380, 482, 39);
		contentPane.add(lblError);
		
		
		
		
		//Campo para meter el código de la factura
		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(185, 297, 74, 19);
		contentPane.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);
		textFieldCodigo.setEnabled(false);
		
		
		
		
		
		// ScrollPane para la lista de facturas
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 577, 178);
		contentPane.add(scrollPane);
		
		JTextArea textAreaListaFacturas = new JTextArea();
		textAreaListaFacturas.setEditable(false);
		scrollPane.setViewportView(textAreaListaFacturas);

		
		//Metemos las facturas del socio en la lista
		List<Factura> facturas = logNeg.consultarFacturasSocio(socio);
		for(Factura f : facturas) {
			textAreaListaFacturas.setText(f.toString() + "\n\n\n");
		}
		
		
		
		
		
		
		
		//Botón para pagar la factura
		JButton btnPagarFactura = new JButton("Pagar factura");
		btnPagarFactura.setBounds(10, 326, 577, 44);
		contentPane.add(btnPagarFactura);
		btnPagarFactura.setEnabled(false);
		
		btnPagarFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblError.setForeground(Color.RED);
				
				String codigoString = textFieldCodigo.getText();
				Long codigoLong;
				
				//Si no ha metido el código de la factura
				if(codigoString.isEmpty()) {
					lblError.setText("Debe de introducir el código de la factura para poder pagarla");
				}
				//Si ha metido el código de la factura
				else {
					//Verificar que únicamente haya metido dígitos en el campo de código
					try {
						codigoLong = Long.parseLong(codigoString);
					}
					catch(NumberFormatException ex){
						lblError.setText("El código debe contener únicamente dígitos");
						return;
					}
					
					String exitoPago = logNeg.pagarFactura(socio, codigoLong);
					
					//Si el código dado no corresponde a una de las facturas del socio
					if(exitoPago.equalsIgnoreCase("Codigo mal")) {
						lblError.setText("El codigo introducido no pertenece a ninguna de sus facturas pendientes");
					}
					//Si el código dado corresponde a una de las facturas del socio
					else if(exitoPago.equalsIgnoreCase("Saldo insuficiente")) {
						lblError.setText("El saldo de su cuenta bancaria es insuficiente");
					}
					//Si ha habido algun error
					else if(exitoPago.equalsIgnoreCase("Error sistema bancario")) {
						lblError.setText("Ha ocurrido un error en el sistema bancario");
					}
					else if(exitoPago.equalsIgnoreCase("OK")){
						lblError.setForeground(Color.GREEN);
						lblError.setText("El pago se ha realizado con éxito");
						//Actualizamos el area de texto de facturas
						textAreaListaFacturas.setText("");
						List<Factura> facturas = logNeg.consultarFacturasSocio(socio);
						for(Factura f : facturas) {
							textAreaListaFacturas.setText(f.toString() + "\n\n\n");
						}
						//Si no hay faturas pendientes
						if(textAreaListaFacturas.getText().equalsIgnoreCase("")) {
							lblError.setForeground(Color.BLACK);
							lblError.setText("No tiene facturas pendientes");
						}
						//Si tiene facturas pendientes, se activa el botón de pagar factura
						else {
							btnPagarFactura.setEnabled(true);
							textFieldCodigo.setEnabled(true);
						}
						
						
					}
					
				}
			}
		});
		


		
		//Si no hay faturas pendientes
		if(textAreaListaFacturas.getText().equalsIgnoreCase("")) {
			lblError.setText("No tiene facturas pendientes");
		}
		//Si tiene facturas pendientes, se activa el botón de pagar factura
		else {
			btnPagarFactura.setEnabled(true);
			textFieldCodigo.setEnabled(true);
		}
		

		

		
		
		
		

		// Botón de volver a MenuSocioGUI
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(502, 398, 85, 21);
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
