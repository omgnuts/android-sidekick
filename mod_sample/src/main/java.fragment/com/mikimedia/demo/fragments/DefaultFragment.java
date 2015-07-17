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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikimedia.android.fragment.Screen;
import com.mikimedia.android.fragment.ScreenCompatActivity;
import com.mikimedia.android.fragment.ScreenSwitcher;
import com.mikimedia.demo.R;
import com.mikimedia.demo.fragments.SwitcherActivity;

public class DefaultFragment extends Fragment implements Screen {

    private EditText getEditText() {
        return (EditText) parentView.findViewById(android.R.id.edit);
    }

    private TextView getTextView() {
        return (TextView) parentView.findViewById(android.R.id.text1);
    }

    private Button getButtonStartActivity() {
        return (Button) parentView.findViewById(android.R.id.button2);
    }

    private Button getButtonSimulateInput() {
        return (Button) parentView.findViewById(android.R.id.button1);
    }

    private Button getButtonStartFragment() {
        return (Button) parentView.findViewById(R.id.button4);
    }

    protected ScreenSwitcher getSwitcher() {
        return ((ScreenCompatActivity)getActivity()).getSwitcher();
    }

    Context context;

    View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        parentView = inflater.inflate(R.layout.fragment_demo_default, container, false);
        context = getActivity();

        onInitViews();
        return parentView;
    }

    private void onInitViews() {
        getButtonSimulateInput().setOnClickListener(clicker);
        getButtonStartActivity().setOnClickListener(clicker);
        getButtonStartFragment().setOnClickListener(clicker);
    }

    private final View.OnClickListener clicker = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case android.R.id.button1: // getButtonSimulateInput
                    getEditText().setText("Today is a good day");
                    getTextView().setText("Today is a good day");
                    break;

                case android.R.id.button2: // getButtonStartActivity
                    getSwitcher().changeScreen(SwitcherActivity.ScreenType.DEFAULT_FRAGMENT);
                    break;

                case R.id.button4: //getButtonStartFragment
                    getSwitcher().changeScreen(SwitcherActivity.ScreenType.RANDOM_FRAGMENT);
                    break;
            }
        }
    };

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
