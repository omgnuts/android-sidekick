package com.mikimedia.android.nuori;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NuoriParallaxScrollView extends ScrollView implements NuoriParallaxView {

    private final Nuori nuori = new Nuori(this);

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
        nuori.initFromAttributes(context, null, 0, 0);
    }

    public NuoriParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        nuori.initFromAttributes(context, attrs, 0, 0);
    }

    public NuoriParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        nuori.initFromAttributes(context, attrs, defStyle, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NuoriParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        nuori.initFromAttributes(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        boolean isCollapseAnimation = nuori.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);

        boolean res = isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);

        System.out.println(res);

        return res;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        nuori.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        nuori.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

}
