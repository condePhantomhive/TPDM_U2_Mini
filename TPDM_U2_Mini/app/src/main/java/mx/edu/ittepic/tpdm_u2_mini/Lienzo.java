package mx.edu.ittepic.tpdm_u2_mini;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kon_n on 17/04/2017.
 */

public class Lienzo extends View {
    //2048x1536
    Bitmap paisaje,azul,verde,marron,amarillo,gris,life;

    Integer [] azules={R.drawable.azul1,R.drawable.azul2,R.drawable.azul3,R.drawable.azul4,R.drawable.azul5};
    Integer []verdes={R.drawable.verde1,R.drawable.verde2,R.drawable.verde3,R.drawable.verde4,R.drawable.verde5};
    Integer [] marrones={R.drawable.marron1,R.drawable.marron2,R.drawable.marron3,R.drawable.marron4,R.drawable.marron5};
    Integer [] amarillos={};
    Integer [] grises={};

    int vidas;
    ArrayList<Basura> blue=new ArrayList<Basura>();

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

        initBlue();
    }
    private void initBlue(){
        Basura b;
        for(int i=0;i<azules.length;i++){
            Bitmap aux=BitmapFactory.decodeResource(getResources(),azules[i]);
            aux=redimensionarImagenMaximo(aux,30,30);
            b= new Basura(aux,getRandomAxis(20,2020),getRandomAxis(20,1020));
            blue.add(b);
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
        c.drawBitmap(blue.get(0).img,0,0,p);
        int countx=1965;
        for(int i=0;i<vidas;i++){
            c.drawBitmap(life,countx,10,p);
            countx=countx-90;
        }
        Log.e("Blue tiene ",blue.size()+"");

        for(int z=0;z<blue.size();z++){
            c.drawBitmap(blue.get(z).img,blue.get(z).cx,blue.get(z).cy,p);
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
