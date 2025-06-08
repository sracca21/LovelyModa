package com.lovelymoda.models;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.lovelymoda.models.Inventario;
import com.lovelymoda.utils.*;

public class DocInventarioHTML {
	private static float valorTotal;
	private static Util msg = new Util();
	
	public static void generarReporteInventario(List<Inventario> inventarios, String path) {
		valorTotal = Lovelymoda.getTotalInventario();
		
		//msg.mostrarMensaje(String.valueOf(valorTotal));
		
		//Ordernar descendente por monto (pxq)------------------------------------------------------------
        inventarios.sort((a, b) -> {
            float montoClaseA = a.getPrecio() * a.getStock();
            float montoClaseB = b.getPrecio() * b.getStock();
            return Float.compare(montoClaseB, montoClaseA);
        });
        
        //clasificacion y corte----------------------------------------------------------------------------
        float acumulado = 0;
        float limite = 0.8f * valorTotal;
        int claseACorte = 1;
        for (Inventario inv : inventarios) {
            float valor = inv.getPrecio() * inv.getStock();
            if (acumulado + valor <= limite) {
                acumulado += valor;
                claseACorte++;
            } else {
                break;
            }
        }
        
        //Armado de reporte HTML---------------------------------------------------------------------------
        StringBuilder html = new StringBuilder();
		html.append("<html><head><meta charset='UTF-8'>");
		html.append("<title>Reporte de Inventario</title>");
		html.append("<style>");
		html.append("body { font-family: Arial; background-color: #f9f9f9; padding: 20px; }");
		html.append("h2 { color: #333; }");
		html.append("table { width: 100%; border-collapse: collapse; }");
		html.append("th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }");
		html.append("th { background-color: #e0e0e0; }");
		html.append(".claseA { background-color: #d4edda; }"); //CLase A
		html.append(".claseB { background-color: #d1ecf1; }"); //Clase B
		html.append("</style></head><body>");

        html.append("<h2>Reporte de Inventario (Clasificación Pareto)</h2>");
        html.append("<table>");
        html.append("<tr><th>Descripción</th><th>Stock</th><th>Precio</th><th>Valor Total</th><th>Clase</th></tr>");
   
        for (int i = 0; i < inventarios.size(); i++) {
            Inventario inv = inventarios.get(i);
            float valor = inv.getPrecio() * inv.getStock();
            String clase = (i < claseACorte) ? "A" : "B";
            String rowClass = (clase.equals("A")) ? "claseA" : "claseB";

            html.append("<tr class='").append(rowClass).append("'>")
                .append("<td>").append(inv.getDescripcion()).append("</td>")
                .append("<td>").append(inv.getStock()).append("</td>")
                .append("<td>").append(String.format("%.2f", inv.getPrecio())).append("</td>")
                .append("<td>").append(String.format("%.2f", valor)).append("</td>")
                .append("<td>").append(clase).append("</td>")
                .append("</tr>");
        }
        //linea final con Total--------------------------------------------------------------------
        html.append("<tr>")
	        .append("<td></td>")
	        .append("<td></td>")
	        .append("<td></td>")
	        .append("<td>").append(String.format("%.2f", valorTotal)).append("</td>")
	        .append("<td></td>")
	        .append("</tr>");
        html.append("</table>");
        //referencias-------------------------------------------------------------------------------
        html.append("<p><strong>Clase A:</strong> productos que representan el 80% o más del valor del inventario.</p>");
        html.append("<p><strong>Clase B:</strong> productos restantes.</p>");
        html.append("</body></html>");

        //escribir html--------------------------------------------------------------------------------
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(html.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
