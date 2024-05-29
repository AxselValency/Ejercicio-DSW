package com.cibertec.mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private static final int PORT = 13;

    public Server(){
        System.out.println("1 >> [ini] Server constructor");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("2 >> waiting for client...");                
            while(true){
                Socket clienSocket = serverSocket.accept();
                System.out.println("3 >> accepted client connection...");
                
                //Flujo de datos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
                PrintWriter salida = new PrintWriter(clienSocket.getOutputStream(), true);

                //Reglas de negocio
                String mensajeString = entrada.readLine();
                System.out.println("Mensaje recibido: " + mensajeString);

                String precio = "";
                switch (mensajeString) {
                    case "PLATEA": precio = "PEN 50";break;
                    case "VIP": precio = "PEN 150";break;
                    case "PLATINIUM": precio = "PEN 2250";break;
                }
                salida.println(precio);
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
