package com.lovelymoda.dao;

import com.lovelymoda.db.*;
import com.lovelymoda.models.*;
import java.sql.*;


public class ClienteDAO {
    
    public int agregarCliente(Cliente cliente) {
        String sql_persona = "INSERT INTO Persona (idPersona, dni, nombre, apellido, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sql_cliente = "INSERT INTO Cliente (idCliente, Persona_idPersona) VALUES (?, ?)";
        
        		
        //Creo la conexion-------------------------------------------------------------------------
    	DBConnection dbConn = new DBConnection();
    	
        try (Connection conn = dbConn.getConnection()) {
        	conn.setAutoCommit(false);
        	
        	try(PreparedStatement stmt_persona = conn.prepareStatement(sql_persona);
                PreparedStatement stmt_cliente = conn.prepareStatement(sql_cliente)){
        	
	        	int idPersona = Lovelymoda.idPersona + 1;
	        	int idCliente = cliente.getIdCliente();
	        	stmt_persona.setInt(1, idPersona);
	            stmt_persona.setString(2, cliente.getDni());
	            stmt_persona.setString(3, cliente.getNombre());
	            stmt_persona.setString(4, cliente.getApellido());
	            stmt_persona.setString(5, cliente.getDireccion());
	            stmt_persona.setString(6, cliente.getTelefono());
	            stmt_persona.setString(7, cliente.getCorreo());
	            stmt_persona.executeUpdate();
	
	            stmt_cliente.setInt(1, idCliente);
	            stmt_cliente.setInt(2, idPersona);
	            stmt_cliente.executeUpdate();
	            
	            
	            conn.commit();
	            Lovelymoda.idCliente= idCliente;
	            Lovelymoda.idPersona= idPersona;
	            System.out.println(conn.getMetaData().getURL());
	            
	            return 0;
	            
        	} catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

}