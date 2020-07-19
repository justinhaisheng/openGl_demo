package com.haisheng.opengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
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
public class TextureRender implements GLSurfaceView.Renderer {

    private static final String TAG = TextureRender.class.getSimpleName();
    //顶点坐标系
    private final float[] vertexData = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f,};

    //纹理坐标
    private final float[] textureData = {1f, 0f, 0f, 0f, 1f, 1f, 0f, 1f};


    private int mProgram;

    private int mAVPositionHandler;//代表顶点
    private int mAFPositionHandler;//代表纹理

    public TextureRender(Context context) {
        this.mContext = context;
        mProgram = 0;
        mVertexBuffer = ByteBuffer.allocateDirect(textureData.length * 4) //float 占4个字节
                .order(ByteOrder.nativeOrder())//native 层 申请数据
                .asFloatBuffer().put(vertexData);
        mVertexBuffer.clear();//初始化位置归0

        mTextureBuffer = ByteBuffer.allocateDirect(vertexData.length * 4) //float 占4个字节
                .order(ByteOrder.nativeOrder())//native 层 申请数据
                .asFloatBuffer().put(textureData);
        mTextureBuffer.clear();
    }

    Context mContext;
    private FloatBuffer mVertexBuffer;//存储顶点坐标
    private FloatBuffer mTextureBuffer;//存储纹理坐标

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated");
        String vertexSource = ShaderUtil.readRawText(mContext, R.raw.vertex_tex_shader);
        String framentSource = ShaderUtil.readRawText(mContext, R.raw.fragment_tex_shader);
        mProgram = ShaderUtil.createProgram(vertexSource, framentSource);
        if (mProgram > 0) {
            //得到着色器中的属性
            mAVPositionHandler = GLES20.glGetAttribLocation(mProgram, "av_Position");
            mAFPositionHandler = GLES20.glGetAttribLocation(mProgram, "af_Position");

            //创建和绑定纹理
            int[] textureIds = new int[1];
            GLES20.glGenTextures(1,textureIds,0);
            if(textureIds[0] == 0)
            {
                return;
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureIds[0]);

            //设置环绕和过滤方式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);


            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.og);
            if(bitmap == null)
            {
                return;
            }

            //设置图片（bitmap）到纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);

            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged width:" + width + " height:" + height);
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "onDrawFrame mProgram:" + mProgram);
        if (mProgram > 0) {

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            //使用源程序
            GLES20.glUseProgram(mProgram);
            //使顶点属性数组有效：
            GLES20.glEnableVertexAttribArray(mAVPositionHandler);
            //为顶点属性赋值：
            GLES20.glVertexAttribPointer(mAVPositionHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mVertexBuffer);

            //使顶点属性数组有效：
            GLES20.glEnableVertexAttribArray(mAFPositionHandler);
            //为顶点属性赋值：
            GLES20.glVertexAttribPointer(mAFPositionHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
            //绘制
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        }

    }
}
