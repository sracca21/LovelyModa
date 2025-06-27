package com.lovelymoda.controller;

import java.util.Optional;

import com.lovelymoda.models.*;
import com.lovelymoda.utils.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;


public class MainMenuController {
	public ObservableList<Cliente> clientes = Lovelymoda.clientes;
	public Util msg = new Util();
	public Cliente cliente;
	public String formulario = "";
	// Constructor---------------------------------------------------------------------------------
	public MainMenuController() {
		Lovelymoda lmsys = new Lovelymoda();
		this.clientes = lmsys.getClientes();
	}
	
	
    @FXML
    private RadioButton radioInventario;

    @FXML
    private RadioButton radioMasVendidos;

    @FXML
    private RadioButton radioHistorico;

    @FXML
    public void abrirUsuarios() {
    	msg.mostrarMensaje("Abrir Usuarios");
    }

    @FXML
    public void abrirParametros() {
    	msg.mostrarMensaje("Abrir Parámetros");
    }

    @FXML
    public void abrirABMCliente() {
        String formulario = "/com/lovelymoda/view/ABMClientes.fxml";
    	msg.loadForm(formulario, "ABM Cliente");
    }

    @FXML
    public void abrirProductos() {
    	msg.mostrarMensaje("Abrir Productos");
    }

    @FXML
    public void abrirRegistrarVenta() {
    	// Crear un cuadro de diálogo para ingresar el DNI
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Ingreso de DNI");
    	dialog.setHeaderText("Ingrese el DNI del cliente");
    	dialog.setContentText("DNI:");

    	// Mostrar el cuadro y esperar la entrada del usuario
    	Optional<String> result = dialog.showAndWait();

    	if (result.isPresent()) {
    	    String dniIngresado = result.get();
    	    System.out.println("DNI ingresado: " + dniIngresado);
    	    
    	    cliente = Lovelymoda.buscarClienteDni(dniIngresado);
    	    if (cliente != null) {
    	        // El cliente fue encontrado se muestra para proseguir la venta
    	        System.out.println("Cliente encontrado: " + cliente.getNombre());
    	        formulario = "/com/lovelymoda/view/CrearVentaCliente.fxml";
    	    	msg.loadForm(formulario,"Crear Venta Cliente");
    	        
    	    } else {
    	        // No se encontró ningún cliente con ese DNI
    	        System.out.println("Cliente no encontrado.");
    	        // Crear un cuadro de informacion
    	        msg.mostrarMensaje("El cliente no existe.");
    	        Lovelymoda.procesoVenta = true;
    	        // solicita la creacion del cliente
    	        formulario = "/com/lovelymoda/view/AltaCliente.fxml";
    	    	msg.loadForm(formulario,"Alta Cliente");
    	    }
    	    
    	    
    	} else {
    	    System.out.println("El usuario canceló el ingreso.");
    	}
    }

    @FXML
    public void abrirVentas() {
    	//msg.mostrarMensaje("Abrir Ventas");
    	String formulario = "/com/lovelymoda/view/ABMVentas.fxml";
    	msg.loadForm(formulario, "Ventas");
    }

    @FXML
    public void abrirCobros() {
    	msg.mostrarMensaje("Abrir Cobros");
    }

    @FXML
    public void ejecutarReporte() {
        if (radioInventario.isSelected()) {
        	Lovelymoda.reporteInventario();
        } else if (radioMasVendidos.isSelected()) {
        	msg.mostrarMensaje("Ejecutar reporte de Productos más Vendidos");
        } else if (radioHistorico.isSelected()) {
        	msg.mostrarMensaje("Ejecutar reporte de Histórico de Ventas");
        } else {
        	msg.mostrarMensaje("Selecciona una opción de reporte.");
        }
    }

    @FXML
    public void mostrarAyuda() {
    	msg.mostrarMensaje("Mostrando ayuda...");
    }

    @FXML
    public void salirAplicacion() {
        System.exit(0);
    }

}
