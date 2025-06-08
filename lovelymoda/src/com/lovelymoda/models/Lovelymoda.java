package com.lovelymoda.models;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import com.lovelymoda.dao.*;
import com.lovelymoda.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import com.lovelymoda.utils.*;



public class Lovelymoda {
    
    public static ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    public static ObservableList<Venta> ventas = FXCollections.observableArrayList();
    public static ObservableList<Inventario> inventarios = FXCollections.observableArrayList();
    public static int idCliente = 0;
    public static int idPersona = 0;
    public static Cliente cliente;
    public static Inventario inventario;
    public static Boolean procesoVenta = false;
    public static Util msg = new Util();
    public static String formulario;
    public static Venta venta;
    public static double interes;
    public static VentaDAO vtaDB = new VentaDAO();
    
    // Constructor---------------------------------------------------------------------------------
    public Lovelymoda() {
    	//Creo la conexion-------------------------------------------------------------------------
    	DBConnection dbConn = new DBConnection();
    	
    	//levanto los clientes----------------------------------------------------------------------
    	String sql = "SELECT c.idCliente, p.dni, p.nombre, p.apellido, p.telefono, p.direccion, p.correo " +
                "FROM Cliente c JOIN Persona p ON c.Persona_idPersona = p.idPersona";
    	try (Connection conn = dbConn.getConnection();
    		Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

           clientes.clear();
           while (rs.next()) {
               Cliente cliente = new Cliente(
                   rs.getInt("idCliente"),
                   rs.getString("dni"),
                   rs.getString("nombre"),
                   rs.getString("apellido"),
                   rs.getString("direccion"),
                   rs.getString("telefono"),
                   rs.getString("correo")
               );
               clientes.add(cliente);
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       // Ultimo cliente---------------------------------------------------------------------------------
       sql = "SELECT MAX(idCliente) AS idCliente FROM Cliente";
       try (Connection conn = dbConn.getConnection();
    		Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

           while (rs.next()) {
               idCliente = rs.getInt("idCliente");
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       // Ultima Persona --------------------------------------------------------------------------------
       sql = "SELECT MAX(idPersona) AS idPersona FROM Persona";
       try (Connection conn = dbConn.getConnection();
    		Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

           while (rs.next()) {
               idPersona = rs.getInt("idPersona");
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       
       //levanto los productos----------------------------------------------------------------------
	   String sql_inventario = "SELECT idInventario, puntoPedido, loteMinimo, stock, descripcion, caracteristicas, precio FROM inventario";
	   try (Connection conn = dbConn.getConnection();
			  Statement stmt = conn.createStatement();
	          ResultSet rs = stmt.executeQuery(sql_inventario)) {
		      
		   	  inventarios.clear();
	         
		      while (rs.next()) {
	             Inventario inventario = new Inventario(
	                 rs.getInt("idInventario"),
	                 rs.getInt("puntoPedido"),
	                 rs.getInt("loteMinimo"),
	                 rs.getInt("stock"),
	                 rs.getString("descripcion"),
	                 rs.getString("caracteristicas"),
	                 rs.getFloat("precio")
	                 
	             );
	             inventarios.add(inventario);
	         }
	   } catch (SQLException e) {
	         e.printStackTrace();
	   }
	   //levanto parametros---------------------------------------------------------------------------
	   //Interes------------------------
	   String parametro = "Interes";
	   interes = getParametro(parametro);
       
    }


    // Gets--------------------------------------------------------------------------------
    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public static Cliente buscarClienteDni(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
            	cliente = c;
                return c;
            }
        }
        return null;
    }
    
    public static void registrarCliente() {
    	try {
    		formulario = "/com/lovelymoda/view/AltaCliente.fxml";
    		msg.loadForm(formulario,"Registrar Cliente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void buscarCliente() {
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
    	        // El cliente fue encontrado
    	        System.out.println("Cliente encontrado: " + cliente.getNombre());
    	        String formulario = "/com/lovelymoda/view/VerCliente.fxml";
    	    	msg.loadForm(formulario, "Buscar Cliente");
    	        
    	    } else {
    	        // No se encontró ningún cliente con ese DNI
    	        System.out.println("Cliente no encontrado.");
    	        // Crear un cuadro de informacion
    	        msg.mostrarMensaje("El cliente no existe.");
    	    }
    	    
    	    
    	} else {
    	    System.out.println("El usuario canceló el ingreso.");
    	}
    }
    
    
    public static void registrarVenta(String lvTipoVenta) {
        if (!lvTipoVenta.equals("")) {
        	if (lvTipoVenta.equals("CONTADO")) {
        		Lovelymoda.venta = new VentaContado();
            } else {
            	Lovelymoda.venta = new VentaCredito();
            }
            Lovelymoda.venta.registrarVenta();
        }
    }
    
    public static Inventario buscarProductoId(int codProducto) {
        for (Inventario i : inventarios) {
            if (i.getIdInventario() == codProducto) {
            	inventario = i;
                return i;
            }
        }
        return null;
    }
    
    public static Inventario buscarProducto(String descripcion) {
        for (Inventario i : inventarios) {
            if (i.getDescripcion() == descripcion) {
            	inventario = i;
                return i;
            }
        }
        return null;
    }
    
    public static Inventario buscarProducto() {
    	// Crear un cuadro de diálogo para ingresar codigo de producto
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Ingreso de Codigo de Producto");
    	dialog.setHeaderText("Ingrese el codigo del Producto");
    	dialog.setContentText("Codigo Producto:");
    	int codProducto;
    	// Mostrar el cuadro y esperar la entrada del usuario
    	Optional<String> result = dialog.showAndWait();

    	if (result.isPresent()) {
    	    String codProductostr = result.get();
    	    codProducto = Integer.parseInt(codProductostr);
    	    System.out.println("Producto Ingresado: " + codProductostr);
    	    
    	    inventario = Lovelymoda.buscarProductoId(codProducto);
    	    if (inventario != null) {
    	        // El producto fue encontrado
    	        System.out.println("Producto encontrado: " + inventario.getDescripcion());
    	        return inventario;
    	        
    	    } else {
    	        // No se encontró ningún producto
    	        System.out.println("Producto no encontrado.");
    	        // Crear un cuadro de informacion
    	        msg.mostrarMensaje("El producto no existe.");
    	    }
    	    
    	} else {
    	    System.out.println("El usuario canceló el ingreso.");
    	}
    	return null;
    }
    
    public static int getLastProducto() {
    	//Creo la conexion--------------------------------------------------------------------------------
    	DBConnection dbConn = new DBConnection();
    	int idProducto = 0;
    	// Ultima Producto --------------------------------------------------------------------------------
        String sql = "SELECT MAX(idProducto) AS idProducto FROM Producto";
        try (Connection conn = dbConn.getConnection();
     		Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                idProducto = rs.getInt("idProducto");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idProducto;
        
    }
    
    public static int getLastVenta() {
    	//Creo la conexion--------------------------------------------------------------------------------
    	DBConnection dbConn = new DBConnection();
    	int idVenta = 0;
    	// Ultima Venta --------------------------------------------------------------------------------
        String sql = "SELECT MAX(idVenta) AS idVenta FROM Venta";
        try (Connection conn = dbConn.getConnection();
     		Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                idVenta = rs.getInt("idVenta");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idVenta; 
    }
    
    public static int getLastMovCont() {
    	//Creo la conexion--------------------------------------------------------------------------------
    	DBConnection dbConn = new DBConnection();
    	int idMovimientoContable = 0;
    	// Ultima Venta --------------------------------------------------------------------------------
        String sql = "SELECT MAX(idMovimientoContable) AS idMovimientoContable FROM MovimientoContable";
        try (Connection conn = dbConn.getConnection();
     		Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	idMovimientoContable = rs.getInt("idMovimientoContable");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMovimientoContable; 
    }
    
    public static void finalizarCarrito() {
    	venta = Lovelymoda.venta.getVenta();
    	venta.setVenta();
    	String rta = msg.ingresoSiNo("Confirma", "Confirma finalizar venta?");
    	if(rta == "SI") {
    		venta.registrarCobro();
    		Object[] datos = venta.getDatosCobro();
    		int cuotas = (int) datos[0];
    		float monto = (float) datos[1];
    		String str = "";
    		if(venta.getTipoVenta() == "CONTADO") {
    			str = "Cobro: " + String.valueOf(cuotas) + " pago de " + String.valueOf(monto) + " " + venta.getMoneda();
    		}else {
    			
    			str = "Cobro: " + String.valueOf(cuotas) + " cuotas de " + String.valueOf(monto) + " " + venta.getMoneda();
    		}
    		rta = msg.ingresoSiNo("Confirma", str);
    		if(rta == "SI") {
    			finalizarVenta();
    		}
    	}
    }
    
    public static void finalizarVenta() {
    	//msg.mostrarMensaje("Finalizar Venta");
    	cliente.setVenta(venta);
    	venta.finalizarVenta();
    	//Actualizar BD
    	//vtaDB.agregarVenta(venta, cliente);
    	
    }
    
    public static double getParametro(String parametro) {
        // Conexion ---------------------------------------------------------------------    	
    	DBConnection dbConn = new DBConnection();
        double valor = 0;
        
        // Select------------------------------------------------------------------------
        String sql = "SELECT valor FROM Parametro p WHERE p.parametro = ?";

        try (Connection conn = dbConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parametro);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    valor = rs.getDouble("valor");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return valor;
    }
    
    public static float getTotalInventario() {
    	//Calcular total de inventario
        float valorTotal = 0;
        for (Inventario inv : inventarios) {
            valorTotal += inv.getPrecio() * inv.getStock();
        }
        return valorTotal;
    }
    
    public static void reporteInventario() {
    	//msg.mostrarMensaje("Ejecutar reporte de Inventario");
    	String fecha = Util.getFechaYYYYMMDD();
    	String file = "D:/Silvana/lovelymoda/resources/doc/ReporteInventario" + fecha + ".html";
    	DocInventarioHTML.generarReporteInventario(inventarios, file);
		String formulario = "/com/lovelymoda/view/DocInventario.fxml";
		String titulo = "Reporte de Inventario - Clasificacion de Pareto";
		msg.loadDocInventario(formulario, file, titulo);
    }
}