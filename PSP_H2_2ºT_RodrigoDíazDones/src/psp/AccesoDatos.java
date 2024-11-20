package psp;

import java.io.*;

// Clase para acceder a los datos de los libros
public class AccesoDatos {
    
    // Método que busca libros en el archivo .dat basado en el término de búsqueda
    public String searchBooks(String searchTerm) {
        StringBuilder result = new StringBuilder();  // StringBuilder para almacenar los resultados
        
        try (BufferedReader reader = new BufferedReader(new FileReader("libros.dat"))) { 
            // Abrir el archivo "libros.dat" para leer los libros
            String line;
            
            // Leer cada línea del archivo
            while ((line = reader.readLine()) != null) {
                // Comprobar si la línea contiene el término de búsqueda, insensible a mayúsculas/minúsculas
                if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                    result.append(line).append("\n");  // Agregar el libro al resultado
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Manejar excepciones si ocurren
        }
        
        // Si no se encontró ningún libro, devolver un mensaje adecuado
        return result.toString().isEmpty() ? "No se encontraron resultados." : result.toString(); 
    }
}