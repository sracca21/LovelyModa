package com.lovelymoda.controller;


import com.lovelymoda.models.Cliente;
import com.lovelymoda.models.Lovelymoda;
import com.lovelymoda.dao.*;
import com.lovelymoda.utils.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AltaClienteController {
	private ObservableList<Cliente> clientes = Lovelymoda.clientes;
	private ClienteDAO dao = new ClienteDAO();
	private int intR = 1;
	private Util msg = new Util();
	private String formulario;
	private Cliente cliente;
	
	public AltaClienteController() {
		
	}

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
    public void onGuardarCliente() {
        
    		int idCliente = Lovelymoda.idCliente + 1;
    		Cliente cliente = new Cliente(
                idCliente,
                dniField.getText(),
                nombreField.getText(),
                apellidoField.getText(),
                direccionField.getText(),
                telefonoField.getText(),
                correoField.getText()
    			);
    		
    		intR = dao.agregarCliente(cliente);
    		if (intR == 0) {
    			// agrego el cliente nuevo al array del sistema
    			clientes.add(cliente);
    			Lovelymoda.cliente = cliente;
	            // Mostrar mensaje de éxito
    			msg.mostrarMensajeCust("Cliente guardado exitosamente.", "Exito");
    			if (Lovelymoda.procesoVenta) {
    				Lovelymoda.procesoVenta = false;
    				this.irVentasCliente();
    			}
    			
    		}else {
    			// Mostrar mensaje de fracaso
    			msg.mostrarMensajeCust("El Cliente no se guardó correctamente.", "Error no esperado");
    		};
    		//cierro la ventana activa
            dniField.getScene().getWindow().hide();
    }

    @FXML
    public void onCancelar() {
        dniField.getScene().getWindow().hide();
    }
    public void irVentasCliente() {
	    // se recupera el cliente creado
		cliente = Lovelymoda.cliente;
	    if (cliente != null) {
	        // se muestra el cliente para proseguir con la venta
	        System.out.println("Cliente encontrado: " + cliente.getNombre());
	        formulario = "/com/lovelymoda/view/Cliente.fxml";
	    	msg.loadForm(formulario,"Cliente");
	        
	    }
    }
}
