package com.lovelymoda.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente {
	private ObservableList<Venta> ventas = FXCollections.observableArrayList();
	private int idCliente;
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;

    // Constructores
    public Cliente(int idCliente, String dni, String nombre, String apellido, String d, String telefono,
            String correo) {
    	this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = d;
        this.telefono = telefono;
        this.correo = correo;
        this.ventas.clear();
    }

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void setVenta(Venta vta) {
    	this.ventas.add(vta);
    }
    
    public ObservableList<Venta> getVentas(){
    	return this.ventas;
    }
}
