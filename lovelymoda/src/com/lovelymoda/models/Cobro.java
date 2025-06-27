package com.lovelymoda.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Cobro{
	private LocalDate fecha;
    private LocalTime hora;
	private double monto;
	private String moneda;
	private int nromovcont;
    
    
	public Cobro() {
		this.fecha = LocalDate.now(); //Fecha del sistema
        this.hora = LocalTime.now();  //Hora del sistema
        this.moneda = "ARS";
	}
	
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public double getMonto() {
		return this.monto;
	}
	
	public int getNroMovCont() {
		return this.nromovcont;
	}
	
	public void setNroMovCont(int nroMovCont) {
		this.nromovcont = nroMovCont;
	}
	
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	public LocalTime getHora() {
		return this.hora;
	}
	
	public String getMoneda() {
		return this.moneda;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
}