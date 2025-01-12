package com.aluracursos.challenges.literalura.principal;

import com.aluracursos.challenges.literalura.model.Datos;
import com.aluracursos.challenges.literalura.model.DatosLibros;
import com.aluracursos.challenges.literalura.service.ConsumoAPI;
import com.aluracursos.challenges.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    public void mostrarTop10Libros() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        System.out.println("\nTop 10 libros más descargados:");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

    public void buscarLibroPorTitulo(String tituloLibro) {
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\nLibro encontrado:");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("\nLibro no encontrado.");
        }
    }

    public void mostrarEstadisticas() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));

        System.out.println("\nEstadísticas de descargas:");
        System.out.println("Promedio: " + est.getAverage());
        System.out.println("Máximo: " + est.getMax());
        System.out.println("Mínimo: " + est.getMin());
        System.out.println("Cantidad de libros evaluados: " + est.getCount());
    }
}