package letier.brandon.weatherapp.ui.wordlist.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class LocationSpacerItemDecorator extends RecyclerView.ItemDecoration {
    private static final float SPACER = 16;
    private static final float SMALL_SPACER = 8;
    private final Context context;

    public LocationSpacerItemDecorator(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = convertDpToDeviceScreenSize(SMALL_SPACER, context);
        outRect.left = convertDpToDeviceScreenSize(SPACER, context);
        outRect.right = convertDpToDeviceScreenSize(SPACER, context);
        outRect.top = convertDpToDeviceScreenSize(SMALL_SPACER, context);
    }

    private static int convertDpToDeviceScreenSize(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, metrics);
    }
}
