package com.haisheng.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * @创建者 luhaisheng
 * @创建时间 2020/7/13 16:58
 * @描述
 */
public class CustomSurfaceView extends GLSurfaceView {
    public CustomSurfaceView(Context context) {
        this(context,null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(new CustomRender(context));
    }
}
