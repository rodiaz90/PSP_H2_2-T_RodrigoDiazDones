package psp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

// Clase principal que gestiona la ejecución del servidor
public class Servidor {
    public static void main(String[] args) {
        // Crear un ExecutorService con un pool de hilos para manejar múltiples solicitudes simultáneamente
        ExecutorService executor = Executors.newFixedThreadPool(2); 
        
        try (ServerSocket serverSocket = new ServerSocket(12345)) { 
            // Crear un servidor que escucha en el puerto 12345
        	System.out.println("Programa realizado por Rodrigo Díaz Dones.");
            System.out.println("Servidor iniciado... esperando conexiones.");
            
            // Ciclo infinito para aceptar conexiones entrantes
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Aceptar una nueva conexión de cliente
                // Asignar la solicitud del cliente a un hilo del ExecutorService
                executor.submit(new ClientHandler(clientSocket)); 
            }
        } catch (IOException e) {
            e.printStackTrace();  // Manejar excepciones si ocurren
        }
    }
}

// Clase que maneja las solicitudes de los clientes
class ClientHandler implements Runnable {
    private Socket clientSocket;
    
    // Instancia de la clase de acceso a los datos para buscar los libros
    private AccesoDatos dataAccess = new AccesoDatos(); 

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;  // Asignar el socket del cliente
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { 
            
            String searchTerm;  // Variable para almacenar el término de búsqueda ingresado por el cliente
            
            // Recibir solicitudes del cliente hasta que el cliente cierre la conexión
            while ((searchTerm = in.readLine()) != null) {
                System.out.println("Buscando libros relacionados con: " + searchTerm);
                
                // Buscar los libros que coinciden con el término de búsqueda utilizando BookDataAccess
                String response = dataAccess.searchBooks(searchTerm);
                
                // Enviar la respuesta al cliente
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();  // Manejar excepciones si ocurren
        }
    }
}