package com.example.gestiontareas;

import java.time.LocalDateTime;
import java.util.Timer;



/**
 * Clase para representar un objeto alarma.
 */
public class Alarma {

    private String titulo;
    private String mensaje;
    private LocalDateTime horaYdiaAlarma;
    private Boolean sonido;
    private Timer time;



    /*Constructores*/
    public Alarma(String msj, LocalDateTime horaDia, Boolean music, String id){

        this.mensaje=msj;
        this.horaYdiaAlarma = horaDia;
        this.sonido = music;
        this.titulo = id;
        if(horaYdiaAlarma==null){
            horaYdiaAlarma = LocalDateTime.now();
        }
    }
    public Alarma(){};


    /*Getters y setters*/
    public String getMensaje() {

        return mensaje;
    }

    public void setMensaje(String mensaje) {

        this.mensaje = mensaje;
    }

    public LocalDateTime getHoraYdiaAlarma() {

        return horaYdiaAlarma;
    }

    public void setHoraYdiaAlarma(LocalDateTime horaYdiaAlarma) {

        this.horaYdiaAlarma = horaYdiaAlarma;
    }

    public Boolean getSonido() {

        return sonido;
    }

    public void setSonido(Boolean sonido) {

        this.sonido = sonido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Timer getTime() {
        return time;
    }

    public void setTime(Timer time) {
        this.time = time;
    }

    /*Sobreescritura del método, debido a que el ListView muestra en su display el resultado de este método. Así obtenemos
     mostrar los datos de los objetos Alarma en el ListView como queramos*/
    @Override
    public String toString(){

        /*Se crea la salida de datos de los objetos Alarma en el ListView*/
        String micadena = horaYdiaAlarma.toString().substring(8, 10);
        micadena = micadena + horaYdiaAlarma.toString().substring(4, 7) + " " + horaYdiaAlarma.toString().substring(11, 16)
        + " Motivo: " + titulo;


        return micadena;
    }



}
