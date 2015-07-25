package nini.cpviewer.widget;

import android.content.Context;

/**
 * Created by nini on 15/7/23.
 */
public class UI {
    public static int toPixel(final Context context, final float dp) {
        return Math.round(context.getResources().getDisplayMetrics().density * dp);
    }
}
