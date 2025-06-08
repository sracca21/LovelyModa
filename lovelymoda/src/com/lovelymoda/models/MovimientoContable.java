package com.lovelymoda.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class MovimientoContable{
	private String tipo;
	private LocalDate fecha;
    private LocalTime hora;
	private double monto;
	private String moneda;
	
	public MovimientoContable() {
		this.fecha = LocalDate.now(); //Fecha del sistema
        this.hora = LocalTime.now();  //Hora del sistema
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	public LocalTime getHora() {
		return this.hora;
	}
	
	public double getMonto() {
		return this.monto;
	}
	
	public String getMoneda() {
		return this.moneda;
	}
	
	public void registrarMovimientoContable(String tipo, double monto) {
		this.tipo = tipo;
        this.monto = monto;
        this.moneda = "ARS";
	}
	
	
}