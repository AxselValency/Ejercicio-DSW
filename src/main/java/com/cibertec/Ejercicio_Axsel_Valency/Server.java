package com.cibertec.Ejercicio_Axsel_Valency;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server {
    private static final int PORT = 13;

    public Server() {
        System.out.println("1 >> [ini] Server constructor");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("2 >> waiting for client...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("3 >> accepted client connection...");
                
                // Recibir y guardar la imagen en la base de datos
                recibirYGuardarImagen(clientSocket);

                System.out.println("4 >> final for client...");
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recibirYGuardarImagen(Socket clientSocket) {
        try (Connection connection = Conennection.getConnection()) {
            // Leer datos del cliente
            DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
            
            // Obtener nombre y tamaño de la imagen
            String nombreImagen = entrada.readUTF();
            long tamañoImagen = entrada.readLong();

            // Crear un archivo temporal para guardar la imagen
            File file = File.createTempFile("temp-", ".tmp");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                // Recibir la imagen y escribirla en el archivo
                byte[] buffer = new byte[4096];
                long bytesRead = 0;
                while (bytesRead < tamañoImagen) {
                    int read = entrada.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    fos.write(buffer, 0, read);
                    bytesRead += read;
                }
            }

            // Guardar la imagen en la base de datos
            guardarImagenEnBD(connection, file, nombreImagen, tamañoImagen);
            
            // Eliminar el archivo temporal después de guardarlo en la base de datos
            file.delete();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarImagenEnBD(Connection connection, File file, String nombreImagen, long tamañoImagen) throws SQLException {
        String query = "INSERT INTO imagen (nombre, tamaño, archivo) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query);
             FileInputStream fis = new FileInputStream(file)) {
            statement.setString(1, nombreImagen);
            statement.setLong(2, tamañoImagen);
            statement.setBinaryStream(3, fis, file.length());
            statement.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
