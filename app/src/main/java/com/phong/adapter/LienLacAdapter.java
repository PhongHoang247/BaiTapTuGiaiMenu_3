package com.phong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phong.baitaptugiaimenu_3.R;
import com.phong.model.LienLac;

public class LienLacAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    public LienLacAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = this.context.getLayoutInflater().inflate(this.resource, null);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        TextView txtTen = (TextView) view.findViewById(R.id.txtTen);
        TextView txtSoDienThoai = (TextView) view.findViewById(R.id.txtSoDienThoai);
        LienLac lienlac = (LienLac) getItem(position);
        imgHinh.setImageResource(lienlac.getHinh());
        txtTen.setText(lienlac.getTen());
        txtSoDienThoai.setText(lienlac.getSoDienThoai());
        return view;
    }
}
