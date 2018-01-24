package com.example.anacristina.lectorlibros;

/**
 * Created by AnaCristina on 19/01/2018.
 */

public class Libro {

    private String titulo;
    private String texto;

    // Constructor vacío:
    public Libro() {
    }

    // Constructor:
    public Libro(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    // Método que permite recuperar el título de un libro:
    public String getTitulo() {
        return titulo;
    }

    // Método que permite modificar el título de un libro:
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Método que permite recuperar el texto de un libro:
    public String getTexto() {
        return texto;
    }

    // Método que permite modificar el texto de un libro.
    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Método que devuelve el título del libro cuando visualizamos un objeto de esta clase.
    @Override
    public String toString() {
        return titulo;
    }

}
