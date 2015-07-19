/*
 *
 *  * Copyright (c) 2015. The MikiMedia Inc
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.mikimedia.demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mikimedia.android.fragment.ScreenCompatActivity;
import com.mikimedia.android.fragment.ScreenSwitcher;
import com.mikimedia.demo.R;
import com.mikimedia.demo.fragments.DefaultFragment;

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
