package com.example.anacristina.lectorlibros;

/**
 * Created by AnaCristina on 19/01/2018.
 */

public class Libro {

    private String titulo;
    private String texto;

    public Libro() {
    }

    public Libro(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
