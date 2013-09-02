package com.example.flashcards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // references to our images
    private String[] mDeckNames;
    
    public ImageAdapter(Context c, String [] flashCardDeckNames) {
        mContext = c;
        mDeckNames = flashCardDeckNames;
    }

    public int getCount() {
        return mDeckNames.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	RelativeLayout relativeLayout = new RelativeLayout(mContext);
    	relativeLayout.setPadding(8, 8, 8, 8);
        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.smallcard);
        TextView textView;
        textView = new TextView(mContext);
        textView.setText(mDeckNames[position]);
        relativeLayout.addView(imageView);
        relativeLayout.addView(textView);
        return relativeLayout;
    }

}