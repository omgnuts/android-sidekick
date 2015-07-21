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

package com.mikimedia.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mikimedia.demo.fragments.SwitcherActivity;
import com.mikimedia.demo.imageslider.ImageSliderActivity;
import com.mikimedia.demo.nuori.NuoriListViewActivity;
import com.mikimedia.demo.nuori.NuoriRecyclerViewActivity;
import com.mikimedia.demo.nuori.NuoriScrollViewActivity;
import com.mikimedia.demo.nuori.Pull2ZoomActivity;

public class MainActivity extends AppCompatActivity {

    private static final SparseArray<String> buttons = new SparseArray<String>();

    static {
        buttons.put(1, "Fragments");
        buttons.put(2, "ImageSlider");
        buttons.put(3, "XCropImageView");
        buttons.put(4, "Nuori Listview");
        buttons.put(5, "Nuori Scrollview");
        buttons.put(6, "Nuori RecyclerView");
        buttons.put(100, "Pull2Zoom");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.container);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int c = 0; c < buttons.size(); c++) {
            int key = buttons.keyAt(c);
            String name = buttons.get(key);

            Button b = new Button(this);
            b.setLayoutParams(lp);
            b.setText(name);
            b.setId(key);
            b.setOnClickListener(clicker);

            layout.addView(b);
        }

    }

    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch(v.getId()) {
                case 1: // fragments
                    intent = new Intent(MainActivity.this, SwitcherActivity.class);
                    break;
                case 2: // ImageSliderActivity
                    intent = new Intent(MainActivity.this, ImageSliderActivity.class);
                    break;
                case 3: // XCropImageViewActivity
                    intent = new Intent(MainActivity.this, XCropImageViewActivity.class);
                    break;
                case 4: // NuoriListViewActivity
                    intent = new Intent(MainActivity.this, NuoriListViewActivity.class);
                    break;
                case 5: // NuoriScrollViewActivity
                    intent = new Intent(MainActivity.this, NuoriScrollViewActivity.class);
                    break;
                case 6: // NuoriRecyclerViewActivity
                    intent = new Intent(MainActivity.this, NuoriRecyclerViewActivity.class);
                    break;
                case 100: // Pull2ZoomActivity
                    intent = new Intent(MainActivity.this, Pull2ZoomActivity.class);
                    break;
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    };

}
