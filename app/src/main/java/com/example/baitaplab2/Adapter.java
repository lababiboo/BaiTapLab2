package com.example.baitaplab2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ArrayList<Contact> data;
    private Activity context;
    private LayoutInflater inflater;

    public Adapter(ArrayList<Contact> data, Activity context) {
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
            v = inflater.inflate(R.layout.contact,null);
        TextView txtName = v.findViewById(R.id.txtName);
        txtName.setText(data.get(position).getName());
        TextView txtPhone = v.findViewById(R.id.txtPhone);
        txtPhone.setText(data.get(position).getPhone());
        TextView txtEmail = v.findViewById(R.id.txtEmail);
        txtEmail.setText(data.get(position).getEmail());
        ImageView img = v.findViewById(R.id.image);
        String imageUrl = data.get(position).getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context).load(imageUrl).into(img);
            Log.d("a-adapter", "getView: " + imageUrl);
        }
        CheckBox cb = v.findViewById(R.id.cb);
        cb.setChecked(data.get(position).getStatus());
        Contact c = data.get(position);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                c.setStatus(isChecked) ;
            }
        });
        return v;
    }

}
