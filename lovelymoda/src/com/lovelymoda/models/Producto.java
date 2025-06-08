package com.lovelymoda.models;

public class Producto {
	
	private int idProducto;
	private String descripcion;
	private int cantidad;
	private float precio;
	private float subtotal;
	private String caracteristicas;
	
	// Constructor
	public Producto() {
		
	}
	
	public Producto(int idProducto, String descripcion, int cantidad, float precio, String caracteristicas) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.subtotal = precio * cantidad;
		this.descripcion = descripcion;
		this.caracteristicas = caracteristicas;
	}
	
	public int getIdProducto() {
		return this.idProducto;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public float getPrecio() {
		return this.precio;
	}
	
	public float getSubtotal() {
		return this.subtotal;
	}
	
	public String getCaracteristicas() {
		return this.caracteristicas;
	}
	
}