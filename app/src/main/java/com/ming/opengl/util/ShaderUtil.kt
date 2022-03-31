package com.ming.opengl.util

import android.content.res.Resources
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import java.nio.charset.Charset
import android.opengl.GLES30
import java.lang.RuntimeException


/**
 * Date: 2022/3/31 15:29
 * @Author: ming
 * @Description:
 */
object ShaderUtil {
    //从sh脚本中加载shader内容的方法
    fun loadFromAssetsFile(fname: String?, r: Resources): String {
        var result = ""
        try {
            val `in`: InputStream = r.assets.open(fname)
            var ch = 0
            val baos = ByteArrayOutputStream()
            while (`in`.read().also { ch = it } != -1) {
                baos.write(ch)
            }
            val buff: ByteArray = baos.toByteArray()
            baos.close()
            `in`.close()
            result = String(buff, Charset.forName("UTF-8"))
            result = result.replace("\\r\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    //加载制定shader的方法
    private fun loadShader(
        shaderType: Int,  //shader的类型  GLES30.GL_VERTEX_SHADER   GLES30.GL_FRAGMENT_SHADER
        source: String //shader的脚本字符串
    ): Int {
        //创建一个新shader
        var shader = GLES30.glCreateShader(shaderType)
        //若创建成功则加载shader
        if (shader != 0) {
            //加载shader的源代码
            GLES30.glShaderSource(shader, source)
            //编译shader
            GLES30.glCompileShader(shader)
            //存放编译成功shader数量的数组
            val compiled = IntArray(1)
            //获取Shader的编译情况
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0)
            if (compiled[0] == 0) { //若编译失败则显示错误日志并删除此shader
                LogUtils.e("Could not compile shader $shaderType:")
                LogUtils.e(GLES30.glGetShaderInfoLog(shader))
                GLES30.glDeleteShader(shader)
                shader = 0
            }
        }
        return shader
    }

    //创建shader程序的方法
    /**
     * @param vertexSource 顶点着色器
     * @param fragmentSource 片源着色器
     */
    fun createProgram(vertexSource: String, fragmentSource: String): Int {
        //加载顶点着色器
        val vertexShader: Int = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource)
        if (vertexShader == 0) {
            return 0
        }

        //加载片元着色器
        val pixelShader: Int = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource)
        if (pixelShader == 0) {
            return 0
        }

        //创建程序
        var program = GLES30.glCreateProgram()
        //若程序创建成功则向程序中加入顶点着色器与片元着色器
        if (program != 0) {
            //向程序中加入顶点着色器
            GLES30.glAttachShader(program, vertexShader)
            checkGlError("glAttachShader")
            //向程序中加入片元着色器
            GLES30.glAttachShader(program, pixelShader)
            checkGlError("glAttachShader")
            //链接程序
            GLES30.glLinkProgram(program)
            //存放链接成功program数量的数组
            val linkStatus = IntArray(1)
            //获取program的链接情况
            GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0)
            //若链接失败则报错并删除程序
            if (linkStatus[0] != GLES30.GL_TRUE) {
                LogUtils.e("Could not link program: ")
                LogUtils.e(GLES30.glGetProgramInfoLog(program))
                GLES30.glDeleteProgram(program)
                program = 0
            }
        }
        return program
    }

    //检查每一步操作是否有错误的方法
    private fun checkGlError(op: String) {
        var error: Int
        while (GLES30.glGetError().also { error = it } != GLES30.GL_NO_ERROR) {
            LogUtils.e("$op: glError $error")
            throw RuntimeException("$op: glError $error")
        }
    }
}