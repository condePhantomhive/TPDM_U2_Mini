package mx.edu.ittepic.tpdm_u2_mini;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kon_n on 17/04/2017.
 */

public class Lienzo extends View {
    //2048x1536
    Bitmap paisaje,azul,verde,marron,amarillo,gris,life,trofeo;

    Integer [] azules={R.drawable.azul1,R.drawable.azul2,R.drawable.azul3,R.drawable.azul4,R.drawable.azul5};
    Integer []verdes={R.drawable.verde1,R.drawable.verde2,R.drawable.verde3,R.drawable.verde4,R.drawable.verde5};
    Integer [] marrones={R.drawable.marron1,R.drawable.marron2,R.drawable.marron3,R.drawable.marron4,R.drawable.marron5};
    Integer [] amarillos={R.drawable.amarillo1,R.drawable.amarillo2,R.drawable.amarillo3,R.drawable.amarillo4,R.drawable.amarillo5};
    Integer [] grises={};

    private int basura_activa=-1;

    int c_azules=0;
    int c_verdes=0;
    int c_marron=0;
    int c_amarillo=0;
    int c_gris=0;
    int tipo;
    int vidas;
    ArrayList<Basura>basura = new ArrayList<Basura>();
    boolean eliminar=false;
    boolean error=false;
    public Lienzo(Context context) {

        super(context);
        vidas=5;
        life=BitmapFactory.decodeResource(getResources(),R.drawable.vida);
        life=redimensionarImagenMaximo(life,80,80);

        trofeo=BitmapFactory.decodeResource(getResources(),R.drawable.complete);
        trofeo=redimensionarImagenMaximo(trofeo,200,320);

        paisaje= BitmapFactory.decodeResource(getResources(),R.drawable.bosque);
        paisaje=redimensionarImagenMaximo(paisaje,2048,1536);

        azul=BitmapFactory.decodeResource(getResources(),R.drawable.boteazul);
        azul=redimensionarImagenMaximo(azul,200,320);

        verde=BitmapFactory.decodeResource(getResources(),R.drawable.boteverde);
        verde=redimensionarImagenMaximo(verde,200,320);

        marron=BitmapFactory.decodeResource(getResources(),R.drawable.botemarron);
        marron=redimensionarImagenMaximo(marron,200,320);

        amarillo=BitmapFactory.decodeResource(getResources(),R.drawable.boteamarillo);
        amarillo=redimensionarImagenMaximo(amarillo,200,320);

        gris=BitmapFactory.decodeResource(getResources(),R.drawable.botegris);
        gris=redimensionarImagenMaximo(gris,200,320);

        initBasuras(azules,1);
        initBasuras(verdes,2);
        initBasuras(marrones,3);
        initBasuras(amarillos,4);
        //gris 5
    }
    private void initBasuras(Integer [] imagenes,int tipo){
        Basura b;
        for(int i=0;i<imagenes.length;i++){
            Bitmap aux=BitmapFactory.decodeResource(getResources(),imagenes[i]);
            aux=redimensionarImagenMaximo(aux,130,130);
            b= new Basura(aux,getRandomAxis(65,1983),getRandomAxis(550,1000),tipo);
            basura.add(b);
        }
    }
    private int getRandomAxis(int init,int end){
        Random randomGenerator= new Random();
        long rango=end-init+1;
        long fraccion=(long)(rango*randomGenerator.nextDouble());
        int randomNum=(int)(fraccion+init);
        return randomNum;
    }
    @Override
    protected void onDraw(Canvas c){
        Paint p= new Paint();
        c.drawBitmap(paisaje,0,0,p);

        c.drawBitmap(azul,80,1070,p);
        if(c_azules==5){c.drawBitmap(trofeo,80,1070,p);}
        c.drawBitmap(amarillo,500,1070,p);
        if(c_amarillo==5){c.drawBitmap(trofeo,500,1070,p);}
        c.drawBitmap(verde,850,1070,p);
        if(c_verdes==5){c.drawBitmap(trofeo,850,1070,p);}
        c.drawBitmap(gris,1400,1070,p);
        if(c_gris==5){c.drawBitmap(trofeo,1400,1070,p);}
        c.drawBitmap(marron,1800,1070,p);
        if(c_marron==5){c.drawBitmap(trofeo,1800,1070,p);}


        int countx=1965;
        for(int i=0;i<vidas;i++){
            c.drawBitmap(life,countx,10,p);
            countx=countx-90;
        }

        for(int z=0;z<basura.size();z++){
            c.drawBitmap(basura.get(z).img,basura.get(z).cx,basura.get(z).cy,p);
        }

    }
    public boolean onTouchEvent(MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int ex= (int)e.getX();
                int ey= (int)e.getY();
                int size= basura.size();
                if(basura_activa==-1){
                    for(int i=0;i<size;i++){
                        if(ex>basura.get(i).cx && ex<basura.get(i).cx+130 ){
                            if(ey>basura.get(i).cy && ey<basura.get(i).cy+130){
                                //Log.e("Basura_activa primeriza",basura_activa+"");
                                basura_activa=i;
                                break;
                            }
                            break;
                        }
                    }
                }
                else{
                    //Log.e("Basura_activa ",basura_activa+"");
                    basura.get(basura_activa).cx=ex-65;
                    basura.get(basura_activa).cy=ey-65;
                    //azul
                    if(basura.get(basura_activa).cx>100 && basura.get(basura_activa).cx<260){
                        if(basura.get(basura_activa).cy>1140 && basura.get(basura_activa).cy<1390){
                            if(basura.get(basura_activa).tipo==1){
                                eliminar=true;
                                tipo=1;
                                Log.e("Azul ",c_azules+"");
                                error=false;
                            }else{
                                error=true;
                            }
                        }
                    }
                    //amarillo
                    if(basura.get(basura_activa).cx>520 && basura.get(basura_activa).cx<680){
                        if(basura.get(basura_activa).cy>1140 && basura.get(basura_activa).cy<1390){
                            if(basura.get(basura_activa).tipo==4){
                                eliminar=true;
                                tipo=4;
                                Log.e("Amarillo ",c_amarillo+"");
                                error=false;
                            }else{
                                error=true;
                            }
                        }
                    }
                    //verde
                    if(basura.get(basura_activa).cx>870 && basura.get(basura_activa).cx<1030){
                        if(basura.get(basura_activa).cy>1140 && basura.get(basura_activa).cy<1390){
                            if(basura.get(basura_activa).tipo==2){
                                eliminar=true;
                                tipo=2;
                                Log.e("Verde ",c_verdes+"");
                                error=false;
                            }else{
                                error=true;
                            }
                        }
                    }
                    //gris
                    if(basura.get(basura_activa).cx>1420 && basura.get(basura_activa).cx<1580){
                        if(basura.get(basura_activa).cy>1140 && basura.get(basura_activa).cy<1390){
                            if(basura.get(basura_activa).tipo==5){
                                eliminar=true;
                                tipo=5;
                                Log.e("Gris ",c_gris+"");
                                error=false;
                            }else{
                                error=true;
                            }
                        }
                    }
                    //marron
                    if(basura.get(basura_activa).cx>1820 && basura.get(basura_activa).cx<1980){
                        if(basura.get(basura_activa).cy>1140 && basura.get(basura_activa).cy<1390){
                            if(basura.get(basura_activa).tipo==3){
                                eliminar=true;
                                tipo=3;
                                Log.e("Marron ",c_marron+"");
                                error=false;
                            }else{
                                error=true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                    /*100,1140,520,1140,870,11401420,1140
                    1820,1140*/
                if(eliminar || error){
                    basura.remove(basura_activa);
                    if(error){vidas--;}
                    if(eliminar){
                        switch (tipo){
                            case 1:
                                c_azules++;
                                break;
                            case 2:
                                c_verdes++;
                                break;
                            case 3:
                                c_marron++;
                                break;
                            case 4:
                                c_amarillo++;
                                break;
                            case 5:
                                c_gris++;
                                break;
                        }
                    }
                    eliminar=false;
                    error=false;
                    tipo=-1;
                }
                basura_activa=-1;
                //Log.e("Basura_activa ",basura_activa+"");
                break;
        }
        invalidate();
        return true;
    }



    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
