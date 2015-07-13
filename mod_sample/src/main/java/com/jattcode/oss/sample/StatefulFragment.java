package com.jattcode.oss.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jattcode.android.fragment.Screen;
import com.jattcode.android.fragment.ScreenFragment;


public class StatefulFragment extends ScreenFragment implements Screen {

    private TextView getTextView() {
        return (TextView) parentView.findViewById(android.R.id.text1);
    }

    private Button getButtonSimulateProperty() {
        return (Button) parentView.findViewById(android.R.id.button1);
    }

    private Button getButtonDisplayProperty() {
        return (Button) parentView.findViewById(android.R.id.button3);
    }

    private Button getButtonStartActivity() {
        return (Button) parentView.findViewById(android.R.id.button2);
    }

    private ListView getListView() {
        return (ListView) parentView.findViewById(android.R.id.list);
    }

    private Button getButtonStartFragment() {
        return (Button) parentView.findViewById(R.id.button4);
    }

    Context context;
    View parentView;

    String[] items;
    private String property = "(property is unset)";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;

        context = getActivity();
        // unlike activity we can load models here.
        onLoadModel();
    }

    private void onLoadModel() {
        items = new String[] {
                "START",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "END"
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentView = inflater.inflate(R.layout.fragment_demo_stateful, container, false);

        onInitViews();

        return parentView;
    }

    private void onInitViews() {
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, items);

        getButtonSimulateProperty().setOnClickListener(clicker);
        getButtonDisplayProperty().setOnClickListener(clicker);
        getButtonStartActivity().setOnClickListener(clicker);
        getButtonStartFragment().setOnClickListener(clicker);
        getListView().setAdapter(adapter);

        /**
         * setFreezeText makes the textview save its state on orientation change
         */
        getTextView().setFreezesText(true);
    }

    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            int position;

            switch (v.getId()) {
                case android.R.id.button1: // getButtonSimulateProperty
                    property = "Coffee is beautiful.";
                    position = 10; // simulate click yogurt;
                    getListView().setSelection(position);
                    break;

                case android.R.id.button3: // getButtonDisplayProperty
                    try {
                        position = getListView().getFirstVisiblePosition();
                        String item = (String) getListView().getAdapter().getItem(position);
                        getTextView().setText(property + " And so is " + item);
                    } catch (NullPointerException e) {
                        getTextView().setText(property + " And so is " + "(unset)");
                    }

                    break;
                case android.R.id.button2: // getButtonStartActivity()
                    getSwitcher().changeScreen(SwitcherActivity.ScreenType.DEFAULT_FRAGMENT);
                    break;

                case R.id.button4: //getButtonStartFragment
                    getSwitcher().changeScreen(SwitcherActivity.ScreenType.RANDOM_FRAGMENT);
                    break;
            }
        }
    };

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("save:property", property);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            property = savedInstanceState.getString("save:property", null);
        }
    }

}
