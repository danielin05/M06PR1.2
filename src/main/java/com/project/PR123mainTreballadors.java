package com.project;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.utilitats.UtilsCSV;

import java.util.List;
import java.util.Scanner;

public class PR123mainTreballadors {
    private String filePath = System.getProperty("user.dir") + "/data/PR123treballadors.csv";
    private Scanner scanner = new Scanner(System.in);

    // Getters i setters per a filePath
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void iniciar() {
        boolean sortir = false;

        while (!sortir) {
            try {
                // Mostrar menú
                mostrarMenu();

                // Llegir opció de l'usuari
                int opcio = Integer.parseInt(scanner.nextLine());

                switch (opcio) {
                    case 1 -> mostrarTreballadors();
                    case 2 -> modificarTreballadorInteractiu();
                    case 3 -> {
                        System.out.println("Sortint...");
                        sortir = true;
                    }
                    default -> System.out.println("Opció no vàlida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Si us plau, introdueix un número vàlid.");
            } catch (IOFitxerExcepcio e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    // Mètode que mostra el menú
    private void mostrarMenu() {
        System.out.println("\nMenú de Gestió de Treballadors");
        System.out.println("1. Mostra tots els treballadors");
        System.out.println("2. Modificar dades d'un treballador");
        System.out.println("3. Sortir");
        System.out.print("Selecciona una opció: ");
    }

    // Mètode per mostrar els treballadors llegint el fitxer CSV
    public void mostrarTreballadors() throws IOFitxerExcepcio {
        List<String> treballadorsCSV = llegirFitxerCSV();
        treballadorsCSV.forEach(System.out::println);
    }

    // Mètode per modificar un treballador (interactiu)
    public void modificarTreballadorInteractiu() throws IOFitxerExcepcio {
        // Demanar l'ID del treballador
        System.out.print("\nIntrodueix l'ID del treballador que vols modificar: ");
        String id = scanner.nextLine();

        // Demanar quina dada vols modificar
        System.out.print("Quina dada vols modificar (Nom, Cognom, Departament, Salari)? ");
        String columna = scanner.nextLine();

        // Demanar el nou valor
        System.out.print("Introdueix el nou valor per a " + columna + ": ");
        String nouValor = scanner.nextLine();

        // Modificar treballador
        modificarTreballador(id, columna, nouValor);
    }

    // Mètode que modifica un treballador (per a tests i usuaris)
    public void modificarTreballador(String id, String columna, String nouValor) throws IOFitxerExcepcio {
        List<String> treballadorsCSV = llegirFitxerCSV();

        // Buscar el treballador amb l'ID proporcionat
        int numLinia = UtilsCSV.obtenirNumLinia(treballadorsCSV, "Id", id);
        if (numLinia == -1) {
            throw new IOFitxerExcepcio("No s'ha trobat cap treballador amb l'ID " + id);
        }

        // Obtenir la línia i modificar la columna especificada
        String[] dadesTreballador = UtilsCSV.obtenirArrayLinia(treballadorsCSV.get(numLinia));
        switch (columna.toLowerCase()) {
            case "nom" -> dadesTreballador[1] = nouValor;
            case "cognom" -> dadesTreballador[2] = nouValor;
            case "departament" -> dadesTreballador[3] = nouValor;
            case "salari" -> dadesTreballador[4] = nouValor;
            default -> throw new IOFitxerExcepcio("Columna no vàlida: " + columna);
        }

        // Actualitzar la línia modificada
        treballadorsCSV.set(numLinia, String.join(",", dadesTreballador));

        // Preguntar si vol guardar els canvis
        System.out.print("Vols guardar els canvis al fitxer? (sí/no): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("sí")) {
            escriureFitxerCSV(treballadorsCSV);
            System.out.println("Canvis desats correctament.");
        } else {
            System.out.println("Canvis no desats.");
        }
    }

    // Encapsulació de llegir el fitxer CSV
    private List<String> llegirFitxerCSV() throws IOFitxerExcepcio {
        List<String> treballadorsCSV = UtilsCSV.llegir(filePath);
        if (treballadorsCSV == null) {
            throw new IOFitxerExcepcio("Error en llegir el fitxer.");
        }
        return treballadorsCSV;
    }

    // Encapsulació d'escriure el fitxer CSV
    private void escriureFitxerCSV(List<String> treballadorsCSV) throws IOFitxerExcepcio {
        try {
            UtilsCSV.escriure(filePath, treballadorsCSV);
        } catch (Exception e) {
            throw new IOFitxerExcepcio("Error en escriure el fitxer.", e);
        }
    }

    // Mètode main
    public static void main(String[] args) {
        PR123mainTreballadors programa = new PR123mainTreballadors();
        programa.iniciar();
    }
}
