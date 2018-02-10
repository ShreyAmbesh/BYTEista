package byteista.sahayam.Utils;

import android.graphics.Bitmap;


/**
 * Created by Lykan on 9-02-2018.
 */

public abstract class BitmapManipulator {
    public static Bitmap scaleBitmap(Bitmap srcBmp)
    {Bitmap dstBmp;
        if (srcBmp.getWidth() <=srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );


        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }
        return dstBmp;

    }
}
