package com.mikimedia.android.nuori;

import android.content.Context;

interface NuoriParallaxView {

    Nuori getNuori();
    Nuori setNuori(Nuori nuori);

    Context getContext();
    int getPaddingTop();

    boolean post(Runnable runnable);

}
