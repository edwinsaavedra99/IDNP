package com.myappdeport.view.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


public class BarrasView extends View {

    private Paint pencil;
    private Paint pencilText;
    private Paint penceilLineinGraph;
    private DisplayMetrics metrics;
    private List<Double> data = new ArrayList<>();

    public BarrasView(Context context) {
        super(context);
    }

    public BarrasView(Context context, DisplayMetrics metrics, ArrayList<Double> data) {
        super(context);
        this.metrics = metrics;
        this.data =  data.subList(data.size()-7,data.size());
        initialStyleFigure();
        initialStyleText();
        initialStyleFigureLines();
        invalidate();
    }

    /**
     * Method initialStyleFigure define the parameters initials of the pencil Paint for background
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
    /**
     * Method initialStyleFigureLines define the parameters initials of the pencil Paint for Graph Bar
     */
    public void initialStyleFigureLines() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        penceilLineinGraph = new Paint();
        penceilLineinGraph.setAntiAlias(true);
        penceilLineinGraph.setARGB(250, Util.color2[0], Util.color2[1], Util.color2[2]);
        penceilLineinGraph.setStrokeWidth(6);
        penceilLineinGraph.setStyle(Paint.Style.FILL);
        penceilLineinGraph.setPathEffect(dashPathEffect);
    }//End Method

    public void initialStyleText() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilText = new Paint();
        pencilText.setAntiAlias(true);
        pencilText.setARGB(250, Util.color4[0], Util.color4[1], Util.color4[2]);
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
        int margen_inferior = 200;
        int altoMax = metrics.heightPixels;
        int anchoMax = metrics.widthPixels;
        int margen_y = 10;
        int margen_x = 120;
        int alto_disponible = altoMax - margen_y * 2;
        int ancho_disponible = anchoMax - margen_x * 2;
        int cantidad_lineas = 10;
        int distancia_lineas = (alto_disponible - margen_inferior) / cantidad_lineas-50;
        double max_value_data = getMaxValue(data);
        double intervalo = max_value_data / (cantidad_lineas - 1);
        canvas.rotate(-90,10, alto_disponible/2 );
        canvas.drawText("Tiempo", (float) (alto_disponible/(3.5)), alto_disponible/2 +35, pencilText);
        canvas.rotate(90,10, alto_disponible/2 );
        for (int i = 0; i < cantidad_lineas; i++) {
            int x = 80;
            int y = distancia_lineas * i;
            canvas.drawText(""+ Math.round(100.0*(max_value_data - (intervalo * i)))/100.0, x, y + margen_y, pencilText);
            canvas.drawLine(margen_x+15+x-80, y + margen_y, anchoMax - margen_x+15-x+110, y + margen_y, pencil);
        }
        int dias = 7;
        int distancia_dias = ancho_disponible / dias+11;
        for (int i = 0; i < dias; i++) {
            canvas.drawText("Dia " + (i + 1), 15+margen_x + distancia_dias * i, distancia_lineas * cantidad_lineas + margen_y, pencilText);
        }
        canvas.drawText("Dias ", 15+margen_x + ancho_disponible/2, distancia_lineas * (cantidad_lineas + 1)+ margen_y, pencilText);
        int espacio_central_texto_dias = 25;
        int init_point = (cantidad_lineas - 1) * distancia_lineas + margen_y;
        double variable = (init_point - margen_y) / max_value_data;
        float prev_x = margen_x;
        float prev_y = init_point;
        for (int i = 0; i < dias; i++) {
            float x = i * distancia_dias + margen_x + espacio_central_texto_dias;
            float y = 0;
            int random = (int)(Math.random()*24);
            penceilLineinGraph.setARGB(250,Util.getCollections()[random][0],Util.getCollections()[random][1],Util.getCollections()[random][2]);
            if(data.get(i)!=null){
                y = (float) (init_point - data.get(i) * (variable));
                Log.e("ERROR: ", "" + (init_point - data.get(i) * variable));
                canvas.drawPoint(x, y, penceilLineinGraph);
                canvas.drawRect(x-5,y,x+25,init_point,penceilLineinGraph);
                canvas.drawText(""+data.get(i),x,y,pencilText);
            }else{
                y = (float) (init_point - 0 * (variable));
                Log.e("ERROR: ", "" + (init_point - 0 * variable));
                canvas.drawPoint(x, y, penceilLineinGraph);
                canvas.drawText(""+0.0,x,y,pencilText);
                canvas.drawRect(x-5,y,x+25,init_point,penceilLineinGraph);
            }
            prev_x = x;
            prev_y = y;
        }
    }

    public void actualizarData(float newData){
        this.data.remove(0);
        this.data.add((double) newData);
        invalidate();
    }

    private double getMaxValue(List<Double> data) {
        double max = data.get(0);
        for (Double d : data) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }
}