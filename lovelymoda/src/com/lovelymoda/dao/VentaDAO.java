package com.lovelymoda.dao;

import com.lovelymoda.db.*;
import com.lovelymoda.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.lovelymoda.utils.*;

public class VentaDAO {
    
	public Util msg = new Util();
	

    public void agregarVenta(Venta venta, Cliente cliente) {
        DBConnection dbConn = new DBConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConn.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Insert en tabla VENTA
            String sqlVenta = "INSERT INTO Venta (fecha, hora, monto, moneda, Cliente_idCliente, cuotas, montoCuota, tipoVenta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setTime(2, java.sql.Time.valueOf(LocalTime.now()));
            stmt.setDouble(3, venta.getMonto());
            stmt.setString(4, venta.getMoneda());
            stmt.setInt(5, cliente.getIdCliente());

            Object[] datosCobro = venta.getDatosCobro();
            int cuotas = (int) datosCobro[0];
            double montoCuota = (double) datosCobro[1];

            stmt.setInt(6, cuotas);
            stmt.setDouble(7, montoCuota);
            stmt.setString(8, venta.getTipoVenta());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }
            venta.setIdVenta(idVenta);
            rs.close();
            stmt.close();

            // Insert en tabla PRODUCTOS
            String sqlProducto = "INSERT INTO Producto (descripcion, cantidad, precio, subtotal, caracteristicas, Venta_idVenta, Venta_Cliente_idCliente, Venta_Producto_idProducto, Inventario_idInventario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlProducto);

            int indexProducto = 1;
            for (Producto p : Venta.productos) {
                Inventario inv = Lovelymoda.buscarProducto(p.getDescripcion());

                stmt.setString(1, p.getDescripcion());
                stmt.setInt(2, p.getCantidad());
                stmt.setFloat(3, p.getPrecio());
                stmt.setFloat(4, p.getSubtotal());
                stmt.setString(5, p.getCaracteristicas());
                stmt.setInt(6, idVenta);
                stmt.setInt(7, cliente.getIdCliente());
                stmt.setInt(8, indexProducto++);
                stmt.setInt(9, inv.getIdInventario());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();

            // Insert en tabla MOVIMIENTOCONTABLE
            String sqlMov = "INSERT INTO MovimientoContable (tipo, fecha, hora, monto, moneda) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlMov, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, "DEBE");
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setTime(3, java.sql.Time.valueOf(LocalTime.now()));

            double totalCobros = 0;
            for (Cobro c : Venta.cobros) {
                totalCobros += c.getMonto();
            }

            stmt.setDouble(4, totalCobros);
            stmt.setString(5, "ARS");
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            int idMovCont = 0;
            if (rs.next()) {
                idMovCont = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            // Insert en tabla COBRO
            String sqlCobro = "INSERT INTO Cobro (fecha, hora, monto, moneda, Venta_Producto_idProducto, MovimientoContable_idMovimientoContable, Venta_idVenta, Venta_Cliente_idCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlCobro);

            int indexCobro = 1;
            for (Cobro c : Venta.cobros) {
                stmt.setDate(1, java.sql.Date.valueOf(c.getFecha()));
                stmt.setTime(2, java.sql.Time.valueOf(c.getHora()));
                stmt.setDouble(3, c.getMonto());
                stmt.setString(4, c.getMoneda());
                stmt.setInt(5, indexCobro++);
                stmt.setInt(6, idMovCont);
                stmt.setInt(7, idVenta);
                stmt.setInt(8, cliente.getIdCliente());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();

            // Update en tabla INVENTARIO
            String sqlInventario = "UPDATE Inventario SET stock = stock - ? WHERE idInventario = ?";
            stmt = conn.prepareStatement(sqlInventario);
            for (Producto p : Venta.productos) {
                Inventario inv = Lovelymoda.buscarProducto(p.getDescripcion());
                stmt.setInt(1, p.getCantidad());
                stmt.setInt(2, inv.getIdInventario());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();

            conn.commit(); // Commitea
            
            // Mensaje de exito
            //msg.mostrarMensaje("Venta registrada correctamente.");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            msg.mostrarMensaje("Error al registrar venta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}