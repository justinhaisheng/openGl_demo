package com.haisheng.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.haisheng.opengl.R;
import com.haisheng.opengl.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @创建者 luhaisheng
 * @创建时间 2020/7/13 17:02
 * @描述
 */
public class QualrilaterRender implements GLSurfaceView.Renderer {

    private static final String TAG = QualrilaterRender.class.getSimpleName();
    //顶点坐标系
//    private final float[] vertexData = {
//            -1f, 0.0f,
//            0.0f, 1.0f,
//            1f, 0.0f,
//            1f, 0.0f,
//            0.0f,-1.0f,
//            -1f, 0.0f};


        private final float[] vertexData = {
            -1f, 0.0f,
            0.0f, 1.0f,
            0.0f, -1f,
            1f, 0.0f,
            };



    private int mProgram;

    private int mPositionHandler;
    private int mColorHandler;

    public QualrilaterRender(Context context){
       this.mContext = context;
        mProgram = 0;
       mVertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4) //float 占4个字节
               .order(ByteOrder.nativeOrder())//native 层 申请数据
               .asFloatBuffer()
               .put(vertexData);

       mVertexBuffer.clear();//初始化位置归0
   }

    Context  mContext;
    private FloatBuffer mVertexBuffer;//存储顶点坐标

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG,"onSurfaceCreated");
        String vertexSource = ShaderUtil.readRawText(mContext, R.raw.vertex_shader);
        String framentSource = ShaderUtil.readRawText(mContext,R.raw.fragment_shader);
        mProgram = ShaderUtil.createProgram(vertexSource, framentSource);
        if (mProgram >0){
           //得到着色器中的属性
            mPositionHandler = GLES20.glGetAttribLocation(mProgram,"av_Position");
            mColorHandler = GLES20.glGetUniformLocation(mProgram,"af_Color");
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG,"onSurfaceChanged width:"+width+" height:"+height);
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG,"onDrawFrame mProgram:"+mProgram);
        if (mProgram>0){

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
             GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
            //使用源程序
            GLES20.glUseProgram(mProgram);
            //使顶点属性数组有效：
            GLES20.glEnableVertexAttribArray(mPositionHandler);
            //为片源属性赋值
            GLES20.glUniform4f(mColorHandler,1f, 0, 0, 1f);
            //为顶点属性赋值：
            GLES20.glVertexAttribPointer(mPositionHandler, 2, GLES20.GL_FLOAT, false, 2*4, mVertexBuffer);
            //绘制三角形
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
        }

    }
}
