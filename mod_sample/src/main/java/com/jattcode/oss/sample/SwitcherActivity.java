package com.jattcode.oss.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jattcode.android.fragment.ScreenCompatActivity;
import com.jattcode.android.fragment.ScreenSwitcher;

public class SwitcherActivity extends ScreenCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switcher_activity);
        if (savedInstanceState == null) switcher.rootScreen();
    }

    // use class here rather than enum to keep code cleaner
    public static final class ScreenType {
        public static final int ROOT = 0;
        public static final int RANDOM_FRAGMENT = 1;
        public static final int DEFAULT_FRAGMENT = 1;
    }

    public ScreenSwitcher getSwitcher() {
        return switcher;
    }

    private final ScreenSwitcher switcher = new ScreenSwitcherImpl(this, R.id.container);

    private class ScreenSwitcherImpl extends ScreenSwitcher {

        ScreenSwitcherImpl(ScreenCompatActivity activity, int containerId) {
            super(activity, containerId);
        }

        @Override
        protected Fragment getFragment(int screenId) {
            switch(screenId) {
                case ScreenType.ROOT:
                    return new StatefulFragment();
                case ScreenType.RANDOM_FRAGMENT:
                    return new RandomFragment();
                default:
                    return new DefaultFragment();
            }

        }
    }

}
