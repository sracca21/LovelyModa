package com.lovelymoda.controller;


import com.lovelymoda.models.Cliente;
import com.lovelymoda.models.Lovelymoda;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class VerClienteController {
	
	public VerClienteController() {
		
	}

	public void initialize() {	
    	Cliente cliente = Lovelymoda.cliente;
    	idField.setText(String.valueOf(cliente.getIdCliente()));
    	dniField.setText(cliente.getDni());
    	nombreField.setText(cliente.getNombre());
    	apellidoField.setText(cliente.getApellido());
    	direccionField.setText(cliente.getDireccion());
    	telefonoField.setText(cliente.getTelefono());
    	correoField.setText(cliente.getCorreo());
	}
	
	@FXML
    private TextField idField;
	
    @FXML
    private TextField dniField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField correoField;


    @FXML
    public void onSalir() {
        dniField.getScene().getWindow().hide();
    }
}