package byteista.sahayam.NavigationDrawer;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {


        outRect.bottom = space;

        int pos=parent.getChildLayoutPosition(view);
        if (pos == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
        if (pos == 2||pos == 4||pos == 6||pos == 8) {
            outRect.left = space/2;
        } else {
            outRect.left = space;
        }
        if (pos == 1||pos == 3||pos == 5||pos == 7) {
            outRect.right = space/2;
        } else {
            outRect.right= space;
        }
    }
}
