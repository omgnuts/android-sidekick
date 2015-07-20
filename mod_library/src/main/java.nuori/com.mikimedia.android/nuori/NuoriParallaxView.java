package com.mikimedia.android.nuori;

import android.content.Context;

interface NuoriParallaxView {

    Context getContext();

    Nuori getNuori();
    void setNuori(Nuori nuori);

    float computePerspectiveOffset(int initHeightPx, float zoomed,float nonZoomablePart);
    void scrollBy(int x, int y);

}
