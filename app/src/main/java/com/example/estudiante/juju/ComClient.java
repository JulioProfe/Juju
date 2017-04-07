package com.example.estudiante.juju;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

/**
 * Created by estudiante on 07/04/17.
 */

public class ComClient extends Observable implements Runnable {

    private static ComClient instance;
    private Socket s = null;
    private final int PORT = 5000;
    private boolean online, validar;


    private ComClient() {
        this.validar = false;
    }

    public static ComClient getInstance() {
        if (instance == null) {
            instance = new ComClient();
            new Thread(instance).start();
        }
        return instance;
    }

    private void iniciarSocket() {

        if (s == null) {
            try {
                s = new Socket(InetAddress.getByName("10.0.2.2"), PORT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void recibirArchivos() throws IOException {
        DataInputStream entrada = new DataInputStream(new BufferedInputStream(
                s.getInputStream()));
        int numArchivos = entrada.readInt();
        for (int i = 0; i < numArchivos; i++) {
            String nombre = entrada.readUTF();
            int cantBytes = entrada.readInt();
            byte[] buf = new byte[cantBytes];
            int j = 0;
            while (j < cantBytes) {
                buf[j] = entrada.readByte();
                j++;
            }
            setChanged();
            notifyObservers(buf);
            clearChanged();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                iniciarSocket();
                if (validar) {
                    System.out.println("=====================");
                    recibirArchivos();
                    validar = false;
                }

                setChanged();
                notifyObservers("sume");
                clearChanged();
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }


}

