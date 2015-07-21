package com.mikimedia.demo.nuori;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikimedia.android.component.PicassoTopCropTransform;
import com.mikimedia.android.nuori.Nuori;
import com.mikimedia.android.nuori.NuoriParallaxRecyclerView;
import com.mikimedia.demo.ImageLoader;
import com.mikimedia.demo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class NuoriRecyclerViewActivity extends AppCompatActivity {

    private static String[] getDataItems() {
        String[] data = new String[200];
        for (int c = 0; c < data.length; c++) {
            data[c] = "String " + c;
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuori_recyclerview_activity);

        NuoriParallaxRecyclerView recyclerView = (NuoriParallaxRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        View header = LayoutInflater.from(this).inflate(R.layout.nuori_listview_header, recyclerView, false);
        HeaderAdapter adapter = new HeaderAdapter(this, getDataItems());
        adapter.setHeader(header);
        recyclerView.setAdapter(adapter);

        final ImageView imageView = (ImageView) header.findViewById(R.id.image);
        final Nuori nuori = Nuori.from(recyclerView)
                .setImageView(imageView)
                .setHeaderView(header)
                .into(false);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                nuori.notifyViewBoundsChanged();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageView.setImageDrawable(errorDrawable);
                nuori.notifyViewBoundsChanged();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageView.setImageDrawable(placeHolderDrawable);
                nuori.notifyViewBoundsChanged();
            }
        };

        final Point size = nuori.getPreCachedSize();
        final Transformation transformer = new PicassoTopCropTransform(size.x, size.y);
        ImageLoader.with(this).loadSampleImage(target, transformer);

//        imageView.setImageResource(R.mipmap.ic_launcher);
//        imageView.setImageResource(R.mipmap.horizontal_image);
//        imageView.setImageResource(R.mipmap.vertical_long);
//        ImageLoader.with(this).loadSampleImage(target);
//        ImageLoader.with(this).loadSampleImage(target, 4);
//        ImageLoader.with(this).loadSampleImage(imageView);
//        ImageLoader.with(this).loadSampleImage(imageView, 4);

    }

    private static class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        private VHHeader header;

        private final LayoutInflater inflater;

        private final String[] data;

        private HeaderAdapter(Context context, String[] data){
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        void setHeader(View header) {
            this.header = new VHHeader(header);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_HEADER: return header;
                case TYPE_ITEM:
                    View itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                    return new VHItem(itemView);
            }

            throw new RuntimeException("there is no type that matches the type " +
                    viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == TYPE_ITEM) {
                ((VHItem)holder).textView.setText(data[position]);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return data.length + 1;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private final static class VHItem extends RecyclerView.ViewHolder {
            TextView textView;

            public VHItem(View view) {
                super(view);
                textView = (TextView) view.findViewById(android.R.id.text1);
                view.setTag(this);
            }
        }

        private static class VHHeader extends RecyclerView.ViewHolder {
            View header;

            private VHHeader(View itemView) {
                super(itemView);
                header = itemView;
            }
        }
    }

}