package com.lovelymoda.models;

import java.util.Optional;

import com.lovelymoda.utils.Util;

import javafx.scene.control.TextInputDialog;

public class VentaCredito extends Venta {
    public static Util msg = new Util();
    String tipoVenta;
    String formulario;
    Cobro cobro;
    
    public VentaCredito() {
    	tipoVenta = "CREDITO";
    	this.clearCobro();
    	this.clearProducto();
    }
    
    @Override
    public void registrarVenta() {
        //msg.mostrarMensaje("Venta Credito");
        formulario = "/com/lovelymoda/view/Items.fxml";
    	msg.loadForm(formulario,"Registrar Venta"); 
    }
    
    @Override
    public Venta getVenta() {
        return this;
    }
    
    @Override
    public void registrarCobro() {
    	//msg.mostrarMensaje("Registrar Cobro");
    	for (int i = 1; i <= this.getCuotas(); i++) {
    		Cobro cobro = new Cobro();
            cobro.setMonto(this.getMontoCuota());
            this.agregarCobro(cobro);
    	}
        
    }
    
    @Override
    public String getTipoVenta() {
        return this.tipoVenta;
    }
        
    @Override
    public void calcularCuotas() {
    	//msg.mostrarMensaje("Cuotas: " + String.valueOf(getCuotas()) + " Interes: " + String.valueOf(getInteres()));
    	double nro = Math.pow(this.getInteres(), this.getCuotas());
    	double montoCuota = (this.getMonto() * (1 + nro / 100)) / this.getCuotas();
    	msg.mostrarMensaje("Monto de cada Cuota: " + String.format("%.2f", montoCuota));
    	this.setMontoCuota(montoCuota);
    }
    
    
}