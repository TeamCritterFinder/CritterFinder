package com.codepath.apps.critterfinder.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codepath.apps.critterfinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by smacgregor on 3/12/16.
 */
public class ImageGalleryAdapter extends PagerAdapter {

    private List<String> mImageUrls;
    private Context mContext;

    public ImageGalleryAdapter(Context context, List<String> imageUrls) {
        super();
        mImageUrls = imageUrls;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.item_gallery_image, container, false);

        ImageView imageView = (ImageView) layout.findViewById(R.id.image_pet);
        Picasso.with(mContext).
                load(mImageUrls.get(position)).
                placeholder(R.drawable.ic_launcher).
                //resize(DeviceDimensionsHelper.getDisplayWidth(getContext()), 0).
                        into(imageView);

        // now load the item
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
