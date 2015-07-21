package com.mikimedia.android.nuori;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NuoriParallaxRecyclerView extends RecyclerView implements NuoriParallaxView {

    private final Nuori nuori;

    public Nuori getNuori() {
        return this.nuori;
    }

    public void setNuori(Nuori nuori) {
        if (!nuori.equals(this.nuori)) {
            throw new IllegalStateException("Don't just change the nuori!");
        }
//        setHasFixedSize(false);
    }

    public NuoriParallaxRecyclerView(Context context) {
        super(context);
        nuori = new Nuori(this, context, null, 0, 0);
        initScrollLayoutManager(context);
    }

    public NuoriParallaxRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        nuori = new Nuori(this, context, attrs, 0, 0);
        initScrollLayoutManager(context);
    }

    public NuoriParallaxRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nuori = new Nuori(this, context, attrs, defStyleAttr, 0);
        initScrollLayoutManager(context);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // performs the overscroll
//        nuori.pullToZoom(deltaY, true);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        nuori.onParallaxList();
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        nuori.onBounceBack(ev.getAction());
        return super.onTouchEvent(ev);
    }

    public float computePerspectiveOffset(int initHeightPx, float zoomed,float nonZoomablePart) {
        return 0;
    }

    public void setLayoutManager(LayoutManager layout) {
        throw new IllegalStateException("Unable to override ScrollLayoutManager!!");
    }

    private void initScrollLayoutManager(Context context) {
        LinearLayoutManager linearLayoutManager = new ScrollLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        super.setLayoutManager(linearLayoutManager);
    }

    private class ScrollLayoutManager extends LinearLayoutManager {

        public ScrollLayoutManager(Context context) {
            super(context);
        }

        public ScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public ScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        // https://www.reddit.com/r/androiddev/comments/2t01e0/detect_overscroll_in_recyclerview/
        @Override
        public int scrollVerticallyBy (int dy, RecyclerView.Recycler recycler, RecyclerView.State state ) {
            int scrollRange = super.scrollVerticallyBy(dy, recycler, state);
            int overscroll = (dy - scrollRange);
            if (overscroll < 0) {
                // top overscroll
                if (nuori.pullToZoom(overscroll, true)) {
                    post(updateLayout);
                };
            } else if (overscroll > 0) {
                // bottom overscroll
            }
            return scrollRange;
        }

        private final Runnable updateLayout = new Runnable() {
            @Override
            public void run() {
                NuoriParallaxRecyclerView.this.getAdapter().notifyItemChanged(0);
            }
        };
    }
}
