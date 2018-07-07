package com.example.manpreetkaur.pet_treasure_Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.manpreetkaur.pet_treasure.R;

import java.util.List;

import pet_treasure_Model.Pet;

public class Pet_Adapter extends ArrayAdapter<Pet> {

    protected Context mContext;
    protected List<Pet> mPlanets;

    public Pet_Adapter(Context context, List<Pet> objects) {
        super(context, R.layout.single_row, objects);
        mContext = context;
        mPlanets = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.single_row, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageview);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.textview);

            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    holder.ratingBar.setFocusable(false);
                    return false;
                }
            });


            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();



        }

        Pet planet = mPlanets.get(position);
        holder.imageView.setImageResource(planet.getImage());
        holder.textView.setText(planet.getName());



        return convertView;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textView;
        RatingBar ratingBar;


    }

}
