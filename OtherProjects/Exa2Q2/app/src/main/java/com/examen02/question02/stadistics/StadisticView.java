package com.examen02.question02.stadistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
    private  LinearLayout layout;
    private DisplayMetrics metrics;
    private int alto=0,ancho= 0;
    private ArrayList<Double> data = new ArrayList<Double>();
    private List<Double> listData;

    public StadisticView(Context context) {
        super(context);
    }

    public StadisticView(Context context, LinearLayout _layout, DisplayMetrics metrics,ArrayList<Double> data) {
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
    public void initialStyleFigure(){
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, Util.color1[0],Util.color1[1],Util.color1[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.STROKE);
        pencil.setPathEffect(dashPathEffect);
    }//End Method

    public void initialStyleFigurePoint(){
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        penceilLine = new Paint();
        penceilLine.setAntiAlias(true);
        penceilLine.setARGB(250, Util.color3[0],Util.color3[1],Util.color3[2]);
        penceilLine.setStrokeWidth(7);
        penceilLine.setStyle(Paint.Style.STROKE);
        penceilLine.setPathEffect(dashPathEffect);
    }//End Method


    public void initialStyleText(){
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencilText = new Paint();
        pencilText.setAntiAlias(true);
        pencilText.setARGB(250, Util.color2[0],Util.color2[1],Util.color2[2]);
        pencilText.setStrokeWidth(1);
        pencilText.setTextSize(20);
        pencilText.setStyle(Paint.Style.FILL);
        pencilText.setPathEffect(dashPathEffect);
    }//End Method


    /**
     * Method onDraw draw the figure geometric
     * @param canvas area of draw*/
    @SuppressLint("CanvasSize")
    protected void onDraw(Canvas canvas){
        int altoCa = metrics.heightPixels;
        int anchoCa = metrics.widthPixels;
        int paddingAlto = altoCa - 50;
        int margen = 80;
        int margen2 = 120;
        System.out.println("medidas : "+ altoCa+" , "+anchoCa);
        double auxMax = 12;
        for (int i = 0;i<10;i++){
            //System.out.println("data : "+data.get(i));
            /*if(i==0){
                canvas.drawText(""+(auxMax+0.5),50,60+i*margen,pencilText);
            }else*/ if(i==9){
                canvas.drawText("" + (0), 50, 60 + i * margen, pencilText);
            }else {
                canvas.drawText("" + (Math.round((auxMax / 9 * (9 - i+1))*100)/100d), 45, 60 + i * margen, pencilText);
            }
            //ca.drawTe
            canvas.drawLine(100,50+i*margen,anchoCa-50,50+i*margen,pencil);
        }
        ArrayList<Double> auxlist = new ArrayList<>();
        auxlist=data;
        Collections.sort(auxlist);
        for(int i = 0;i<7 ; i++){
            canvas.drawText("Dia "+(i+1),margen2+(anchoCa-150)/7*i,50+10*margen,pencilText);
            canvas.drawPoint(margen2+20+(anchoCa-150)/7*i,50+1*margen+(0),penceilLine);
        }
    }

    public double supremoplus5(){
        System.out.println("maximoo : "+maxValue());
        return Math.floor(maxValue())+0.5;
    }

    public int maxValue(){
        double max = 0;
        int position = -1;
        for (int i = data.size()-7 ;i<data.size();i++){
            if (this.data.get(i)>=max){
                max = this.data.get(i);
                position = i;
            }
        }
        return position;

    }
}
