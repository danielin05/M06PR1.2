package com.project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.project.excepcions.IOFitxerExcepcio;

public class PR120mainPersonesHashmap {
    private static String filePath = System.getProperty("user.dir") + "/data/PR120persones.dat";

    public static void main(String[] args) {
        // Crear el HashMap con los datos de las personas
        HashMap<String, Integer> persones = new HashMap<>();
        persones.put("Anna", 25);
        persones.put("Bernat", 30);
        persones.put("Carla", 22);
        persones.put("David", 35);
        persones.put("Elena", 28);

        try {
            // Escribir las personas en el archivo
            escriurePersones(persones);
            // Leer las personas desde el archivo y mostrar por pantalla
            llegirPersones();
        } catch (IOFitxerExcepcio e) {
            // Gestión de la excepción para evitar que el programa termine abruptamente
            System.err.println("Error en trabajar con el archivo: " + e.getMessage());
        }
    }

    // Método para escribir las personas en el archivo
    public static void escriurePersones(HashMap<String, Integer> persones) throws IOFitxerExcepcio {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            for (Map.Entry<String, Integer> entry : persones.entrySet()) {
                dos.writeUTF(entry.getKey());  // Escribir el nombre
                dos.writeInt(entry.getValue()); // Escribir la edad
            }
        } catch (IOException e) {
            // Lanzar una excepción personalizada en caso de error de escritura
            throw new IOFitxerExcepcio("Error en escriure les persones al fitxer", e);
        }
    }

    // Método para leer las personas desde el archivo
    public static void llegirPersones() throws IOFitxerExcepcio {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            while (dis.available() > 0) {
                String nom = dis.readUTF();  // Leer el nombre
                int edat = dis.readInt();    // Leer la edad
                System.out.println(nom + ": " + edat + " anys");
            }
        } catch (FileNotFoundException e) {
            // Lanzar una excepción personalizada si el archivo no existe
            throw new IOFitxerExcepcio("Error en llegir les persones del fitxer: Fitxer no trobat", e);
        } catch (IOException e) {
            // Lanzar una excepción personalizada si ocurre un error de lectura
            throw new IOFitxerExcepcio("Error en llegir les persones del fitxer", e);
        }
    }

    // Getter para filePath
    public static String getFilePath() {
        return filePath;
    }

    // Setter para filePath
    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }
}
