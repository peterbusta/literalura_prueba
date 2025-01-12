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
            System.out.println("1. Buscar un libro por título");
            System.out.println("2. Mostrar todos los libros buscados");
            System.out.println("3. Mostrar libros por idioma");
            System.out.println("4. Listar autores de libros buscados");
            System.out.println("5. Listar autores vivos en un año determinado");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        principal.mostrarTodosLosLibrosBuscados();
                        break;
                    case 3:
                        mostrarLibrosPorIdioma();
                        break;
                    case 4:
                        principal.listarAutores();
                        break;
                    case 5:
                        listarAutoresVivosEnAnio();
                        break;
                    case 6:
                        System.out.println("Saliendo de la aplicación...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 6.");
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

    private void mostrarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma para filtrar libros: ");
        String idioma = scanner.nextLine();
        principal.mostrarLibrosPorIdioma(idioma);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Ingrese el año para buscar autores vivos: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine());
            principal.listarAutoresVivosEnAnio(anio);
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Por favor, ingrese un número válido.");
        }
    }
}
