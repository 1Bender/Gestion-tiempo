package com.example.gestiontareas;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase controlador de vista.
 */
public class ControladorGestionTareas {

    @FXML
    public ListView<Alarma> LVlista;



    /**
     * Método que abre una nueva ventana para introducir los datos de la alarma.
     * @param event
     */
    public void abrirNuevaAlarma(ActionEvent event) {
        try {
            /*Cargar la vista*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaNuevaAlarma.fxml"));

            /*Cargar la ventana padre*/
            Parent root = loader.load();

            /*Obtenemos el controlador encargado de la vista, y le pasamos nuestra lista observable que se compartira*/
            ContorladorNuevaAlarma cont = loader.getController();


            /*Abrimos la ventana*/
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(escena);
            stage.setTitle("Datos del nuevo aviso");

            /*Con esto esperaremos a que la ventana abierta se cierre*/
            stage.showAndWait();

            /*Recuperamos el objeto y lo introducimos en el ListView*/
            Alarma alarm = cont.getAlarma();

            /*Nos aseguramos que alarma no és null*/
            if (alarm != null) {
                introducirAlarma(alarm);
                Date date = LocalDateTimeToDate(alarm.getHoraYdiaAlarma());
                control(alarm, date);
            }


        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Método que muestra el mensaje de alarma.
     * @param a
     */
    public void mostrar(Alarma a) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alarma");
        alert.setContentText(a.getMensaje());
        alert.showAndWait();

    }

    /**
     * Método para añadir items al ListView
     * @param alarma
     */
    public void introducirAlarma(Alarma alarma) {

        if (alarma != null) {
            LVlista.getItems().add(alarma);
        }

    }

    /**
     * Método para eliminar items del ListView
     * @param a
     */
    public void eliminarAlarma(Alarma a) {

        LVlista.getItems().remove(a);
    }

    /**
     * Conversor de LocalDateTime a Date.
     * @param h
     * @return Date
     */
    public Date LocalDateTimeToDate(LocalDateTime h) {
        LocalDateTime localDateTime = h;
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
        return date;
    }

    /**
     * Método que nos permite mostrar los datos de la alarma en el momento programado.
     *
     * @param a
     * @param momento
     */
    public void control(Alarma a, Date momento) {

        /*Nos aseguramos de no tener un objeto null*/
        if (a != null) {

            /*Se usa un TimerTask para programar que la tarea se ejecute en un momento determinado*/
            Timer time = new Timer();
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {

                /*Expresión lambda. Se usa runLater para que se ejecute en el subproceso de la
                aplicación con el resto de la interfaz de usuario.*/
                    Platform.runLater(() -> {

                        /*Controlamos la cariable booleana de sonido*/
                        if (a.getSonido()) {

                            /*Reproducimos sonido, el archivo esta en el proyecto*/
                            Path p = Paths.get("src/main/resources/sonidos/BELLBIKE.mp3");
                            Media media = new Media(p.toUri().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.play();
                        }

                        /*Llamada a los metodos que gestionana la alarma*/
                        mostrar(a);
                        eliminarAlarma(a);

                        /*Muy importante finalizar el hilo, de lo contrario queda activo*/
                        time.cancel();
                    });
                }
            }; /*Indicamos tarea y momento de ejecución*/
            time.schedule(tarea, momento);

            /*Guardamos el estado de la variable time como atributo del objeto, es importante pues es la manera
             en la que podremos eliminar o modificar la tarea posteriormente*/
            a.setTime(time);
        }
    }


    /**
     * Método que elimina la alarma del ListView y cancela el evento programado por Timer.
     */
    public void eliminacionListView() {

        /*Nos aseguramos de no tener un null. Se obtiene un null si se pulsa la papelera sin
        seleccionar ningún elemento del ListView */
        if(LVlista.getSelectionModel().getSelectedItem()!=null)
        {
        Alarma a = LVlista.getSelectionModel().getSelectedItem();
        Timer time = a.getTime();
        eliminarAlarma(a);
        time.cancel();
        }

    }

    /**
     * Método para crear una alarma de forma rápida que saltara en 30 min.
     */
    public void alarmaRapidaMedia() {

        LocalDateTime momentoAlarma = LocalDateTime.now().plusMinutes(30);
        Alarma a = new Alarma("Modo alarma rápida", momentoAlarma, false, "Alarma en 30min");
        introducirAlarma(a);
        Date date = LocalDateTimeToDate(a.getHoraYdiaAlarma());
        control(a, date);

    }

    /**
     * Método para crear una alarma de forma rápida que saltara en 1 hora.
     */
    public void alarmaRapidaHora() {

        LocalDateTime momentoAlarma = LocalDateTime.now().plusHours(1);
        Alarma a = new Alarma("Modo alarma rápida", momentoAlarma, false, "Alarma en 1h");
        introducirAlarma(a);
        Date date = LocalDateTimeToDate(a.getHoraYdiaAlarma());
        control(a, date);

    }


}