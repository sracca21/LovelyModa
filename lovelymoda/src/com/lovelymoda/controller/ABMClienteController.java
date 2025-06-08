package com.lovelymoda.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.*;

import java.net.URL;
import java.util.Optional;

import com.lovelymoda.models.*;
import com.lovelymoda.utils.Util;


public class ABMClienteController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colDni, colNombre, colApellido, colCelular, colDireccion, colCorreo;
    
    private Cliente cliente;
    public Util msg = new Util();
    private ObservableList<Cliente> clientes = Lovelymoda.clientes;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdCliente()).asObject());
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colApellido.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getApellido()));
        colCelular.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        colDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDireccion()));
        colCorreo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCorreo()));

        colDni.setCellFactory(tc -> new TableCell<Cliente, String>() {
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
        cargarClientes();
    }

    
    
    
    private void cargarClientes() {
    	tablaClientes.setItems(clientes);
    }
    

    // Alta Cliente
    @FXML private void onAltaCliente() {
    	Lovelymoda.registrarCliente();
    }
    
    @FXML private void onEliminar() {
    	
    	
    }
    @FXML private void onModificar() {}
    @FXML private void onListado() {}
    @FXML private void onBuscar() {
    	Lovelymoda.buscarCliente();	
    }
    @FXML private void onAyuda() {}
    @FXML private void onSalir() { tablaClientes.getScene().getWindow().hide(); }
}
