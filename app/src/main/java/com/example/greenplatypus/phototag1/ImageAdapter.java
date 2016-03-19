package com.example.greenplatypus.phototag1;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by kero on 10/24/2015.
 */

public class ImageAdapter extends BaseAdapter {
    BasePhotoTagActivity wantedScene;

    public Integer[] mThumbIds = {R.drawable.char_1, R.drawable.char_2, R.drawable.char_3, R.drawable.char_4,
            R.drawable.char_5, R.drawable.char_6, R.drawable.char_7, R.drawable.char_8,
    };

    // Constructor
    public ImageAdapter(BasePhotoTagActivity c) {
        wantedScene = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        Object image = mThumbIds[position];
        if(position == 0){
            Object selfie = getSelfie();
            if (selfie != null){
                image = selfie;
            }
        }
        return image;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(wantedScene);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(230, 230));
        return imageView;
    }

    Object getSelfie(){
        Log.d("ImageAdapter", "finish selfie");
        return wantedScene.getSelfie();
    }
}