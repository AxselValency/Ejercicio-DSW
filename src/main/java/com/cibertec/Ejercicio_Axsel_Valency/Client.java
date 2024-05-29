package com.cibertec.Ejercicio_Axsel_Valency;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 13;
    private static final String RUTA_IMAGEN = "C:\\Users\\Lenovo\\OneDrive\\Escritorio\\imagenes";

    public Client() {
        System.out.println("1 >> [ini] Client constructor");
        try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

      
            File carpeta = new File(RUTA_IMAGEN);
            File[] archivos = carpeta.listFiles();
            if (archivos != null && archivos.length > 0) {
                File imagenAleatoria = archivos[new Random().nextInt(archivos.length)];

                System.out.println("Enviando imagen aleatoria: " + imagenAleatoria.getName());

                try (DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                     FileInputStream fis = new FileInputStream(imagenAleatoria)) {

                    // Envío del nombre de la imagen
                    salida.writeUTF(imagenAleatoria.getName());

                    // Envío del tamaño de la imagen
                    salida.writeLong(imagenAleatoria.length());

                    // Envío de la imagen
                    byte[] buffer = new byte[4096];
                    int count;
                    while ((count = fis.read(buffer)) > 0) {
                        salida.write(buffer, 0, count);
                    }

                    salida.flush();
                    System.out.println("Imagen aleatoria enviada.");
                }
            } else {
                System.out.println("No hay imágenes en la carpeta especificada.");
            }

            System.out.println("4 >> final for client...");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
