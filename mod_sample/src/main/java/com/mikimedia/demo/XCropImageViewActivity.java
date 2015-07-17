package com.mikimedia.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikimedia.android.view.ObjectPageAdapter;

public class XCropImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcropimageview);

        final TextView title = (TextView)findViewById(R.id.title_view);

        final ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new ImagePageAdapter(this, ImageLoader.dataUri));
        title.setText(pager.getAdapter().getPageTitle(0));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(pager.getAdapter().getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ImagePageAdapter extends ObjectPageAdapter {

        private class ViewHolder {
            ImageView image;

            ViewHolder(View itemView) {
                this.image = (ImageView) itemView.findViewById(R.id.image_view);
                itemView.setTag(this);
            }
        }

        private final String[] imgUri;

        private final LayoutInflater inflater;

        ImagePageAdapter(Context context, String[] imgUri) {
            this.inflater = LayoutInflater.from(context);
            this.imgUri = imgUri;
        }

        public String getPageTitle(int position) {
            return imgUri[position];
        }

        @Override
        public int getCount() {
            return imgUri.length;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.xcrop_imageview, null);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            bindView(holder, position);
            return view;
        }

            private void bindView(final ViewHolder holder, int position) {
                String asset = imgUri[position];
                ImageLoader.with(XCropImageViewActivity.this).load(asset, holder.image);
            }
    }

}
