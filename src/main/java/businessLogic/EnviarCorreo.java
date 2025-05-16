package businessLogic;

import java.io.*;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import domain.Encargado;
import domain.Factura;
import domain.Socio;

public class EnviarCorreo {

	private static Encargado encargado;
	private static Socio socio;
	private static Factura factura;
	
			 


public EnviarCorreo(Encargado encargado, Socio socio, Factura factura) {
	this.encargado = encargado;
	this.socio = socio;
	this.factura = factura;
	
}

		 
public String enviarCorreo() {
	
	String receptor = socio.getCorreo().toString();

	try{
	            
			Properties props = new Properties();
//			props.put("mail.smtp.auth", "true");  // Si activamos, entonces hay que autenticarse
//			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.ehu.es");
//			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props);
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(encargado.getCorreo().toString())); //Remitente
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receptor));
				message.setSubject("Mensaje de KIROL4ALL");
				message.setText("Estimado/a " + socio.getNombre() + ", "
					+ "\n\n Aquí tiene un resumen de su factura que deberá de abonar de las reservas realizadas la semana pasada: " + factura);
	 
				Transport.send(message);
	 
				System.out.println("Hecho");
	 
			} catch (MessagingException e) {
				//throw new RuntimeException(e);
				return "ERROR";
			}
		}
	catch (Exception e) {
		//System.out.println("Error: "+e.getMessage());
		return "ERROR";
		}
	return "OK";
	}

}
