package com.haisheng.opengl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haisheng.opengl.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void trilateral(View view) {
        startActivity(new Intent(this,TrilateralActivity.class));
    }

    public void quadrilateral(View view) {
        startActivity(new Intent(this,QuadrilateralActivity.class));
    }

    public void texture(View view) {
        startActivity(new Intent(this,TextureActivity.class));
    }
}
