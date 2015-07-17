package com.mikimedia.demo.imageslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ImageSliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImageSliderController isc = new ImageSliderController(this);
//        isc.setAdapter();
//        setContentView(isc.onCreate());

    }

//    private static class ImageAdapter extends FragmentStatePagerAdapter {
//
//        private final String[] rawImageURI;
//
//        // Build a Constructor and assign the passed Values to appropriate values in the class
//        public ImageAdapter(FragmentManager fragg, ImageMemo memo) {
//            super(fragg);
//            this.rawImageURI = memo.rawImageURI;
//        }
//
//        //This method return the fragment for the every position in the View Pager
//        @Override
//        public Fragment getItem(int position) {
//            ImageViewFragment fragment = new ImageViewFragment();
//            fragment.init(rawImageURI[position]);
//            return fragment;
//        }
//
//        // This method return the Number of tabs for the tabs Strip
//        @Override
//        public int getCount() {
//            return rawImageURI.length;
//        }
//    }
//
//    public static class ImageViewFragment extends Fragment {
//
//        private static final String BUNDLE_ASSET = "asset";
//
//        String asset;
//
//        void init(String asset) {
//            this.asset = asset;
//        }
//
//        private Target wrapper = null;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View parentView = inflater.inflate(R.layout.imageslider_imageview, container, false);
//
//            if (savedInstanceState != null) {
//                if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
//                    asset = savedInstanceState.getString(BUNDLE_ASSET);
//                }
//            }
//
//            if (asset != null) {
//                if (wrapper == null) {
//                    SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)
//                            parentView.findViewById(R.id.image);
//                    imageView.setMinimumDpi(50);
//                    imageView.setDoubleTapZoomScale(8F);
//                    wrapper = new ImageWrapper(imageView);
//                }
//
//                PicassoHandle.get(getActivity()).load(asset, wrapper);
//            }
//
//            return parentView;
//        }

//        @Override
//        public void onSaveInstanceState(Bundle outState) {
//            super.onSaveInstanceState(outState);
////            View rootView = getView();
////                if (rootView != null) {
////                    outState.putString(BUNDLE_ASSET, asset);
////            }
//        }
//    }
}