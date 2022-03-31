package com.ming.opengl

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLU
import com.ming.opengl.util.BufferUtil


class TwoDRenderer : GLSurfaceView.Renderer {
    var one = 0x10000 //16进制 等于65536
    val bufferMaxColor =  0.5f
    //三角形
    private val triggerBuffer = BufferUtil.iBuffer(
        intArrayOf(
            0, one, 0,    //上顶点
            -one, -one, 0,    //左下点
            one, -one, 0 //右下点
        )
    )

    private val quaterBuffer = BufferUtil.iBuffer(
        intArrayOf(
            one, one, 0,
            -one, one, 0,
            one, -one, 0,
            -one, -one, 0
        )
    )

    //三角形的顶点颜色值(r,g,b,a)
    private val colorBuffer = BufferUtil.iBuffer(
        floatArrayOf(
            bufferMaxColor, 0f, 0f, bufferMaxColor,
            0f, bufferMaxColor, 0f, bufferMaxColor,
            0f, 0f, bufferMaxColor, bufferMaxColor
        )
    )

    var roation = 0f

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        // 告诉系统需要对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        // 设置清理屏幕的颜色
        gl.glClearColor(0f, 0f, 0f, 1f);

        // 启用深度缓存
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

        val ratio = width.toFloat() / height
        // 设置视口(OpenGL场景的大小)
        gl.glViewport(0, 0, width, height)
        // 设置投影矩阵为透视投影
        gl.glMatrixMode(GL10.GL_PROJECTION)

        // 重置投影矩阵（置为单位矩阵）
        gl.glLoadIdentity();

        // 创建一个透视投影矩阵（设置视口大小）
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
    }

    override fun onDrawFrame(gl: GL10) {
        // 首先清理屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        // 设置模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW)

        // 重置矩阵
        gl.glLoadIdentity()

        // 视点变换(前三个眼睛， 中间3个 视点 后面3个 脑袋方向)
        GLU.gluLookAt(gl, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1f, 0f)

        // 设置模型位置(圆点再中间)
        gl.glTranslatef(0f, 0.0f, 0.0f)
        //设置旋转(Y轴) (非0 表示绕哪个轴转动)
        gl.glRotatef(roation, 0.0f, 1.0f, 0.0f);
        // 允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        // 允许设置颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
        //设置不平滑过渡
        gl.glShadeModel(GL10.GL_SMOOTH)  //GL_SMOOTH

        //设置颜色数组
        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
        // 设置三角形的顶点数据
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer)
        //绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3)
        //关闭颜色数组的设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        // 重置当前的模型观察矩阵
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f)
        gl.glLoadIdentity()

        // 画正方形
        gl.glTranslatef(0.0f, 0.0f, -6.0f)
        gl.glRotatef(roation,0f,1f,0f)
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer)
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4)
        /* 绘制出来则是线框 */
        //gl.glDrawArrays(GL10.GL_LINES, 0, 4);
        //关闭顶点数组
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        roation += 0.5f
    }
}