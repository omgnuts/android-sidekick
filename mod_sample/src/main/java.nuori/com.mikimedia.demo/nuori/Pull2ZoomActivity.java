package com.mikimedia.demo.nuori;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mikimedia.android.nuori.PullToZoomListView;
import com.mikimedia.demo.ImageLoader;
import com.mikimedia.demo.R;

public class Pull2ZoomActivity extends AppCompatActivity {
	PullToZoomListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull2zoom_activity);
		listView = (PullToZoomListView)findViewById(R.id.listview);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
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
//        listView.getHeaderView().setImageResource(R.mipmap.horizontal_image);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);

		ImageLoader.with(this).loadSampleImage(listView.getHeaderView());
	}

}
