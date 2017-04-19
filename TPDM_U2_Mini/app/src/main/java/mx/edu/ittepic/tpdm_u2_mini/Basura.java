package mx.edu.ittepic.tpdm_u2_mini;

import android.graphics.Bitmap;

/**
 * Created by kon_n on 17/04/2017.
 */

public class Basura {
    Bitmap img;
    int cx,cy;
    int tipo;
    public Basura(Bitmap img, int cx, int cy){
        this.cx=cx;
        this.cy=cy;
        this.img=img;
    }
}
