package com.cibertec.zip;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
public class Client {
    
    private static final String HOST = "localhost";
    private static final int PORT = 13;
    
    public Client(){
        System.out.println("1 >> [ini] Client constructor");
        try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

            File[] files = new File("D:\\client").listFiles();

            //Se crea un archivo zip
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\client\\archivo.zip"));
            for (File file : files) {
                //Se crea una entrada en el archivo zip
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                //Se construye el archivo zip con los bytes del archivo
                byte[] buffer = new byte[1024];
                int count;
                while ((count = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, count);
                }
                fis.close();
                zos.closeEntry();    
            }
            zos.close();

            
            //Se envia el archivo zip
            File file = new File("D:\\client\\archivo.zip");

            //Flujo de datos de entrada y salida
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            //Envio de archivo
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                salida.write(buffer, 0, count);
            }
            fis.close();
            
            System.out.println("Archivo enviado: " + file.getAbsolutePath());
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

