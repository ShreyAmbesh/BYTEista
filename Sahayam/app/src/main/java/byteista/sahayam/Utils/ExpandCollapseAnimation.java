package byteista.sahayam.Utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class ExpandCollapseAnimation extends Animation {
    private View mAnimatedView;
    private int mEndHeight;
    private int mType;
    private int height;

    public ExpandCollapseAnimation(View view, int duration, int type,int height) {
        setDuration(duration);
        this.height=height;
        mAnimatedView = view;

        mEndHeight = mAnimatedView.getLayoutParams().height;

        mType = type;
        if(mType == 0) {
            mAnimatedView.getLayoutParams().height = height;
            mAnimatedView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
            if(mType == 0) {
                mAnimatedView.getLayoutParams().height = (int) ((mEndHeight-height) * interpolatedTime)+height;
            } else {
                mAnimatedView.getLayoutParams().height = mEndHeight - (int) ((mEndHeight-height)* interpolatedTime);
            }
            mAnimatedView.requestLayout();
        } else {
            if(mType == 0) {
                mAnimatedView.getLayoutParams().height = mEndHeight;
                mAnimatedView.requestLayout();
            } else {
                mAnimatedView.getLayoutParams().height = height;
                mAnimatedView.requestLayout();
            }
        }
    }
}
