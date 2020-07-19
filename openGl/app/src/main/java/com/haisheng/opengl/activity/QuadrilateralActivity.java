package com.haisheng.opengl.activity;

import android.os.Bundle;

import com.haisheng.opengl.CustomSurfaceView;
import com.haisheng.opengl.R;
import com.haisheng.opengl.render.QualrilaterRender;

import androidx.appcompat.app.AppCompatActivity;

public class QuadrilateralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadrilateral);
        CustomSurfaceView surfaceView = findViewById(R.id.csv);
        surfaceView.setRenderer(new QualrilaterRender(this));
    }
}
