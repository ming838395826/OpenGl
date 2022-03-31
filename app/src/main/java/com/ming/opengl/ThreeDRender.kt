package com.ming.opengl

import android.opengl.GLSurfaceView
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLU
import com.ming.opengl.util.BufferUtil
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig


class ThreeDRender: GLSurfaceView.Renderer {

    private var rot = 0.0f

    //顶点数组
    private val vertices: FloatBuffer = BufferUtil.iBuffer(
        floatArrayOf(
            0f, -0.525731f, 0.850651f,
            0.850651f, 0f, 0.525731f,
            0.850651f, 0f, -0.525731f,
            -0.850651f, 0f, -0.525731f,
            -0.850651f, 0f, 0.525731f,
            -0.525731f, 0.850651f, 0f,
            0.525731f, 0.850651f, 0f,
            0.525731f, -0.850651f, 0f,
            -0.525731f, -0.850651f, 0f,
            0f, -0.525731f, -0.850651f,
            0f, 0.525731f, -0.850651f,
            0f, 0.525731f, 0.850651f
        )
    )

    //颜色数组
    private val colors: FloatBuffer = BufferUtil.iBuffer(
        floatArrayOf(
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.5f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            0.5f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.5f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 0.5f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.5f, 1.0f
        )
    )

    //索引数组
    private val icosahedronFaces: ByteBuffer = BufferUtil.iBuffer(
        byteArrayOf(
            1, 2, 6,
            1, 7, 2,
            3, 4, 5,
            4, 3, 8,
            6, 5, 11,
            5, 6, 10,
            9, 10, 2,
            10, 9, 3,
            7, 8, 9,
            8, 7, 0,
            11, 0, 1,
            0, 11, 4,
            6, 2, 10,
            1, 6, 11,
            3, 5, 10,
            5, 4, 11,
            2, 7, 9,
            7, 1, 0,
            3, 9, 8,
            4, 8, 0
        )
    )

    override fun onDrawFrame(gl: GL10) {
        // TODO Auto-generated method stub

        // 首先清理屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        // 设置模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW)

        //重置矩阵
        gl.glLoadIdentity()

        // 视点变换
        GLU.gluLookAt(gl, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1f, 0f)

        //平移操作
        gl.glTranslatef(0.0f, 0.0f, -3.0f)

        //旋转操作
        gl.glRotatef(rot, 1.0f, 1.0f, 1.0f)

        //缩放操作
        gl.glScalef(3.0f, 3.0f, 3.0f)

        //允许设置顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)

        //允许设置颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY)

        //设置顶点数组
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices)

        //设置颜色数组
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors)

        //绘制
        //gl.glDrawElements(GL10.GL_TRIANGLES, 60, GL10.GL_UNSIGNED_BYTE, icosahedronFaces);
        for (i in 1..30) {
            gl.glLoadIdentity()
            gl.glTranslatef(0.0f, -1.5f, -3.0f * i.toFloat())
            gl.glRotatef(rot, 1.0f, 1.0f, 1.0f)
            gl.glDrawElements(GL10.GL_TRIANGLES, 60, GL10.GL_UNSIGNED_BYTE, icosahedronFaces)
        }

        //取消顶点数组和颜色数组的设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY)

        //更改旋转角度
        rot += 0.5f
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        // TODO Auto-generated method stub
        val ratio = width.toFloat() / height

        // 设置视口(OpenGL场景的大小)
        gl.glViewport(0, 0, width, height)

        // 设置投影矩阵为透视投影
        gl.glMatrixMode(GL10.GL_PROJECTION)

        // 重置投影矩阵（置为单位矩阵）
        gl.glLoadIdentity()

        //创建一个透视投影矩阵（设置视口大小）
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 1.0f, 1000.0f)

        //创建一个正交投影矩阵
        //gl.glOrthof(-ratio, ratio, -1, 1, 1.0f, 1000.0f);
    }


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        // TODO Auto-generated method stub

        //告诉系统需要对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST)

        //设置清理屏幕的颜色
        gl.glClearColor(0f, 0f, 0f, 1f)

        //启用深度缓存
        gl.glEnable(GL10.GL_DEPTH_TEST)
    }
}