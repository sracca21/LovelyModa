package com.lovelymoda.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.lovelymoda.utils.*;



public abstract class Venta {
	private LocalDate fecha;
    private LocalTime hora;
	private double monto;
	private String moneda;
	public static ObservableList<Producto> productos = FXCollections.observableArrayList();
	public static ObservableList<Cobro> cobros = FXCollections.observableArrayList();
	private Inventario inventario;
	private MovimientoContable movCont;
	private Util msg = new Util();
	private int cuotas;
    private double montoCuota;
    private double interes;
    private int idVenta;
    private int idCliente;
	
	public abstract void registrarVenta();
	public abstract void registrarCobro();
	public abstract Venta getVenta();
	public abstract String getTipoVenta();
	public abstract void calcularCuotas();
	
	public void setVenta() {
        this.fecha = LocalDate.now(); //Fecha del sistema
        this.hora = LocalTime.now();  //Hora del sistema
        this.monto = this.getMonto(); //Suma los subtotales de los prod agreggados
        this.moneda = "ARS";
	}
	
	public double getMonto() {
    	double monto = 0;
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
	
	public void agregarProducto(Producto producto) {
		productos.add(producto);
	}
	
	public void clearCobro() {
		cobros.clear();
	}
	
	public void clearProducto() {
		productos.clear();
	}
	
	public Object[] getDatosCobro() {
	    int cuotas = cobros.size();
	    double monto = 0;
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
		String file = "D:/Silvana/lovelymoda/resources/doc/ventaNro_" + String.valueOf(this.idVenta) + ".html";
		DocVentaHTML.generarDocVenta(Lovelymoda.cliente, this, file);
		String formulario = "/com/lovelymoda/view/DocVenta.fxml";
		String titulo = "Documento de Venta";
		msg.loadDocVenta(formulario, file, titulo);
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
		
    public void setCuotas(int cuotas) {
    	this.cuotas = cuotas;
    }
    
    public void setMontoCuota(double montoCuota) {
    	this.montoCuota = montoCuota;
    }
    
    public void setInteres(double interes) {
    	this.interes = interes;
    }
    
    public int getCuotas() {
    	return this.cuotas;
    }
    
    public double getMontoCuota() {
    	return this.montoCuota;
    }
    
    public String getMontoCuotaStr() {
    	return String.format("%.2f", this.montoCuota);
    }
    
    public double getInteres() {
    	return this.interes;
    }
    
    public int getIdVenta() {
    	return this.idVenta;
    }
    
    public void setIdVenta(int idVenta) {
    	this.idVenta = idVenta;
    }
    
    public String getFecha() {
    	LocalDate fecha = this.fecha;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formatter);
    }
    
    public String getHora() {
        LocalTime hora = this.hora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return hora.format(formatter);
    }
    
    public int getIdCliente() {
    	return this.idCliente;
    }
    
    public void setIdCliente(int idCliente) {
    	this.idCliente = idCliente;
    }
    
    
}