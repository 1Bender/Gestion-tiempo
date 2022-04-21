package com.example.gestiontareas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase controlador de vista.
 */
public class ContorladorNuevaAlarma{

    @FXML
    private TextField txtIdentAlarma;
    @FXML
    private CheckBox checkSonido;
    @FXML
    private DatePicker datFecha;
    @FXML
    private TextField txtTextoAlarma;
    @FXML
    private Spinner<String> spHoras;
    @FXML
    private Spinner<String> spMinutos;

    private Alarma alarma;

    /**
     * Método get para la variable alarma.
     * @return
     */
    public Alarma getAlarma() {
        return alarma;
    }


    /**
     * Método que es llamado automaticamente al crear la ventana e inicia los componentes.
     */
    public void initialize(){

        spinTiempo();
        displayDatePicker();
        formatoPicker(datFecha);

    }



    /**
     * Método para modificar el DatePicker con un dayCellFactory. En este caso modificamos que se deshabiliten
     * fechas posteriores a la actual y ponemos de color verde los fines de semana.
     */
    public void displayDatePicker(){

        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {

                /*Desahabilitamos las fechas que ya han pasado*/
                if (item.isBefore(LocalDate.now())) {
                    this.setDisable(true);
                }

                /*Coloreamos fines de semana en verde*/
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.GREEN);

                }
            }
        };
        datFecha.setDayCellFactory(dayCellFactory);
    }



    /**
     * Método para poblar el spin con el tipo de datos que necesitemos.
     */
    public void spinTiempo(){

        /*Como queremos ceros, poblamos manualmente por pura simplicitud*/
        ObservableList<String> lista = FXCollections.observableArrayList("00","01","02","03","04","05","06","07",
                "08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");

        /*Introducimos datos al spinner*/
        SpinnerValueFactory<String> valores = new SpinnerValueFactory.ListSpinnerValueFactory<String>(lista);
        spHoras.setValueFactory(valores);

        /*Poblamos los números que necesitamos con cero y el resto en bucle for*/
        ObservableList<String> listah = FXCollections.observableArrayList("00","01","02","03","04","05","06","07",
                "08","09","10");

        /*Terminamos de poblar la lista con todos los minutos*/
        for (int i=11; i<=59; i++){
            listah.add(String.valueOf(i));
        }
        /*Introducimos datos al spinner*/
        SpinnerValueFactory<String> valoresh = new SpinnerValueFactory.ListSpinnerValueFactory<String>(listah);
        spMinutos.setValueFactory(valoresh);
    }


    /**
     * Método que recoge los datos y fabrica un objeto alarma.
     */
    @FXML
    public void crearAlarma(ActionEvent e){

        /*Nos aseguramos recibir datos del DatePicker*/
        if(datFecha.getValue()!=null) {
            /*Recogemos datos*/
            Boolean music = checkSonido.isSelected();
            String fecha = datFecha.getValue().toString() + " " + spHoras.getValue() + ":" + spMinutos.getValue();
            String mensaje = txtTextoAlarma.getText();
            LocalDateTime timeAlarma = fabricarFecha(fecha);
            String id = txtIdentAlarma.getText();

            /*Se crea el objeto alarma*/
            Alarma nuevaAlarma = new Alarma(mensaje, timeAlarma, music, id);

            /*Guardamos el objeto*/
            this.alarma = nuevaAlarma;

        }
        /*Cerramos ventana*/
        cerrarVentana(e);
    }

    /**
     * Método para fabricar una objeto fecha LocalDateTime con datos String.
     * @param fecha
     * @return
     */
    public LocalDateTime fabricarFecha(String fecha){

        DateTimeFormatter forma = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime momento = LocalDateTime.parse(fecha, forma);
        return momento;
    }

    /**
     * Método para modificar el formato del DatePicker.
     * @param picker
     */
    public void formatoPicker(DatePicker picker){

        /*Modificamos el formato de salida para facilitar la comparación con la fecha del sistema*/
        DateTimeFormatter formatoSist = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        picker.setConverter(new LocalDateStringConverter(formatoSist, null));

    }

    /**
     * Método para cerrar una ventana.
     * @param event
     */
    public void cerrarVentana(ActionEvent event){

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }



}
