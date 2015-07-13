package com.jattcode.oss.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jattcode.android.fragment.Screen;
import com.jattcode.android.fragment.ScreenCompatActivity;
import com.jattcode.android.fragment.ScreenSwitcher;

/**
 * Created by Javan on 13/7/2015.
 */
public class RandomFragment extends Fragment implements Screen {

    private TextView getTextView() {
        return (TextView) parentView.findViewById(android.R.id.text1);
    }

    private Button getButtonStartActivity() {
        return (Button) parentView.findViewById(android.R.id.button2);
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

        parentView = inflater.inflate(R.layout.fragment_demo_random, container, false);
        context = getActivity();

        onInitViews();
        return parentView;
    }

    private void onInitViews() {

        getTextView().setText(String.valueOf(System.currentTimeMillis()));

        getButtonStartActivity().setOnClickListener(clicker);
        getButtonStartFragment().setOnClickListener(clicker);
    }

    private final View.OnClickListener clicker = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
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
