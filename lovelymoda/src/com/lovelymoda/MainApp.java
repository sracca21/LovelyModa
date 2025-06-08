package com.lovelymoda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @SuppressWarnings("exports")
	@Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lovelymoda/view/MainMenu.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lovely Moda");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

