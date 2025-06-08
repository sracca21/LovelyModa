package com.lovelymoda.models;

import com.lovelymoda.utils.Util;

public class VentaCredito extends Venta {
    public static Util msg = new Util();
    String tipoVenta;
    String formulario;
    Cobro cobro;
    private int cuotas;
    private double montoCuota;
    private double interes;
    
    public VentaCredito() {
    	tipoVenta = "CREDITO";
    }
    
    @Override
    public void registrarVenta() {
        msg.mostrarMensaje("Venta Crédito");
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