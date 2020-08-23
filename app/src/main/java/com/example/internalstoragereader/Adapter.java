package com.example.internalstoragereader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {


    private Context mContext;
    private List<String> dPathList;
    private List<String> dNameList;

    public Adapter(Context mContext, List<String> dPaths, List<String> dNames) {
        this.mContext = mContext;
        this.dPathList = dPaths;
        this.dNameList = dNames;
    }

    @Override
    public int getCount() {
        return dNameList.size();
    }

    @Override
    public Object getItem(int i) {
        return dNameList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        List<String> fileList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_group, viewGroup, false);
        TextView dName = v.findViewById(R.id.dNames);

        ImageView arrow = v.findViewById(R.id.arrow);
        final ListView myDirectory=  v.findViewById(R.id.dNameList);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDirectory.getVisibility() == View.VISIBLE)
                {
                    myDirectory.setVisibility(View.GONE);
                    ((ImageView)view).setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.arrow_right));
                }
            }
        });

        dName.setText(dNameList.get(i));

        File[] files = new File(dPathList.get(i)).listFiles();
        fileList.clear();
        fileNameList.clear();
        if (files != null) {
            for (File file : files) {
                fileList.add(file.getPath());
                fileNameList.add(file.getName());
            }
        }



        Adapter directoryList=new Adapter(mContext,fileList, fileNameList);
        myDirectory.setAdapter(directoryList);

        myDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListView listView = view.findViewById(R.id.dNameList);
                ImageView Arrow = view.findViewById(R.id.arrow);
                if (listView.getVisibility() == View.GONE)
                {
                    listView.setVisibility(View.VISIBLE);
                    Arrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.arrow_down));
                }
                else
                {
                    listView.setVisibility(View.GONE);
                    Arrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.arrow_right));
                }
            }
        });

        return v;
    }
}