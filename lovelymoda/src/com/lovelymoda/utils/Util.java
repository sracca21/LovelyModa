package com.lovelymoda.utils;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import java.util.Optional;
import com.lovelymoda.controller.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util{
	public Util() {
		
	}
	
	//Mensaje de informacion
	public void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
	
	//mensaje custom
	public void mostrarMensajeCust(String mensaje, String titulo) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
				javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
	
	//levanta formulario de pantalla
	public void loadForm(String formulario, String titulo) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            URL cssUrl = getClass().getResource("/styles/estilo.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.out.println("No se encontró estilo.css en /styles/");
            }
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene); 
            stage.setTitle(titulo);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public String ingresoTipoVenta() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Tipo de Venta");
        dialog.setHeaderText("Debe seleccionar el tipo de venta.");

        // Crear botones
        ButtonType contadoButton = new ButtonType("Venta Contado", ButtonBar.ButtonData.OK_DONE);
        ButtonType creditoButton = new ButtonType("Venta Credito", ButtonBar.ButtonData.OTHER);

        dialog.getDialogPane().getButtonTypes().addAll(contadoButton, creditoButton);

        // Crear dialogo
        VBox content = new VBox();
        content.setSpacing(10);
        //content.getChildren().add(new Label("Ingrese Tipo de Venta:"));
        dialog.getDialogPane().setContent(content);

        // retorno
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == contadoButton) {
                return "CONTADO";
            } else if (dialogButton == creditoButton) {
                return "CREDITO";
            }
            return null;
        });
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
	}
	
	public String ingresoSiNo(String titulo, String texto) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(texto);

        // Crear botones
        ButtonType siButton = new ButtonType("Si", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.OTHER);

        dialog.getDialogPane().getButtonTypes().addAll(siButton, noButton);

        // Crear dialogo
        VBox content = new VBox();
        content.setSpacing(10);
        //content.getChildren().add(new Label("¿Los datos son Correctos?"));
        dialog.getDialogPane().setContent(content);

        // retorno
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == siButton) {
                return "SI";
            } else if (dialogButton == noButton) {
                return "NO";
            }
            return null;
        });
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
	}
	
	public void loadDocVenta(String formulario, String file, String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
	        Parent root = loader.load();
	
	        // Obtener controlador para el HTML
	     	DocVentaController controller = loader.getController();
	     	controller.cargarDocVenta(file); 
	        
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	
	        URL cssUrl = getClass().getResource("/styles/estilo.css");
	        if (cssUrl != null) {
	            scene.getStylesheets().add(cssUrl.toExternalForm());
	        } else {
	            System.out.println("No se encontró estilo.css en /styles/");
	        }
	        if (cssUrl != null) {
	            scene.getStylesheets().add(cssUrl.toExternalForm());
	        }
	
	        stage.setScene(scene); 
	        stage.setTitle(titulo);
	        stage.show();
		} catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	public void loadDocInventario(String formulario, String file, String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
	        Parent root = loader.load();
	
	        // Obtener controlador para el HTML
	     	DocInventarioController controller = loader.getController();
	     	controller.cargarDocInventario(file);
	        
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	
	        URL cssUrl = getClass().getResource("/styles/estilo.css");
	        if (cssUrl != null) {
	            scene.getStylesheets().add(cssUrl.toExternalForm());
	        } else {
	            System.out.println("No se encontró estilo.css en /styles/");
	        }
	        if (cssUrl != null) {
	            scene.getStylesheets().add(cssUrl.toExternalForm());
	        }
	
	        stage.setScene(scene); 
	        stage.setTitle(titulo);
	        stage.show();
		} catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	public static String getFechaYYYYMMDD() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return hoy.format(formatter);
    }
	
	public static String getFechaDMY() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return hoy.format(formatter);
    }
}