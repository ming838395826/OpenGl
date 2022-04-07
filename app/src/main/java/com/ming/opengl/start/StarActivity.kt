package com.ming.opengl.start

import android.content.Context
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ming.opengl.R

/**
 * @Description 星星
 * @Author ming
 * @Date 2022/3/21 23:53
 */
class StarActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var glView: GLSurfaceView
    private lateinit var mRender: StarRender

    private var mLastX = 0f
    private var mLastY = 0f
    private val TOUCH_SCALE_FACTOR = 180.0f / 320 //角度缩放比例


    companion object {
        fun open(context: Context, cameraType: Int) {
            context.startActivity(Intent(context, StarActivity::class.java).apply {
                this.putExtra("TYPE", cameraType)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        glView = findViewById(R.id.gl_view)
        glView.setOnTouchListener(this)
        //没设置会导致 创建着色器崩溃
        glView.setEGLContextClientVersion(3)
        glView.requestFocus();//获取焦点
        glView.isFocusableInTouchMode = true;//设置为可触控
        mRender = StarRender(glView, intent.getIntExtra("TYPE",0))
        glView.setRenderer(mRender)
        glView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY//设置渲染模式为主动渲染
    }

    override fun onResume() {
        super.onResume()
        glView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glView.onPause()
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val dy: Float = y - mLastX //计算触控位置的Y位移
                val dx: Float = x - mLastX //计算触控位置的X位移
                for (item in mRender.startList) {//设置各个六角星绕x轴、y轴旋转的角度
                    item.mYAngle += dx * TOUCH_SCALE_FACTOR
                    item.mYAngle += dy * TOUCH_SCALE_FACTOR
                }
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

}