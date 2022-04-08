package com.ming.opengl.cube

import android.content.Context
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ming.opengl.start.StarRender

/**
 * Date: 2022/4/8 14:59
 * @Author: ming
 * @Description: 演示平移
 */
class CubeActivity : AppCompatActivity() {

    private lateinit var glView: GLSurfaceView
    private lateinit var mRender: StarRender

    companion object {
        fun open(context: Context, cameraType: Int) {
            context.startActivity(Intent(context, CubeActivity::class.java).apply {
                this.putExtra("TYPE", cameraType)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SimpleComposable() }
    }

    @Preview
    @Composable
    fun SimpleComposable() {
        Text("Hello World")
    }
}