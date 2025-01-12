package com.aluracursos.challenges.literalura.principal;

import com.aluracursos.challenges.literalura.model.Datos;
import com.aluracursos.challenges.literalura.model.DatosLibros;
import com.aluracursos.challenges.literalura.model.DatosAutor;
import com.aluracursos.challenges.literalura.service.ConsumoAPI;
import com.aluracursos.challenges.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    // Lista para almacenar todos los libros buscados
    private final List<DatosLibros> librosBuscados = new ArrayList<>();

    public boolean ejecutarOpcion(int opcion) {
        Scanner scanner = new Scanner(System.in);
        switch (opcion) {
            case 1:
                System.out.print("Ingrese el nombre del libro que desea buscar: ");
                String titulo = scanner.nextLine();
                buscarLibroPorTitulo(titulo);
                break;
            case 2:
                mostrarTodosLosLibrosBuscados();
                break;
            case 3:
                System.out.print("Ingrese el idioma para filtrar libros: ");
                String idioma = scanner.nextLine();
                mostrarLibrosPorIdioma(idioma);
                break;
            case 4:
                listarAutores();
                break;
            case 5:
                System.out.print("Ingrese el año para buscar autores vivos: ");
                try {
                    int anio = Integer.parseInt(scanner.nextLine());
                    listarAutoresVivosEnAnio(anio);
                } catch (NumberFormatException e) {
                    System.out.println("Año inválido. Por favor, ingrese un número válido.");
                }
                break;
            case 6:
                return false;
            default:
                System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 6.");
        }
        return true;
    }

    // Método para buscar libro por título y almacenar el primer resultado
    public void buscarLibroPorTitulo(String tituloLibro) {
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .findFirst(); // Retenemos el primer resultado

        if (libroBuscado.isPresent()) {
            DatosLibros libro = libroBuscado.get();
            librosBuscados.add(libro); // Almacenamos el libro en la lista
            System.out.println("\nLibro encontrado:");
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autor: " + libro.autor().get(0).nombre());
            System.out.println("Año de nacimiento: " + libro.autor().get(0).anioNacimiento());
            System.out.println("Año de fallecimiento: " + libro.autor().get(0).anioFallecimiento());
            System.out.println("Idioma: " + libro.idioma().get(0));
            System.out.println("Número de descargas: " + libro.numeroDeDescargas());
        } else {
            System.out.println("\nLibro no encontrado.");
        }
    }

    // Método para mostrar todos los libros buscados
    public void mostrarTodosLosLibrosBuscados() {
        if (librosBuscados.isEmpty()) {
            System.out.println("\nNo se han buscado libros aún.");
        } else {
            System.out.println("\nListado de todos los libros buscados:");
            librosBuscados.forEach(libro -> {
                System.out.println("Título: " + libro.titulo());
                System.out.println("Autor: " + libro.autor().get(0).nombre());
                System.out.println("Idioma: " + libro.idioma().get(0));
                System.out.println("Número de descargas: " + libro.numeroDeDescargas());
                System.out.println("-----------------------------");
            });
        }
    }

    // Método para mostrar libros según el idioma
    public void mostrarLibrosPorIdioma(String idioma) {
        var librosPorIdioma = librosBuscados.stream()
                .filter(libro -> !libro.idioma().isEmpty() && // Verifica que la lista no esté vacía
                        libro.idioma().get(0).equalsIgnoreCase(idioma)) // Compara con el primer idioma
                .collect(Collectors.toList());

        if (librosPorIdioma.isEmpty()) {
            System.out.println("\nNo se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("\nListado de libros en el idioma: " + idioma);
            librosPorIdioma.forEach(libro -> {
                System.out.println("Título: " + libro.titulo());
                System.out.println("Autor: " + libro.autor().get(0).nombre());
                System.out.println("Número de descargas: " + libro.numeroDeDescargas());
                System.out.println("-----------------------------");
            });
        }
    }

    // Método para listar todos los autores
    public void listarAutores() {
        if (librosBuscados.isEmpty()) {
            System.out.println("\nNo se han buscado libros aún.");
        } else {
            System.out.println("\nListado de autores de los libros buscados:");
            librosBuscados.stream()
                    .map(libro -> libro.autor().get(0)) // Obtiene el primer autor de cada libro
                    .distinct() // Evita duplicados
                    .forEach(autor -> {
                        System.out.println("Nombre: " + autor.nombre());
                        System.out.println("Año de nacimiento: " + autor.anioNacimiento());
                        System.out.println("Año de fallecimiento: " + autor.anioFallecimiento());
                        System.out.println("-----------------------------");
                    });
        }
    }

    // Método para listar autores vivos en un año determinado
    public void listarAutoresVivosEnAnio(int anio) {
        var autoresVivos = librosBuscados.stream()
                .map(libro -> libro.autor().get(0)) // Obtiene el primer autor de cada libro
                .filter(autor -> autor.anioNacimiento() <= anio && // Nacieron antes o durante el año
                        (autor.anioFallecimiento() == null || autor.anioFallecimiento() > anio)) // No han fallecido aún
                .distinct() // Evita duplicados
                .collect(Collectors.toList());

        if (autoresVivos.isEmpty()) {
            System.out.println("\nNo se encontraron autores vivos en el año: " + anio);
        } else {
            System.out.println("\nListado de autores vivos en el año: " + anio);
            autoresVivos.forEach(autor -> {
                System.out.println("Nombre: " + autor.nombre());
                System.out.println("Año de nacimiento: " + autor.anioNacimiento());
                System.out.println("Año de fallecimiento: " + (autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Aún vivo"));
                System.out.println("-----------------------------");
            });
        }
    }
}
