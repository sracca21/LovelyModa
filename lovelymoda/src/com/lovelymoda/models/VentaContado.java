package com.lovelymoda.models;

import com.lovelymoda.utils.Util;

public class VentaContado extends Venta {
    public static Util msg = new Util();
    String formulario;
    Cobro cobro;
    String tipoVenta;
    
    public VentaContado() {
    	tipoVenta = "CONTADO";
    }
    
    @Override
    public void registrarVenta() {
        //msg.mostrarMensaje("Venta Contado");
        formulario = "/com/lovelymoda/view/Items.fxml";
    	msg.loadForm(formulario,"Registrar Venta"); 
    }
    
    @Override
    public Venta getVenta() {
        return this;
    }
    
    @Override
    public void registrarCobro() {
        Cobro cobro = new Cobro();
        float monto = this.getMonto();
        cobro.setMonto(monto);
        this.agregarCobro(cobro);
    }
    
    @Override
    public String getTipoVenta() {
        return this.tipoVenta;
    }
    
}