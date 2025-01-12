package com.aluracursos.challenges.literalura.menu;

import com.aluracursos.challenges.literalura.principal.Principal;

import java.util.Scanner;

public class MenuHandler {
    private final Principal principal;
    private final Scanner scanner;

    public MenuHandler() {
        this.principal = new Principal();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarMenu() {
        while (true) {
            System.out.println("\n--- Menú de Opciones ---");
            System.out.println("1. Mostrar los 10 libros más descargados");
            System.out.println("2. Buscar un libro por título");
            System.out.println("3. Mostrar estadísticas de descargas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        principal.mostrarTop10Libros();
                        break;
                    case 2:
                        buscarLibro();
                        break;
                    case 3:
                        principal.mostrarEstadisticas();
                        break;
                    case 4:
                        System.out.println("Saliendo de la aplicación...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            }
        }
    }

    private void buscarLibro() {
        System.out.print("Ingrese el nombre del libro que desea buscar: ");
        String titulo = scanner.nextLine();
        principal.buscarLibroPorTitulo(titulo);
    }
}
