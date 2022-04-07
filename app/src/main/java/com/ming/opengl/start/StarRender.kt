package com.ming.opengl.start

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.SurfaceView
import com.ming.opengl.util.MatrixState
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @Description 渲染器
 * @Author ming
 * @Date 2022/3/22 23:18
 */
class StarRender(private val surfaceView: SurfaceView, val cameraType: Int) :
    GLSurfaceView.Renderer {

    var startList = mutableListOf<SixPointedStar>()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
        //创建六角星数组中的各个六角星
        repeat(6) { i ->
            //改变z轴 观察正交相加 有没变大
            //0.5相对于整个视图的大小
            startList.add(SixPointedStar(surfaceView, 0.2f, 0.5f, -0.3f * i))
        }
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val ratio = width.toFloat() / height
        GLES30.glViewport(0, 0, width, height)
        if (cameraType == 0) {
            //设置正交投影
            MatrixState.setProjectOrtho(-ratio, ratio, -1f, 1f, 1f, 10f)
        } else {
            //设置透视投影
            MatrixState.setProjectFrustum(-ratio * 0.4f, ratio * 0.4f, -1 * 0.4f, 1 * 0.4f, 1f, 50f)
        }
        //设置摄像机
        MatrixState.setCamera(0f, 0f, 6f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
    }


    override fun onDrawFrame(gl: GL10?) {
        //清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
        //循环绘制各个六角星
        for (item in startList) {
            item.drawSelf()
        }
    }
}