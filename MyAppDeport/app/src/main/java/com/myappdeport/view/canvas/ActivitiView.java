package com.myappdeport.view.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ActivitiView extends View {
    private Paint pencilCubeGray;
    private Paint pencilText;
    private Paint pencilCubeGreen;
    private DisplayMetrics metrics;
    private List<Integer> data = new ArrayList<>();
    private String month;
    private int day;

    public ActivitiView(Context context) { super(context); }

    public ActivitiView(Context context, DisplayMetrics displayMetrics, List<Integer>data, String month, int day){
        super(context);
        this.metrics =  displayMetrics;
        this.data = data;
        this.month = month;
        this.day =  day;
        initialStyleFigureCubeGray();
        initialStyleFigureCubeGreen();;
        initialStyleText();;
    }
    /**
     * Method initialStyleFigureLines define the parameters initials of the pencil Paint for Graph Activity
     */
    public void initialStyleFigureCubeGray() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilCubeGray = new Paint();
        pencilCubeGray.setAntiAlias(true);
        pencilCubeGray.setARGB(250, Util.color27[0], Util.color27[1], Util.color27[2]);
        pencilCubeGray.setStrokeWidth(22);
        pencilCubeGray.setStyle(Paint.Style.STROKE);
        pencilCubeGray.setPathEffect(dashPathEffect);
    }

    public void initialStyleFigureCubeGreen() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilCubeGreen = new Paint();
        pencilCubeGreen.setAntiAlias(true);
        pencilCubeGreen.setARGB(250, Util.color26[0], Util.color26[1], Util.color26[2]);
        pencilCubeGreen.setStrokeWidth(22);
        pencilCubeGreen.setStyle(Paint.Style.STROKE);
        pencilCubeGreen.setPathEffect(dashPathEffect);
    }//End Method

    public void initialStyleText() {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilText = new Paint();
        pencilText.setAntiAlias(true);
        pencilText.setARGB(250, Util.color4[0], Util.color4[1], Util.color4[2]);
        pencilText.setStrokeWidth(1);
        pencilText.setTextSize(25);
        pencilText.setStyle(Paint.Style.FILL);
        pencilText.setPathEffect(dashPathEffect);
    }//End Method





    @Override
    protected void onDraw(Canvas canvas) {
        int margen_lateral = Math.round(90*getWidth()/480f);
        int margen_superior = 30;
        int margen_entre_cubos = (metrics.widthPixels-(margen_lateral*2))/6 ; //6 = numero de dias -1

        canvas.drawText(this.month, metrics.widthPixels/2f -30,  margen_superior, pencilText);

        int x_indice=margen_lateral;
        int y_indice=margen_superior+40;
        String days[] = GetDays();

        for (int i=0;i <7 ;i++){

            canvas.drawText(days[i],x_indice,y_indice,pencilText);
            x_indice += margen_entre_cubos;
        }

        x_indice = margen_lateral;
        y_indice = margen_superior + 70;

        for (int j =0;j<5;j++) {
            for (int i = 0; i < 7; i++) {
                if (data.get(i) == 0) {
                    canvas.drawPoint(x_indice, y_indice, pencilCubeGray);
                } else {
                    canvas.drawPoint(x_indice, y_indice, pencilCubeGreen);
                }
                x_indice += margen_entre_cubos;

            }
            y_indice += + 30;
            x_indice = margen_lateral;
        }

        System.out.println(getHeight() +" -------------------" + getWidth());
        System.out.println(metrics.heightPixels +" -------------------" + metrics.widthPixels);


    }
    private String[] GetDays(){
        String [] days= {"d","l","m","m","j","v","s"};
        return days;
    }


}
