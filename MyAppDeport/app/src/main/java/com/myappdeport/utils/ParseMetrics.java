package com.myappdeport.utils;

import android.util.Log;

public class ParseMetrics {

    public static double Km2Mill(double distance){
        return distance / 1.609;
    }
    public static double Mill2Km(double distance){
        return distance * 1.609;
    }
    public static double Km2Pie(double distance){
        return distance * 3281;
    }
    public static double Pie2Km(double distance){
        return distance / 3281;
    }
    public static double Pie2Mill(double distance){
        return distance / 5280;
    }
    public static double Mill2Pie(double distance){
        return distance * 5280;
    }
    public static double mtoKm(double distance){return  distance/1000; }
    public static long TimeMile2MSeg(long time){
       return  time / 1000;
    }
    public static long TimeSeg2Min(long time){
        return time / 60;
    }
    public static long TimeMin2Hours(long time){
        return time / 60;
    }
    /**
     * Function to convert milliseconds time to Timer Format
     * Hours:Minutes:Seconds
     * */
    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) { finalTimerString = hours + ":"; }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) { secondsString = "0" + seconds; }
        else { secondsString = "" + seconds; }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        // return timer string
        return finalTimerString;
    }
    /*
    * Function calcule duration time in format 00:00:00
    * */
    public static String Duration(int time) {
        int minutes = ((time % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (((time % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
        String d;
        if (seconds > 0 && seconds < 10) {
            d = "0" + minutes + ":0" + seconds;
        } else
            d = "0" + minutes + ":" + seconds;
        return d;
    }

    public static double rhythm(double distance,long time){
        return distance/time;
    }
    /*
    ESTE METODO ESTA EN CONTRUCCION - RECORDAR QUE SE PUEDE CALCULAR CON UNA TABLA O SINO UN APROX
    tiempo en minutos, peso en kilogramos
    rhythm : km/h
     */
    public static double kCal(double peso, double tiempo, double rhythm){
        return 0;
    }
}
