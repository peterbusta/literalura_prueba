package com.aluracursos.challenges.literalura.principal;

import com.aluracursos.challenges.literalura.model.Datos;
import com.aluracursos.challenges.literalura.model.DatosLibros;
import com.aluracursos.challenges.literalura.service.ConsumoAPI;
import com.aluracursos.challenges.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    // Lista para almacenar todos los libros buscados
    private final List<DatosLibros> librosBuscados = new ArrayList<>();

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
            System.out.println("Autor: " + libro.autor());
            System.out.println("Idioma: " + libro.idioma());
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
                System.out.println("Autor: " + libro.autor());
                System.out.println("Idioma: " + libro.idioma());
                System.out.println("Número de descargas: " + libro.numeroDeDescargas());
                System.out.println("-----------------------------");
            });
        }
    }

    // Método para mostrar libros según el idioma
    public void mostrarLibrosPorIdioma(String idioma) {
        var librosPorIdioma = librosBuscados.stream()
                .filter(libro -> libro.idioma().equalsIgnoreCase(idioma))
                .collect(Collectors.toList());

        if (librosPorIdioma.isEmpty()) {
            System.out.println("\nNo se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("\nListado de libros en el idioma: " + idioma);
            librosPorIdioma.forEach(libro -> {
                System.out.println("Título: " + libro.titulo());
                System.out.println("Autor: " + libro.autor());
                System.out.println("Número de descargas: " + libro.numeroDeDescargas());
                System.out.println("-----------------------------");
            });
        }
    }
}
