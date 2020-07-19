package com.haisheng.opengl.activity;

import android.os.Bundle;

import com.haisheng.opengl.CustomSurfaceView;
import com.haisheng.opengl.R;
import com.haisheng.opengl.render.TextureRender;

import androidx.appcompat.app.AppCompatActivity;

public class TextureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        CustomSurfaceView surfaceView = findViewById(R.id.csv);
        surfaceView.setRenderer(new TextureRender(this));
    }
}
