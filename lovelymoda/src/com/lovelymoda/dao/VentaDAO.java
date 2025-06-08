package com.lovelymoda.dao;

import com.lovelymoda.db.*;
import com.lovelymoda.models.*;
import java.sql.*;

import com.lovelymoda.utils.*;

public class VentaDAO {
    
	public Util msg = new Util();
	
    public void agregarVenta(Venta venta, Cliente cliente){
    	msg.mostrarMensaje("Registrar Venta en BD");
    }

}