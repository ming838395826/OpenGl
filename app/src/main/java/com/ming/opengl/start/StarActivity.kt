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

    private var mLastX = 0
    private var mLastY = 0

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
        glView.setRenderer(StarRender(glView))
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

            }
        }
        return true
    }

}