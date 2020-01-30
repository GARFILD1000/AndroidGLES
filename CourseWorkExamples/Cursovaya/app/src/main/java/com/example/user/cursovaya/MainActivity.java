package com.example.user.cursovaya;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
    Renderer render = new Renderer(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView canvas = findViewById(R.id.surface_view);
        canvas.setEGLContextClientVersion(2);
        canvas.setRenderer(render);
        canvas.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.assignment_menu_item: {
                AlertDialog.Builder builder1;
                builder1 = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                builder1.setMessage("Создайте программу в которой нарисован стол на OpenGL ES 2.0.");
                builder1.setCancelable(true);
                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}