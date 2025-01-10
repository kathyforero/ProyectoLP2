package Clases;

import Bases.*;
import java.io.File;
import java.io.Serializable;

public class Auto implements Serializable {
    private float precio;
    private MarcaDeAuto marca;
    private String modelo;
    private Tipo tipo;
    private int año;
    private String placa;
    private int kilometraje;
    private Motor motor;
    private Transmision transmisión;
    private float peso;
    private Ubicacion ubicacion;
    private Estado estado;
    private Usuario usuario;
    private DoublyCircularList<File> fotos;
    private ArrayList<Reporte> reportes; // Se usa un ArrayList de la clase Reporte que creamos para almacenar los múltiples reportes que puede tener un auto.
    private static final long serialVersionUID = 2004140222041502L;

    public Auto(float precio, MarcaDeAuto marca, String modelo, Tipo tipo, int año, String placa, int kilometraje,
            Motor motor, Transmision transmisión, float peso, Ubicacion ubicacion, Usuario usuario, Estado estado,
            DoublyCircularList<File> fotos) {
        this.precio = precio;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.año = año;
        this.kilometraje = kilometraje;
        this.motor = motor;
        this.transmisión = transmisión;
        this.peso = peso;
        this.ubicacion = ubicacion;
        this.usuario = usuario;
        this.estado = estado;
        this.placa = placa;
        this.fotos = fotos;
    }

    public float getPrecio() {
        return precio;
    }

    public MarcaDeAuto getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getAño() {
        return año;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public Motor getMotor() {
        return motor;
    }

    public Transmision getTransmisión() {
        return transmisión;
    }

    public float getPeso() {
        return peso;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public DoublyCircularList<File> getFotos() {
        return fotos;
    }

    public ArrayList<Reporte> getReportes() {
        return reportes;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setMarca(MarcaDeAuto marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public void setTransmisión(Transmision transmisión) {
        this.transmisión = transmisión;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFotos(DoublyCircularList<File> fotos) {
        this.fotos = fotos;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setReportes(ArrayList<Reporte> reportes) {
        this.reportes = reportes;
    }

    @Override
    public String toString() {
        return "Auto [precio=" + precio + ", marca=" + marca + ", modelo=" + modelo + ", tipo=" + tipo + ", año=" + año
                + ", placa=" + placa + ", kilometraje=" + kilometraje + ", motor=" + motor + ", transmisión="
                + transmisión + ", peso=" + peso + ", ubicacion=" + ubicacion + ", estado=" + estado + ", usuario="
                + usuario + "]";
    }

}
