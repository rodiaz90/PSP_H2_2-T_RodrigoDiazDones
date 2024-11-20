package psp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Clase cliente que interactúa con el servidor
public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345); 
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            // Crear un Scanner para leer la entrada desde la consola
            Scanner scanner = new Scanner(System.in);
            String searchTerm;
            
            System.out.println("Programa realizado por Rodrigo Díaz Dones.");
            System.out.println("Introduce la consulta (por ejemplo: 'Java' o 'Python'):");

            // Bucle para recibir múltiples consultas del usuario
            while (true) {
                searchTerm = scanner.nextLine().trim();  // Obtener la consulta y eliminar espacios extra
                
                if ("exit".equalsIgnoreCase(searchTerm)) { 
                    break;  // Salir si el usuario escribe 'exit'
                }
                
                // Enviar la consulta al servidor
                out.println(searchTerm);

                // Leer la respuesta del servidor y mostrarla al usuario
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    
                    // Romper el ciclo si ya no hay más respuestas
                    if (!in.ready()) break;
                }
                
                // Pedir otra consulta después de recibir la respuesta
                System.out.println("Introduce otra consulta o escribe 'exit' para salir:");
            }
            
            scanner.close();  // Cerrar el scanner para liberar los recursos
        } catch (IOException e) {
            e.printStackTrace();  // Manejar excepciones si ocurren
        }
    }
}