
package com.jnicube.myapplication;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;


public class MainActivity extends Activity {

    GLSurfaceView GLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLView = new GLView(this);
        setContentView(GLView);
    }

    static {
        System.loadLibrary("cube");
    }
    
}
