package com.mikimedia.android.nuori;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NuoriParallaxScrollView extends ScrollView implements NuoriParallaxView {

    private final Nuori nuori;

    public Nuori getNuori() {
        return this.nuori;
    }

    public void setNuori(Nuori nuori) {
        if (!nuori.equals(this.nuori)) {
            throw new IllegalStateException("Don't just change the nuori!");
        }
//        addHeaderView(nuori.getHeaderView());
    }

    public NuoriParallaxScrollView(Context context) {
        super(context);
        nuori = new Nuori(this, context, null, 0, 0);
    }

    public NuoriParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        nuori = new Nuori(this, context, attrs, 0, 0);
    }

    public NuoriParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nuori = new Nuori(this, context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NuoriParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        nuori = new Nuori(this, context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // performs the overscroll
        boolean consume = nuori.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
        return consume || super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        nuori.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean consume = nuori.onTouchEvent(ev);
        return consume || super.onTouchEvent(ev);
    }

    private final int FAT_FINGERS = 0;

    public float computePerspectiveOffset(int initHeightPx, float zoomed,float nonZoomablePart) {
        float offset = 0;
        int scrollY = getScrollY();

        if (zoomed > 1.0) {
            offset = -1 * (zoomed - 1) / zoomed * (scrollY - nonZoomablePart);
        }

        if (scrollY + offset - FAT_FINGERS > nonZoomablePart + initHeightPx) {
//            Log.d(TAG, "offset changed before = " + offset);
            offset = nonZoomablePart + 0.8f * initHeightPx - scrollY;
//            Log.d(TAG, "offset changed after = " + offset);
        }

        return offset;
    }
}
