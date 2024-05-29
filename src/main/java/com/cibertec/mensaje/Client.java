package com.cibertec.mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client {
    
    private static final String HOST = "localhost";
    private static final int PORT = 13;
    
    public Client(){
        System.out.println("1 >> [ini] Client constructor");
        try {
            System.out.println("2 >> connecting to server...");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 >> connected to server...");

            //Flujo de datos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            
            //Reglas de negocio
            String[] tiposCliente = {"PLATEA", "VIP", "PLATINIUM"};
            Random random = new Random();
            int index = random.nextInt(tiposCliente.length);
            String mensaje = tiposCliente[index];
            System.out.println("Enviando mensaje: " + mensaje);

            //Salida de mensaje
            salida.println(mensaje);

            //Recibiendo mensaje
            String precio = entrada.readLine();
            System.out.println("Precio: " + precio);
            
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
