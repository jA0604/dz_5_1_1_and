package com.androidakbar.dz_5_1_1_and;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private List<Sample> listSample;
    private SampleFileManager sfManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        String file_name = getResources().getString(R.string.str_file_name);
        listSample = new ArrayList<>();
        sfManager = new SampleFileManager(listSample, file_name);

        //заполнение списка названий приложений из внешнего файла
        sfManager.SetContentFromFile(this, listSample);

        Button btnSave = (Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etTitle = (EditText)findViewById(R.id.et_title);
                EditText etCategory = (EditText)findViewById(R.id.et_category);
                EditText etAutor = (EditText)findViewById(R.id.et_autor);
                Sample sam = new Sample(etTitle.getText().toString(), etCategory.getText().toString(), etAutor.getText().toString());
                listSample.add(sam);

                sfManager.SaveTextToFile(AddActivity.this, listSample);

                finish();
            }
        });
    }
}