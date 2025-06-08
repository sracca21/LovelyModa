package com.lovelymoda.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.lovelymoda.models.*;

public class DocVentaHTML {

    public static void generarDocVenta(Cliente cliente, Venta venta, String archivoHTML) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoHTML))) {
            writer.write("<html><head><meta charset='UTF-8'><title>Factura</title></head><body>");
            writer.write("<h1 style='text-align:center;'>DOCUMENTO DE VENTA</h1>");
            writer.write("<h3>Datos del Cliente</h3>");
            writer.write("<p><strong>Nombre:</strong> " + cliente.getNombre() + " " + cliente.getApellido() + "</p>");
            writer.write("<p><strong>DNI:</strong> " + cliente.getDni() + "</p>");
            writer.write("<p><strong>Dirección:</strong> " + cliente.getDireccion() + "</p>");
            writer.write("<p><strong>Teléfono:</strong> " + cliente.getTelefono() + "</p>");
            writer.write("<p><strong>Correo:</strong> " + cliente.getCorreo() + "</p>");

            writer.write("<h3>Productos</h3>");
            writer.write("<ul>");
            for (Producto p : Venta.productos) {
                writer.write("<li>" + p.getDescripcion() + " - Cant: " + p.getCantidad()
                        + " - Precio: $" + p.getPrecio() + " - Subtotal: $" + p.getSubtotal() + "</li>");
            }
            writer.write("</ul>");
            writer.write("<h3>Total: $" + String.format("%.2f", venta.getMonto()) + "</h3>");
            writer.write("</body></html>");

            System.out.println("Factura HTML generada en: " + archivoHTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
