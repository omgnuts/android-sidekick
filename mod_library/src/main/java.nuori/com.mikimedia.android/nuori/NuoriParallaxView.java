package com.mikimedia.android.nuori;

import android.content.Context;

interface NuoriParallaxView {

    Context getContext();

    Nuori getNuori();
    Nuori setNuori(Nuori nuori);

//    int getPaddingTop();
//    int getTop();
//    int getScrollYOffset();

    boolean post(Runnable runnable);

}
