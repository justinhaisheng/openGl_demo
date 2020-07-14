package com.haisheng.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @创建者 luhaisheng
 * @创建时间 2020/7/14 10:32
 * @描述
 */
public class ShaderUtil {

    private static final String TAG = ShaderUtil.class.getSimpleName();

    public static String readRawText(Context context, int rawId) {

        InputStream inputStream = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    //加载shader
    public static int loadShader(int shaderType,String source){
        //创建shader
        int shader = GLES20.glCreateShader(shaderType);
        if (shader!=0){
            //加载shader
            GLES20.glShaderSource(shader,source);
            GLES20.glCompileShader(shader);
            //编译shader
            int[] compile = new int[1];
            GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compile,0);
            if (compile[0] != GLES20.GL_TRUE){
                Log.e(TAG, "shader compile error");
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }else{
            Log.e(TAG, "shader create error");
        }
        return shader;
    }

    //创建渲染程序
    public static int createProgram(String vertexSource,String framentSource){

        if (TextUtils.isEmpty(vertexSource) || TextUtils.isEmpty(framentSource)){
            Log.e(TAG, "TextUtils.isEmpty(vertexSource) || TextUtils.isEmpty(framentSource)");
            return 0;
        }

        //顶点
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);//
        if (vertexShader == 0){
            return 0;
        }
        //片源
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,framentSource);
        if (fragmentShader == 0){
            return 0;
        }
        //创建一个渲染程序：
        int program = GLES20.glCreateProgram();
        if (program!=0){
            //将着色器程序添加到渲染程序中
            GLES20.glAttachShader(program,vertexShader);
            GLES20.glAttachShader(program,fragmentShader);
            //链接程序
            GLES20.glLinkProgram(program);
            //检查链接是否成功
            int[] linkstatus = new int[1];
            GLES20.glGetProgramiv(program,GLES20.GL_LINK_STATUS,linkstatus,0);

            if (linkstatus[0]!=GLES20.GL_TRUE){
                Log.e(TAG, "program link error");
                GLES20.glDeleteProgram(program);
                program = 0;
            }

        }

        return program;

    }



}
