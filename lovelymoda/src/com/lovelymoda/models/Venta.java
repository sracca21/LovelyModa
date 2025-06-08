package com.lovelymoda.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;
import com.lovelymoda.utils.*;



public abstract class Venta {
	private LocalDate fecha;
    private LocalTime hora;
	private float monto;
	private String moneda;
	public static ObservableList<Producto> productos = FXCollections.observableArrayList();
	public static ObservableList<Cobro> cobros = FXCollections.observableArrayList();
	private Inventario inventario;
	private MovimientoContable movCont;
	private Util msg = new Util();
	
	public abstract void registrarVenta();
	public abstract void registrarCobro();
	public abstract Venta getVenta();
	public abstract String getTipoVenta();
	
	public void setVenta() {
        this.fecha = LocalDate.now(); //Fecha del sistema
        this.hora = LocalTime.now();  //Hora del sistema
        this.monto = this.getMonto(); //Suma los subtotales de los prod agreggados
        this.moneda = "ARS";
	}
	
	public float getMonto() {
    	float monto = 0;
    	for (Producto p : Venta.productos) {
            monto = monto + p.getSubtotal();
        }
    	return monto;
    }
	
	public String getMoneda() {
		return this.moneda;
	}
	
	public void agregarCobro(Cobro cobro) {
		cobros.add(cobro);
	}
	
	public Object[] getDatosCobro() {
	    int cuotas = cobros.size();
	    float monto = 0;
	    for (Cobro c : Venta.cobros) {
            monto = c.getMonto(); 
        }
	    return new Object[]{cuotas, monto};
	}
	
	public void finalizarVenta() {
		// Inventario----------------------------------------------------------------------
		for (Producto p : Venta.productos) {
            inventario = Lovelymoda.buscarProducto(p.getDescripcion());
            inventario.actualizarInventario(p.getCantidad());
        }
			// InventarioDAO
			// actualizar inventario en BD
		//Movimiento Contable -------------------------------------------------------------
		double total = 0;
		for (Cobro c : cobros) {
			total = total + c.getMonto();
        }
		movCont = new MovimientoContable();
		movCont.registrarMovimientoContable("DEBE", total);
			// MovimientoContableDAO
			// actualizar Movimiento en BD
		int idMovCont = Lovelymoda.getLastMovCont();
		for (Cobro c : Venta.cobros) {
			c.setNroMovCont(idMovCont);
        }
		Lovelymoda.ventas.add(getVenta());
		imprimirDocVenta();
    }
	
	public void imprimirDocVenta() {
		//String file = "D:/Silvana/lovelymoda/doc/prueba.html";
		String file = "D:/Silvana/lovelymoda/resources/doc/prueba.html";
		DocVentaHTML.generarDocVenta(Lovelymoda.cliente, this, file);
		String formulario = "/com/lovelymoda/view/DocVenta.fxml";
		String titulo = "Documento de Venta";
		msg.loadDocVenta(formulario, file, titulo);
	}
	
}