package mx.edu.ittepic.tpdm_u2_mini;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by kon_n on 17/04/2017.
 */

public class Lienzo extends View {
    //2048x1536
    Bitmap paisaje, azul, verde, marron, amarillo, gris, life, trofeo, correcto, incorrecto, ganar, perder;

    public static int WIDTH;
    public static int HEIGHT;
    MediaPlayer bien, mal;
    ImageView win, lost;
    ImageView success, wrong;
    CountDownTimer timer;
    Integer[] azules = {R.drawable.azul1, R.drawable.azul2, R.drawable.azul3, R.drawable.azul4, R.drawable.azul5};
    Integer[] verdes = {R.drawable.verde1, R.drawable.verde2, R.drawable.verde3, R.drawable.verde4, R.drawable.verde5};
    Integer[] marrones = {R.drawable.marron1, R.drawable.marron2, R.drawable.marron3, R.drawable.marron4, R.drawable.marron5};
    Integer[] amarillos = {R.drawable.amarillo1, R.drawable.amarillo2, R.drawable.amarillo3, R.drawable.amarillo4, R.drawable.amarillo5};
    Integer[] grises = {R.drawable.gris1, R.drawable.gris2, R.drawable.gris3, R.drawable.gris4, R.drawable.gris5};

    int tipo;
    int vidas;
    int pos;

    private int basura_activa;
    int c_azules, c_verdes, c_marron, c_amarillo, c_gris;
    ArrayList<Basura> basura, auxBasura;
    boolean eliminar = false;
    boolean error = false;

    public Lienzo(Context context, Point point) {
        super(context);
        HEIGHT = point.y;
        WIDTH = point.x;
        correcto = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        correcto = redimensionarImagenMaximo(correcto, 600, 600);
        incorrecto = BitmapFactory.decodeResource(getResources(), R.drawable.dislike);
        incorrecto= redimensionarImagenMaximo(incorrecto, 600, 600);
        ganar = BitmapFactory.decodeResource(getResources(), R.drawable.winner);
        ganar = redimensionarImagenMaximo(ganar, 300, 300);
        perder = BitmapFactory.decodeResource(getResources(), R.drawable.perdiste);
        perder = redimensionarImagenMaximo(perder, 300, 300);
        win = new ImageView(getContext());
        success= new ImageView(getContext());
        wrong = new ImageView(getContext());
        lost = new ImageView(getContext());

        success.setImageBitmap(correcto);
        wrong.setImageBitmap(incorrecto);
        win.setImageBitmap(ganar);
        lost.setImageBitmap(perder);
        pos = 0;
        bien = MediaPlayer.create(getContext(), R.raw.bien);
        mal = MediaPlayer.create(getContext(), R.raw.error);
        life = BitmapFactory.decodeResource(getResources(), R.drawable.vida);
        life = redimensionarImagenMaximo(life, 80, 80);

        trofeo = BitmapFactory.decodeResource(getResources(), R.drawable.complete);
        trofeo = redimensionarImagenMaximo(trofeo, 200, 320);

        paisaje = BitmapFactory.decodeResource(getResources(), R.drawable.bosque);
        paisaje = redimensionarImagenMaximo(paisaje, WIDTH, HEIGHT);

        azul = BitmapFactory.decodeResource(getResources(), R.drawable.boteazul);
        azul = redimensionarImagenMaximo(azul, 200, 320);

        verde = BitmapFactory.decodeResource(getResources(), R.drawable.boteverde);
        verde = redimensionarImagenMaximo(verde, 200, 320);

        marron = BitmapFactory.decodeResource(getResources(), R.drawable.botemarron);
        marron = redimensionarImagenMaximo(marron, 200, 320);

        amarillo = BitmapFactory.decodeResource(getResources(), R.drawable.boteamarillo);
        amarillo = redimensionarImagenMaximo(amarillo, 200, 320);

        gris = BitmapFactory.decodeResource(getResources(), R.drawable.botegris);
        gris = redimensionarImagenMaximo(gris, 200, 320);
        startGame();
        timer = new CountDownTimer(4000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (pos < 25) {
                    changeBasuras();
                    invalidate();
                }
            }

            @Override
            public void onFinish() {
                start();
            }
        };
        timer.start();


    }

    private void initBasuras(Integer[] imagenes, int tipo) {
        Basura b;
        for (Integer imagene : imagenes) {
            Bitmap aux = BitmapFactory.decodeResource(getResources(), imagene);
            aux = redimensionarImagenMaximo(aux, 130, 130);
            b = new Basura(aux, getRandomAxis(70, WIDTH - 70), getRandomAxis(HEIGHT / 5, HEIGHT / 2), tipo);
            auxBasura.add(b);
            Collections.shuffle(auxBasura);
        }
    }

    private void changeBasuras(){
        basura.add(auxBasura.get(pos));
        pos ++;
    }

    private int getRandomAxis(int init, int end) {
        Random randomGenerator = new Random();
        long rango = end - init + 1;
        long fraccion = (long) (rango * randomGenerator.nextDouble());
        return (int) (fraccion + init);
    }

    @Override
    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        c.drawBitmap(paisaje, 0, 0, p);

        c.drawBitmap(azul, 80, HEIGHT - 450, p);
        if (c_azules == 5) {
            c.drawBitmap(trofeo, 80, HEIGHT - 450, p);
        }
        c.drawBitmap(amarillo, WIDTH / 5 + 80, HEIGHT - 450, p);
        if (c_amarillo == 5) {
            c.drawBitmap(trofeo, WIDTH / 5 + 80, HEIGHT - 450, p);
        }
        c.drawBitmap(verde, WIDTH / 5 * 2 + 80, HEIGHT - 450, p);
        if (c_verdes == 5) {
            c.drawBitmap(trofeo, WIDTH / 5 * 2 + 80, HEIGHT - 450, p);
        }
        c.drawBitmap(gris, WIDTH / 5 * 3 + 80, HEIGHT - 450, p);
        if (c_gris == 5) {
            c.drawBitmap(trofeo, WIDTH / 5 * 3 + 80, HEIGHT - 450, p);
        }
        c.drawBitmap(marron, WIDTH / 5 * 4 + 80, HEIGHT - 450, p);
        if (c_marron == 5) {
            c.drawBitmap(trofeo, WIDTH / 5 * 4 + 80, HEIGHT - 450, p);
        }

        int countx = WIDTH - 85;
        for (int i = 0; i < vidas; i++) {
            c.drawBitmap(life, countx, 10, p);
            countx = countx - 90;
        }
        for (int pos =0; pos<basura.size(); pos++) {
            c.drawBitmap(basura.get(pos).img, basura.get(pos).cx, basura.get(pos).cy, p);
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int ex = (int) e.getX();
                int ey = (int) e.getY();
                int size = basura.size();
                if (basura_activa == -1) {
                    for (int i = 0; i < size; i++) {
                        if (ex > basura.get(i).cx && ex < basura.get(i).cx + 130) {
                            if (ey > basura.get(i).cy && ey < basura.get(i).cy + 130) {
                                basura_activa = i;
                                break;
                            }
                            break;
                        }
                    }
                } else {
                    basura.get(basura_activa).cx = ex - 65;
                    basura.get(basura_activa).cy = ey - 65;
                    //azul
                    if (basura.get(basura_activa).cx > 80 && basura.get(basura_activa).cx < 280) {
                        if (basura.get(basura_activa).cy > HEIGHT - 400 && basura.get(basura_activa).cy < HEIGHT - 150) {
                            if (basura.get(basura_activa).tipo == 1) {
                                eliminar = true;
                                tipo = 1;
                                Log.e("Azul ", c_azules + "");
                                error = false;
                            } else {
                                error = true;
                            }
                        }
                    }
                    //amarillo
                    if (basura.get(basura_activa).cx > WIDTH / 5 + 80 && basura.get(basura_activa).cx < WIDTH / 5 + 80 + 200) {
                        if (basura.get(basura_activa).cy > HEIGHT - 400 && basura.get(basura_activa).cy < HEIGHT - 150) {
                            if (basura.get(basura_activa).tipo == 4) {
                                eliminar = true;
                                tipo = 4;
                                Log.e("Amarillo ", c_amarillo + "");
                                error = false;
                            } else {
                                error = true;
                            }
                        }
                    }
                    //verde
                    if (basura.get(basura_activa).cx > WIDTH / 5 * 2 + 80 && basura.get(basura_activa).cx < WIDTH / 5 * 2 + 80 + 200) {
                        if (basura.get(basura_activa).cy > HEIGHT - 400 && basura.get(basura_activa).cy < HEIGHT - 150) {
                            if (basura.get(basura_activa).tipo == 2) {
                                eliminar = true;
                                tipo = 2;
                                Log.e("Verde ", c_verdes + "");
                                error = false;
                            } else {
                                error = true;
                            }
                        }
                    }
                    //gris
                    if (basura.get(basura_activa).cx > WIDTH / 5 * 3 + 80 && basura.get(basura_activa).cx < WIDTH / 5 * 3 + 80 + 200) {
                        if (basura.get(basura_activa).cy > HEIGHT - 400 && basura.get(basura_activa).cy < HEIGHT - 150) {
                            if (basura.get(basura_activa).tipo == 5) {
                                eliminar = true;
                                tipo = 5;
                                Log.e("Gris ", c_gris + "");
                                error = false;
                            } else {
                                error = true;
                            }
                        }
                    }
                    //marron
                    if (basura.get(basura_activa).cx > WIDTH / 5 * 4 + 80 && basura.get(basura_activa).cx < WIDTH / 5 * 4 + 80 + 200) {
                        if (basura.get(basura_activa).cy > HEIGHT - 400 && basura.get(basura_activa).cy < HEIGHT - 150) {
                            if (basura.get(basura_activa).tipo == 3) {
                                eliminar = true;
                                tipo = 3;
                                Log.e("Marron ", c_marron + "");
                                error = false;
                            } else {
                                error = true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                    /*100,1140,520,1140,870,11401420,1140
                    1820,1140*/
                if (eliminar || error) {
                    basura.remove(basura_activa);
                    if (error) {
                        mal.start();
                        vidas--;
                        if (vidas >= 1) {
                            Toast toast = new Toast(getContext());
                            toast.setView(wrong);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    else if (eliminar) {
                        bien.start();
                        if (!basura.isEmpty()) {
                            Toast toast = new Toast(getContext());
                            toast.setView(success);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        switch (tipo) {
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
                    eliminar = false;
                    error = false;
                    tipo = -1;
                }
                basura_activa = -1;

                if (basura.isEmpty()){
                    showDialog("¡Buen trabajo!", win);
                }
                if (vidas == 0){
                    showDialog("¡Perdiste!", lost);
                }

                break;
        }
        invalidate();
        return true;
    }

    void showDialog(String message, ImageView imagen) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setTitle(message);
        adb.setView(imagen);
        adb.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame();
                dialog.dismiss();
                invalidate();
            }
        });
        adb.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((Activity)getContext()).finish();
            }
        });
        adb.show();
    }

    private void startGame() {
        vidas = 5;
        pos = 0;
        basura_activa = -1;
        c_azules = c_verdes = c_marron = c_amarillo = c_gris = 0;
        basura = new ArrayList<Basura>();
        auxBasura = new ArrayList<Basura>();
        eliminar = false;
        error = false;
        initBasuras(azules, 1);
        initBasuras(verdes, 2);
        initBasuras(marrones, 3);
        initBasuras(amarillos, 4);
        initBasuras(grises, 5);
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeigth / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
