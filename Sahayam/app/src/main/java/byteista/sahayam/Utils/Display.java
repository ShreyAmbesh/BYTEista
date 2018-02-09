package byteista.sahayam.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;


/**
 * Created by Lykan on 9-02-2018.
 */

public class Display {
    final DisplayMetrics DISPLAY_METRICS;
    int status_bar_height;
    Context context;

    public Display(Context context) {
        this.context = context;
        DISPLAY_METRICS = ((Context) context).getResources().getDisplayMetrics();
        status_bar_height = 0;
        int resourceId = ((Context) context).getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            status_bar_height = ((Context) context).getResources().getDimensionPixelSize(resourceId);
        }
    }

    public int getHeight() {
        return DISPLAY_METRICS.heightPixels - status_bar_height;
    }

    public int getWidth() {
        return DISPLAY_METRICS.widthPixels;
    }

    public int getStatusBarHeight() {
        int resourceId = ((Context) context).getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            status_bar_height = ((Context) context).getResources().getDimensionPixelSize(resourceId);
        }
        return status_bar_height;
    }
}
