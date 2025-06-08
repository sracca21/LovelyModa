package com.lovelymoda.controller;


import com.lovelymoda.models.Cliente;
import com.lovelymoda.models.Lovelymoda;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.lovelymoda.utils.*;

public class CrearVentaClienteController {
	private Util msg = new Util();
	private String lvTipoVenta;
	
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

    public CrearVentaClienteController() {
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
    public void onCrearVenta() {
    	lvTipoVenta = msg.ingresoTipoVenta();
    	Lovelymoda.registrarVenta(lvTipoVenta); 
    	onCancelar();
    }

    @FXML
    public void onCancelar() {
        dniField.getScene().getWindow().hide();
    }
}
