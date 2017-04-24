package mx.edu.ittepic.tpdm_u2_mini;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import static mx.edu.ittepic.tpdm_u2_mini.R.raw.background;

public class Principal extends AppCompatActivity {
//2048x1536
    MediaPlayer mpb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        Point point = new Point(metrics.widthPixels, metrics.heightPixels);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new Lienzo(this, point));

        //mpb = MediaPlayer.create(Principal.this, background);
        //mpb.setLooping(true);
        //mpb.start();
    }

    /*protected void onPause(){
        super.onPause();
        mpb.release();
        finish();
    }*/

 /*   @Override
    protected void onDestroy() {
        super.onDestroy();
        mpb.release();
        finish();
    }*/
}
