/*
En este servidor, cada cliente que se conecta es manejado por un objeto `ClientHandler`, que se ejecuta en su propio hilo. 
El servidor mantiene una lista de todos los clientes conectados para poder buscarlos por su nombre de usuario cuando sea 
necesario.

Cuando un cliente envía un mensaje que comienza con "CONNECT", el servidor busca al cliente correspondiente y le envía 
una solicitud de conexión en modo peer-to-peer. Si el otro cliente acepta la conexión, se establece una nueva conexión 
entre los dos clientes en un hilo separado `PeerToPeerHandler`. El `PeerToPeerHandler` simplemente transmite los mensajes 
recibidos de un cliente al otro.

**Cliente:**

```java*/
import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private BufferedReader consoleInput;

    public void start() {
        try {
            socket = new Socket("localhost", 12345);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Conectado al servidor.");

            String username = getUsername();
            output.println(username);

            while (true) {
                String message = consoleInput.readLine();
                if (message.equalsIgnoreCase("QUIT")) {
                    break;
                }
                output.println(message);
                String response = input.readLine();
                System.out.println(response);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Error al iniciar el cliente: " + e.getMessage());
        }
    }

    private String getUsername() {
        String username = "";
        while (username.isEmpty()) {
            System.out.print("Ingrese su nombre de usuario: ");
            try {
                username = consoleInput.readLine();
            } catch (IOException e) {
                System.out.println("Error al leer el nombre de usuario: " + e.getMessage());
            }
        }
        return username;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
