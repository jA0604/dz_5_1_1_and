package com.androidakbar.dz_5_1_1_and;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SampleFileManager {
    List<Sample> sampleList;
    String fileName;

    public SampleFileManager(List<Sample> sampleList, String fileName) {
        this.sampleList = sampleList;
        this.fileName = fileName;
    }

    //метод записи данных в файл
    public void SaveTextToFile(Context context, List<Sample> list) {
        if (isExternalStorageWritable()) {
            File txtFile = new File(context.getExternalFilesDir(null), fileName);
            FileWriter fw = null;

            try {
                fw = new FileWriter(txtFile);
                for (Sample s : list) {
                    fw.write(s.getTitle() + "/" + s.getCategory() + "/" + s.getAutor() + ";");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //метод чтение данных из файла
    private String LoadText(Context context, List<Sample> list) {
        StringBuilder txtString = new StringBuilder();
        if (isExternalStorageWritable()) {
            File txtFile = new File(context.getExternalFilesDir(null), fileName);
            FileReader fr = null;
            BufferedReader br = null;
            String line = null;

            try {
                fr = new FileReader(txtFile);
                br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    txtString = txtString.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return txtString.toString();
    }

    //метод заполнения списка названий приложений из внешнего файла
    public void SetContentFromFile(Context context, List<Sample> list) {

        String text = LoadText(context, list);
        String[] itemSample = text.split(";");
        for (String s : itemSample) {
            String[] content = s.split("/");
            Sample item = new Sample(content[0], content[1], content[2]);
            list.add(item);
        }

    }

    //метод проверки доступности хранилища
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
