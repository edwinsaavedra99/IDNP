package com.examen02.question02.stadistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.examen02.question02.stadistics.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StadisticView extends View {

    private Paint pencil;
    private Paint pencilText;
    private Paint penceilLine;
    private int[] figureColour = {183, 149, 11};
    private LinearLayout layout;
    private DisplayMetrics metrics;
    private int alto = 0, ancho = 0;
    private ArrayList<Double> data = new ArrayList<Double>();
    private List<Double> listData;

    public StadisticView(Context context) {
        super(context);
    }

    public StadisticView(Context context, LinearLayout _layout, DisplayMetrics metrics, ArrayList<Double> data) {
        super(context);
        layout = _layout;
        this.metrics = metrics;
        this.data = new ArrayList<>(data);
        //Collections.sort(this.data);
        initialStyleFigure();
        initialStyleText();
        initialStyleFigurePoint();
        invalidate();
    }

    /**
     * Method initialStyleFigure define the parameters initials of the pencil Paint
     */
    public void initialStyleFigure() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, Util.color1[0], Util.color1[1], Util.color1[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.STROKE);
        pencil.setPathEffect(dashPathEffect);
    }//End Method

    public void initialStyleFigurePoint() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        penceilLine = new Paint();
        penceilLine.setAntiAlias(true);
        penceilLine.setARGB(250, Util.color3[0], Util.color3[1], Util.color3[2]);
        penceilLine.setStrokeWidth(7);
        penceilLine.setStyle(Paint.Style.STROKE);
        penceilLine.setPathEffect(dashPathEffect);
    }//End Method


    public void initialStyleText() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilText = new Paint();
        pencilText.setAntiAlias(true);
        pencilText.setARGB(250, Util.color2[0], Util.color2[1], Util.color2[2]);
        pencilText.setStrokeWidth(1);
        pencilText.setTextSize(20);
        pencilText.setStyle(Paint.Style.FILL);
        pencilText.setPathEffect(dashPathEffect);
    }//End Method


    /**
     * Method onDraw draw the figure geometric
     *
     * @param canvas area of draw
     */
    @SuppressLint("CanvasSize")
    protected void onDraw(Canvas canvas) {
        int margen_inferior = 400;
        int altoMax = metrics.heightPixels;
        int anchoMax = metrics.widthPixels;
        int margen_y = 50;
        int margen_x = 50;
        int alto_disponible = altoMax - margen_y * 2;
        int ancho_disponible = anchoMax - margen_x * 2;
        int cantidad_lineas = 10;
        int distancia_lineas = (alto_disponible - margen_inferior) / cantidad_lineas;

        double max_value_data = getMaxValue(data);
        double intervalo = max_value_data / (cantidad_lineas - 1);

        for (int i = 0; i < cantidad_lineas; i++) {
            int x = 10;
            int y = distancia_lineas * i;
            canvas.drawText(""+ Math.round(100*(max_value_data - (intervalo * i)))/100, x, y + margen_y, pencilText);
            canvas.drawLine(margen_x, y + margen_y, anchoMax - margen_x, y + margen_y, pencil);
        }

        int dias = 7;
        int distancia_dias = ancho_disponible / dias;
        for (int i = 0; i < dias; i++) {
            canvas.drawText("Dia " + (i + 1), margen_x + distancia_dias * i, distancia_lineas * cantidad_lineas + margen_y, pencilText);
        }
        int espacio_central_texto_dias = 25;
        int init_point = (cantidad_lineas - 1) * distancia_lineas + margen_y;

        double variable = (init_point - margen_y) / max_value_data;
        float prev_x = margen_x;
        float prev_y = init_point;
        for (int i = 0; i < dias; i++) {
            float x = i * distancia_dias + margen_x + espacio_central_texto_dias;
            float y = (float) (init_point - data.get(i) * (variable));
            Log.e("ERROR: ", "" + (init_point - data.get(i) * variable));
            canvas.drawPoint(x, y-8, penceilLine);
            canvas.drawText(""+data.get(i),x,y,pencilText);
            if (i != 0) {
                canvas.drawLine(prev_x, prev_y-8, x, y-8, pencil);
            }
            prev_x = x;
            prev_y = y;
        }
    }

    private double getMaxValue(ArrayList<Double> data) {
        double max = data.get(0);
        for (Double d : data) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public double supremoplus5() {
        System.out.println("maximoo : " + maxValue());
        return Math.floor(maxValue()) + 0.5;
    }

    public int maxValue() {
        double max = 0;
        int position = -1;
        for (int i = data.size() - 7; i < data.size(); i++) {
            if (this.data.get(i) >= max) {
                max = this.data.get(i);
                position = i;
            }
        }
        return position;

    }
}
