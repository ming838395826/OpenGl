package com.ming.opengl

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {
    lateinit var glSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val contain = findViewById<FrameLayout>(R.id.fl_contain)
        glSurfaceView = GLSurfaceView(this)
        glSurfaceView.setRenderer(LightRender())
        contain.addView(glSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}