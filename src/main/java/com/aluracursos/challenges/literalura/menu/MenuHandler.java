package com.aluracursos.challenges.literalura.menu;

import com.aluracursos.challenges.literalura.principal.Principal;

public class MenuHandler {
    private final Principal principal;

    public MenuHandler() {
        this.principal = new Principal();
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
                int opcion = Integer.parseInt(System.console().readLine());

                if (!principal.ejecutarOpcion(opcion)) {
                    System.out.println("Saliendo de la aplicación...");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            }
        }
    }
}
