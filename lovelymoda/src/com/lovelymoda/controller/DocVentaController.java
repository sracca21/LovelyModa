package com.lovelymoda.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.File;

public class DocVentaController {

    @FXML
    private WebView webView;

    private String archivoDocVenta;

    public void initialize() {
       
    }

    public void cargarDocVenta(String rutaArchivo) {
        this.archivoDocVenta = rutaArchivo;
        WebEngine engine = webView.getEngine();
        File file = new File(archivoDocVenta);
        engine.load(file.toURI().toString());
    }

    @FXML
    private void onImprimir(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(webView.getScene().getWindow())) {
            webView.getEngine().print(job);
            job.endJob();
        }
    }

    @FXML
    private void onSalir(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
