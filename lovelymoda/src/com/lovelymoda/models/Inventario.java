package com.lovelymoda.models;

import com.lovelymoda.utils.*;


public class Inventario {
	private int idInventario;
	private int puntoPedido;
	private int loteMinimo;
	private int stock;
	private String descripcion;
	private String caracteristicas;
	private float precio;
	private Util msg = new Util();
	// Constructor
	public Inventario() {
		
	}
	
	public Inventario(int idInventario, int puntoPedido, int loteMinimo, int stock, String descripcion, String caracteristicas, float precio) {
		this.idInventario = idInventario;
		this.puntoPedido = puntoPedido;
		this.loteMinimo = loteMinimo;
		this.stock = stock;
		this.descripcion = descripcion;
		this.caracteristicas = caracteristicas;
		this.precio = precio;
	}
	
	// Getters y Setters
	public void setIdInventario(int id) {
		this.idInventario = id;
	}
	
	public int getIdInventario() {
		return this.idInventario;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public float getPrecio() {
		return this.precio;
	}
	
	public int getStock() {
		return this.stock;
	}
	
	public String getCaracteristicas() {
		return this.caracteristicas;
	}
	
	public void actualizarInventario(int cantidad) {
		int stock = this.stock;
		stock = stock - cantidad;
		int pp = this.puntoPedido;
		if(pp >= stock) {
			String str = "Producto: " + this.descripcion + " Stock: " + String.valueOf(stock) + " PP: " + String.valueOf(pp);
			msg.mostrarMensajeCust(str, "Advertencia Punto de Pedido");
		}
		this.stock = stock;
	}

}