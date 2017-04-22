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
    int x=0;int y=0;
    Bitmap paisaje,azul,verde,marron,amarillo,gris,life;

    Integer [] azules={R.drawable.azul1,R.drawable.azul2,R.drawable.azul3,R.drawable.azul4,R.drawable.azul5};
    Integer []verdes={R.drawable.verde1,R.drawable.verde2,R.drawable.verde3,R.drawable.verde4,R.drawable.verde5};
    Integer [] marrones={R.drawable.marron1,R.drawable.marron2,R.drawable.marron3,R.drawable.marron4,R.drawable.marron5};
    Integer [] amarillos={};
    Integer [] grises={};

    private int basura_activa=-1;
    private int azul_activa=-1;
    private int verde_activa=-1;
    private int cafe_activa=-1;

    int vidas;
    ArrayList<Basura> blue=new ArrayList<Basura>();
    ArrayList<Basura> green= new ArrayList<Basura>();
    ArrayList<Basura> brown= new ArrayList<Basura>();

    public Lienzo(Context context) {

        super(context);
        vidas=5;
        life=BitmapFactory.decodeResource(getResources(),R.drawable.vida);
        life=redimensionarImagenMaximo(life,80,80);

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

        initBasuras(azules,blue,1);
        initBasuras(verdes,green,2);
        initBasuras(marrones,brown,3);

    }
    private void initBasuras(Integer [] imagenes,ArrayList<Basura> basuras,int tipo){
        Basura b;
        for(int i=0;i<imagenes.length;i++){
            Bitmap aux=BitmapFactory.decodeResource(getResources(),imagenes[i]);
            aux=redimensionarImagenMaximo(aux,130,130);
            b= new Basura(aux,getRandomAxis(65,1983),getRandomAxis(550,1000),tipo);
            basuras.add(b);
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
        c.drawBitmap(amarillo,500,1070,p);
        c.drawBitmap(verde,850,1070,p);
        c.drawBitmap(gris,1400,1070,p);
        c.drawBitmap(marron,1800,1070,p);
        int countx=1965;
        for(int i=0;i<vidas;i++){
            c.drawBitmap(life,countx,10,p);
            countx=countx-90;
        }

        for(int z=0;z<blue.size();z++){
            c.drawBitmap(blue.get(z).img,blue.get(z).cx,blue.get(z).cy,p);
        }
        for(int i=0;i<green.size();i++){
            c.drawBitmap(green.get(i).img,green.get(i).cx,green.get(i).cy,p);
        }
        for(int i=0;i<brown.size();i++){
            c.drawBitmap(brown.get(i).img,brown.get(i).cx,brown.get(i).cy,p);
        }

    }
    public boolean onTouchEvent(MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                /*int ex= (int)e.getX();
                int ey= (int)e.getY();
                int size= blue.size();
                Log.e("Tamaño arreglo:",size+"");
                if(basura_activa==-1){
                    for(int i=0;i<size;i++){
                        if(ex>blue.get(i).cx && ex<blue.get(i).cx+130 ){
                            if(ey>blue.get(i).cy && ey<blue.get(i).cy+130){
                                basura_activa=i;
                                break;
                            }
                            break;
                        }
                    }
                }
                else{
                    blue.get(basura_activa).cx=ex-65;
                    blue.get(basura_activa).cy=ey-65;
                }*/
                checkMovement(e,blue,1);
                checkMovement(e,green,2);
                checkMovement(e,brown,3);
                break;
            case MotionEvent.ACTION_UP:
                //basura_activa=-1;
                verde_activa=cafe_activa=azul_activa=-1;
                break;
        }

        invalidate();
        return true;
    }
    private void checkMovement(MotionEvent e,ArrayList<Basura> b,int tipo){
        int ex= (int)e.getX();
        int ey= (int)e.getY();
        int size= b.size();
        switch (tipo){
            case 1:
                if(azul_activa==-1){
                    for(int i=0;i<size;i++){
                        if(ex>b.get(i).cx && ex<b.get(i).cx+130 ){
                            if(ey>b.get(i).cy && ey<b.get(i).cy+130){
                                azul_activa=i;
                                break;
                            }
                            break;
                        }
                    }
                }
                else{
                    b.get(azul_activa).cx=ex-65;
                    b.get(azul_activa).cy=ey-65;
                }
                break;
            case 2:
                if(verde_activa==-1){
                    for(int i=0;i<size;i++){
                        if(ex>b.get(i).cx && ex<b.get(i).cx+130 ){
                            if(ey>b.get(i).cy && ey<b.get(i).cy+130){
                                verde_activa=i;
                                break;
                            }
                            break;
                        }
                    }
                }
                else{
                    b.get(verde_activa).cx=ex-65;
                    b.get(verde_activa).cy=ey-65;
                }
                break;
            case 3:
                if(cafe_activa==-1){
                    for(int i=0;i<size;i++){
                        if(ex>b.get(i).cx && ex<b.get(i).cx+130 ){
                            if(ey>b.get(i).cy && ey<b.get(i).cy+130){
                                cafe_activa=i;
                                break;
                            }
                            break;
                        }
                    }
                }
                else{
                    b.get(cafe_activa).cx=ex-65;
                    b.get(cafe_activa).cy=ey-65;
                }
                break;
        }

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
