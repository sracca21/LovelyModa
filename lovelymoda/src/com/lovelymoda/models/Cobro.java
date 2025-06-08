package com.lovelymoda.models;
import java.time.LocalDate;
import java.time.LocalTime;

public class Cobro{
	private LocalDate fecha;
    private LocalTime hora;
	private float monto;
	private String moneda;
	private int nromovcont;
    
    
	public Cobro() {
		this.fecha = LocalDate.now(); //Fecha del sistema
        this.hora = LocalTime.now();  //Hora del sistema
        this.moneda = "ARS";
	}
	
	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	public float getMonto() {
		return this.monto;
	}
	
	public int getNroMovCont() {
		return this.nromovcont;
	}
	
	public void setNroMovCont(int nroMovCont) {
		this.nromovcont = nroMovCont;
	}
	
}