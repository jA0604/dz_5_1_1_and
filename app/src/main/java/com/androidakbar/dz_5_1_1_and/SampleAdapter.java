package com.androidakbar.dz_5_1_1_and;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends BaseAdapter {
    private List<Sample> sampleItem;
    private LayoutInflater inflater;
    private SampleFileManager sampleFileManager;
    private Context context;

    public SampleAdapter(Context context, List<Sample> sampleItem, SampleFileManager sampleFileManager) {
        if (sampleItem == null) {
            this.sampleItem = new ArrayList<>();
        } else {
            this.sampleItem = sampleItem;
        }
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sampleFileManager = sampleFileManager;
    }

    @Override
    public int getCount() {
        return sampleItem.size();
    }

    @Override
    public Sample getItem(int position) {
        if (position < sampleItem.size()) {
            return sampleItem.get(position);
        } else {
            return null;
        }

    }

    private void removeItem(int position) {
        sampleItem.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        Sample itemSample = sampleItem.get(position);

        ImageView image = view.findViewById(R.id.img_item);
        TextView title = view.findViewById(R.id.txt_app_name);
        TextView category = view.findViewById(R.id.txt_category);
        TextView autor = view.findViewById(R.id.txt_autor);
        ImageButton btnDel = view.findViewById(R.id.ibtn_del);

        title.setText(itemSample.getTitle());
        category.setText(itemSample.getCategory());
        autor.setText(itemSample.getAutor());

        final int po = position;

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(po);
                sampleFileManager.SaveTextToFile(context, sampleItem);
            }
        });

        return view;
    }
}
