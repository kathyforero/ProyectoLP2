package Clases;

import java.io.Serializable;

/**
 *
 * @author Kathy
 */
public class Reporte implements Serializable {
    private String categoria;
    private String descripcion;
    private static final long serialVersionUID = 2004140222041502L;

    public Reporte(String categoria, String descripcion) {
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        String s = "[Categoría: " + categoria + ", Descripción: " + descripcion + "]";
        return s;
    }
}
