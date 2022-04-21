package com.example.gestiontareas;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class Logica extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Logica.class.getResource("VistaGestionTarea.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestión de tareas");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();


    }





















}