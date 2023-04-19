import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                ClientHandler client = new ClientHandler(clientSocket);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter output;
        private BufferedReader input;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);

                while (true) {
                    String message = input.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println("Mensaje recibido del cliente: " + message);
                    if (message.startsWith("CONNECT")) {
                        String[] parts = message.split(" ");
                        if (parts.length == 2) {
                            String targetUsername = parts[1];
                            ClientHandler targetClient = findClientByUsername(targetUsername);
                            if (targetClient == null) {
                                output.println("El usuario " + targetUsername + " no está conectado.");
                            } else {
                                output.println("Conectando con " + targetUsername + "...");
                                targetClient.output.println("El usuario " + getUsername() + " quiere conectarse contigo. ¿Aceptas? (S/N)");
                                String response = input.readLine();
                                if (response != null && response.equalsIgnoreCase("S")) {
                                    output.println("Conexión establecida con " + targetUsername);
                                    targetClient.output.println("Conexión establecida con " + getUsername());
                                    connectPeerToPeer(targetClient);
                                } else {
                                    output.println("El usuario " + targetUsername + " rechazó la conexión.");
                                }
                            }
                        }
                    }
                }

                clientSocket.close();
                clients.remove(this);
            } catch (IOException e) {
                System.out.println("Error al procesar el mensaje del cliente: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        public String getUsername() {
            return clientSocket.getInetAddress().getHostName();
        }

        private ClientHandler findClientByUsername(String username) {
            for (ClientHandler client : clients) {
                if (client.getUsername().equals(username)) {
                    return client;
                }
            }
            return null;
        }

        private void connectPeerToPeer(ClientHandler targetClient) {
            try {
                Socket peerSocket = new Socket(targetClient.clientSocket.getInetAddress(), 12346);
                output.println("Conectado con " + targetClient.getUsername() + " en modo peer-to-peer.");
                targetClient.output.println("Conectado con " + getUsername() + " en modo peer-to-peer.");
                new PeerToPeerHandler(clientSocket, peerSocket).start();
            } catch (IOException e) {
                System.out.println("Error al conectar en modo peer-to-peer: " + e.getMessage());
            }
        }
    }

    private class PeerToPeerHandler extends Thread {
        private Socket clientSocket;
        private Socket peerSocket;
        private BufferedReader clientInput;
        private PrintWriter clientOutput;
        private BufferedReader peerInput;
        private PrintWriter peerOutput;

        public PeerToPeerHandler(Socket clientSocket, Socket peerSocket) {
            this.clientSocket = clientSocket;
            this.peerSocket = peerSocket;
        }

        public void run() {
            try {
                clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                peerInput = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
                peerOutput = new PrintWriter(peerSocket.getOutputStream(), true);

                while (true) {
                    String clientMessage = clientInput.readLine();
                    if (clientMessage == null) {
                        break;
                    }
                    System.out.println("Mensaje del cliente " + getUsername() + " recibido: " + clientMessage);
                    peerOutput.println(clientMessage);
                }

                clientSocket.close();
                peerSocket.close();
            } catch (IOException e) {
                System.out.println("Error en la conexión peer-to-peer: " + e.getMessage());
            }
        }

        public String getUsername() {
            return clientSocket.getInetAddress().getHostName();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

