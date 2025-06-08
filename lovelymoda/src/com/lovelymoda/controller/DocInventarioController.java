package com.lovelymoda.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.File;

public class DocInventarioController {

    @FXML
    private WebView webView;
    private String archivoDocInventario;

    public void initialize() {
       
    }

    public void cargarDocInventario(String rutaArchivo) {
        this.archivoDocInventario = rutaArchivo;
        WebEngine engine = webView.getEngine();
        File file = new File(archivoDocInventario);
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
