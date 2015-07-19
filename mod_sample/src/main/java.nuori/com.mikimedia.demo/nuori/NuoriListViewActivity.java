package com.mikimedia.demo.nuori;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mikimedia.android.nuori.NuoriParallaxListView;
import com.mikimedia.demo.ImageLoader;
import com.mikimedia.demo.R;

public class NuoriListViewActivity extends AppCompatActivity {

    NuoriParallaxListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuori_listview_activity);

        listView = (NuoriParallaxListView) findViewById(R.id.list_view);

        View header = LayoutInflater.from(this).inflate(R.layout.nuori_listview_header, null);

        ImageView imageView = (ImageView) header.findViewById(R.id.image);
        ImageLoader.with(this).loadSampleImage(imageView);

        listView.setParallaxImageView(imageView);
        listView.addHeaderView(header);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{
                        "First Item",
                        "Second Item",
                        "Third Item",
                        "Fifth Item",
                        "Sixth Item",
                        "Seventh Item",
                        "Eighth Item",
                        "Ninth Item",
                        "Tenth Item",
                        "....."
                }
        );
        listView.setAdapter(adapter);
    }

}