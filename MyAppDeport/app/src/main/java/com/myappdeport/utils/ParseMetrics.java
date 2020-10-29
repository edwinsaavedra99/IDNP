package com.myappdeport.utils;

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

    public static long TimeMile2MSeg(long time){
       return  time / 1000;
    }
    public static long TimeSeg2Min(long time){
        return time / 60;
    }
    public static long TimeMin2Hours(long time){
        return time / 60;
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
