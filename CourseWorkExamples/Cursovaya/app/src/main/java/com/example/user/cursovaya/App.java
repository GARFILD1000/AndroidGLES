package com.example.user.cursovaya;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        uploadObjFiles();
    }

    private void uploadObjFiles(){
        File dir = new File(Environment.getExternalStorageDirectory() + "/objects");
        dir.mkdir();
        copyFiletoExternalStorage(R.raw.banana_obj, "banana.obj");
        copyFiletoExternalStorage(R.raw.bottle_obj, "bottle.obj");
        copyFiletoExternalStorage(R.raw.candle_obj, "candle.obj");
        copyFiletoExternalStorage(R.raw.carrot_obj, "carrot.obj");
        copyFiletoExternalStorage(R.raw.orange_obj, "orange.obj");
        copyFiletoExternalStorage(R.raw.table_obj, "table.obj");
        copyFiletoExternalStorage(R.raw.tomato_obj, "tomato.obj");
    }

    private void copyFiletoExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory() + "/objects/" + resourceName;
        try{
            InputStream in = getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
