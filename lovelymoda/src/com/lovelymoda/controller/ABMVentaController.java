package com.lovelymoda.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.*;

import java.util.Optional;

import com.lovelymoda.models.*;
import com.lovelymoda.utils.Util;

public class ABMVentaController {
	public Cliente cliente;
	public String formulario = "";

    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn<Venta, Integer> colId;
    @FXML private TableColumn<Venta, String> colFecha;
    @FXML private TableColumn<Venta, String> colHora;
    @FXML private TableColumn<Venta, String> colMoneda;
    @FXML private TableColumn<Venta, String> colCliente;
    @FXML private TableColumn<Venta, String> colCuotas;
    @FXML private TableColumn<Venta, String> colTipo;
    @FXML private TableColumn<Venta, String> colMonto;
    @FXML private TableColumn<Venta, String> colMontoCuota;

    private Venta venta;
    public Util msg = new Util();
    private ObservableList<Venta> ventas = Lovelymoda.ventas;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdVenta()).asObject());
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFecha()));
        colHora.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHora()));
        colMonto.setCellValueFactory(data -> {
            double valor = data.getValue().getMonto();
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", valor));
        });
        colMoneda.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMoneda()));
        colCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getIdCliente())));
        colCuotas.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getCuotas())));
        colMontoCuota.setCellValueFactory(data -> {
            double valor = data.getValue().getMontoCuota();
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", valor));
        });
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipoVenta()));

        colId.setCellFactory(tc -> new TableCell<Venta, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.toString());
            }
        });

        cargarVentas();
    }

    private void cargarVentas() {
        tablaVentas.setItems(ventas);
    }

    // Alta Cliente
    @FXML
    private void onAltaVenta() {
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
    private void onEliminar() {}

    @FXML
    private void onModificar() {}

    @FXML
    private void onListado() {}

    @FXML
    private void onBuscar() {}

    @FXML
    private void onAyuda() {}

    @FXML
    private void onSalir() {
        tablaVentas.getScene().getWindow().hide();
    }
}

