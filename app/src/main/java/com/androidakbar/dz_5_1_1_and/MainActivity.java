package com.androidakbar.dz_5_1_1_and;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intentAdd;
    private SwipeRefreshLayout swrList;

    private List<Sample> listSample;
    private SampleFileManager sfManager;
    private SampleAdapter sampleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        appToolbar.setTitle(R.string.name_dz);
        appToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryText));
        appToolbar.inflateMenu(R.menu.main_menu);
        setSupportActionBar(appToolbar);

        String file_name = getResources().getString(R.string.str_file_name);
        listSample = new ArrayList<>();
        sfManager = new SampleFileManager(listSample, file_name);

        //начальное заполнение списка примеров из ресурса
        listSample = InitialSampleList(sfManager);

        //инициализация списка и отображение
        sampleAdapter = new SampleAdapter(this, listSample, sfManager);
        ListView lv = findViewById(R.id.lv_container);
        lv.setAdapter(sampleAdapter);

        intentAdd = new Intent(MainActivity.this, AddActivity.class);


        swrList = findViewById(R.id.swr_refresh_list);

        swrList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listSample.clear();
                sfManager.SetContentFromFile(MainActivity.this, listSample);
                sampleAdapter.notifyDataSetChanged();
                swrList.setRefreshing(false);
            }
        });
    }

    private List<Sample> InitialSampleList(SampleFileManager sfm) {
        List<Sample> list = new ArrayList<>();
        String[] sampleLine = getResources().getStringArray(R.array.sa_sample);
        for (String s : sampleLine) {
            String[] samStr = s.split("/");
            Sample sample = new Sample(samStr[0], samStr[1], samStr[2]);
            list.add(sample);
        }
        //начальная загрузка данных в файл во внешней приватной памяти
        sfm.SaveTextToFile(this, list);

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mm_add: {
                startActivity(intentAdd);
                return true;
            }

        }

        return false;
    }
}