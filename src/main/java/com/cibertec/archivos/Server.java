package com.cibertec.archivos;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    
    private static final int PORT = 13;

    public Server(){
        System.out.println("1 >> [ini] Server constructor");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("2 >> waiting for client...");                
            while(true){
                Socket clienSocket = serverSocket.accept();
                System.out.println("3 >> accepted client connection...");
                
                //Generar nombre del archivo
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String rutaArchivo = "G:\\A_EXPERIENCIAS 5 2024\\foto_" + sdf.format(new Date()) + ".png";
                File file = new File(rutaArchivo);
                System.out.println("Archivo creado: " + file.getAbsolutePath());

                //Flujo de datos de entrada y salida
                FileOutputStream fos = new FileOutputStream(file);
                DataInputStream entrada = new DataInputStream(clienSocket.getInputStream());

                //Recibiendo archivo
                byte[] buffer = new byte[1024];
                int count;
                while ((count = entrada.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                System.out.println("Archivo recibido: " + file.getAbsolutePath());

                System.out.println("4 >> final for client...");
                clienSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        new Server();   
    }
}
