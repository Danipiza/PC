package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import ENTREGA.Mensajes.MEmitirFichero;
import ENTREGA.Mensajes.Mensaje;

public class InfoSocket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Socket sc;		 
	private DataInputStream in;
	private DataOutputStream out;
	private String IDCliente;
	
	public InfoSocket(Socket sc, DataInputStream in, DataOutputStream out, String IDCliente) {
		this.sc = sc;
		this.in = in;
		this.out = out;
		this.IDCliente = IDCliente;
	}
	
	public Socket getSocket() {
		return this.sc;
	}
	
	public DataInputStream getIn() {
		return this.in;
	}
	
	public DataOutputStream getOut() {
		return this.out;
	}
	
	public String getIDCliente() {
		return IDCliente;
	}
			
	public void enviaMensaje() throws IOException {
		Mensaje mensajeEnviado = new MEmitirFichero("Servidor", "Cliente");
		
		
		// Convierte la instancia de la clase del mensaje a enviar, en un array de bytes
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStreamEnviado = new ObjectOutputStream(byteStream);
		objectStreamEnviado.writeObject(mensajeEnviado);						
		byte[] bytesEnviado = byteStream.toByteArray();	
		
		// Envia la longitud del array y el array (clase mensaje)
		out.writeInt(bytesEnviado.length);
		out.write(bytesEnviado);
		System.out.println("El Servidor ha enviado -> " + mensajeEnviado.getTipo());
		
	}
	
	public void recibeMensaje() throws IOException, ClassNotFoundException {
		Mensaje mensajeRecibido;
		
		// Recibe la longitud del array
		int length = in.readInt();
		byte[] bytesRecibido = new byte[length];
		// Recibe el array de bytes
		in.readFully(bytesRecibido, 0, length);
		
		// Convierte el array de bytes en la clase mensaje
		ObjectInputStream objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
		mensajeRecibido = (Mensaje) objectStreamRecibido.readObject();
		System.out.println("El Servidor ha recibido -> " + mensajeRecibido.getTipo());
	}
	
}
