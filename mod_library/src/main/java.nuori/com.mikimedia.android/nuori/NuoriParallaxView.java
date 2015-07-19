package com.mikimedia.android.nuori;

import android.content.Context;

interface NuoriParallaxView {

    Nuori getNuori();
    void setNuori(Nuori nuori);

    Context getContext();
    int getPaddingTop();

    boolean post(Runnable runnable);

}
