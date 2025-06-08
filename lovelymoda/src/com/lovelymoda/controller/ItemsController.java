package com.lovelymoda.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Optional;

import com.lovelymoda.models.*;
import com.lovelymoda.utils.Util;
import javafx.scene.control.TextInputDialog;


public class ItemsController {

    @FXML private TableView<Producto> tablaItems;
    @FXML private TableColumn<Producto, Integer> colId, colCantidad;
    @FXML private TableColumn<Producto, String> colDescripcion, colCaracteristicas;
    @FXML private TableColumn<Producto, Float> colPrecio, colSubtotal;
    private int nId;
    private Producto producto;
    private Inventario inventario;
    public Util msg = new Util();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdProducto()).asObject());
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colCantidad.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCantidad()).asObject());
        colPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleFloatProperty(data.getValue().getPrecio()).asObject());
        colSubtotal.setCellValueFactory(data -> new javafx.beans.property.SimpleFloatProperty(data.getValue().getSubtotal()).asObject());
        colCaracteristicas.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCaracteristicas()));
        colDescripcion.setCellFactory(tc -> new TableCell<Producto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
        cargarProductos();
        nId = Lovelymoda.getLastProducto();
    }

    
    
    
    private void cargarProductos() {
    	Venta.productos.clear();
    	tablaItems.setItems(Venta.productos);
    }
    

    // Agregar Producto
    @FXML private void onAgregarProducto() {
    	inventario = Lovelymoda.buscarProducto();
    	if (inventario != null) {
    		// Crear un cuadro de diálogo para ingresar cantidad
        	TextInputDialog dialog = new TextInputDialog();
        	dialog.setTitle("Ingreso de Cantidad");
        	dialog.setHeaderText("Ingrese la cantidad");
        	dialog.setContentText("Cantidad:");

        	// Mostrar el cuadro y esperar la entrada del usuario
        	Optional<String> result = dialog.showAndWait();

        	if (result.isPresent()) {
        	    try {
	        		int cantidad = Integer.parseInt(result.get());
	        		int stock = inventario.getStock();
	        		if(cantidad > stock) {
	        			msg.mostrarMensajeCust("No hay cantidad suficiente en stock.", "Error de inventario");
	        		}else {
		        		float precio = inventario.getPrecio();
		        		float subtotal = precio * cantidad;
		        	    System.out.println("Cantidad ingresada: " + cantidad);
		        	    String str = inventario.getDescripcion() + " por " + cantidad + " Precio: " + precio + " Subtotal: " + subtotal + " Existencias: " + stock;
		        	    String rta = msg.ingresoSiNo("Confirma", str);
		        	    if(rta == "SI") {
		        	    	nId += 1;
		        	    	producto = new Producto(
		        	    			nId,
		        	    			inventario.getDescripcion(),
		        	    			cantidad,
		        	    			precio,
		        	    			inventario.getCaracteristicas()
		        	    			);
		        	    	Venta.productos.add(producto);
		        	    }
	        		}
	        	    
	        	    
        	    } catch (Exception e) {
                    e.printStackTrace();
                    msg.mostrarMensaje("La cantidad debe ser numerica.");
                }
        	}    
    	}
    }
    
    @FXML private void onModificarCantidad() {
    	
    	
    }
    @FXML private void onEliminarProducto() {}
    @FXML private void onFinalizarCompra() {
    	 float monto = Lovelymoda.venta.getMonto();
    	 String str = "Monto total de la compra: " + String.valueOf(monto) + " ARS";
    	 String rta = msg.ingresoSiNo("Confirma", str);
    	 if(rta == "SI") {
    		 tablaItems.getScene().getWindow().hide();
    		 Lovelymoda.finalizarCarrito();
    	 }
    }

    @FXML private void onAyuda() {}
    @FXML private void onSalir() { tablaItems.getScene().getWindow().hide(); }
}