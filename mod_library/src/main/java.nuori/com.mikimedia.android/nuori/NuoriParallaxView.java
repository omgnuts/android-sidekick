package com.mikimedia.android.nuori;

import android.content.Context;

interface NuoriParallaxView {

    Context getContext();

    Nuori getNuori();
    Nuori setNuori(Nuori nuori);

//    float computePerspectiveOffset();

    boolean post(Runnable runnable);

}
