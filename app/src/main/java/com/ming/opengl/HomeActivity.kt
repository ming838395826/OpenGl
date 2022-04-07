package com.ming.opengl

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ming.opengl.util.LogUtils

/**
 * @Description 主界面
 * @Author ming
 * @Date 2022/3/21 23:25
 */
class HomeActivity : AppCompatActivity() {

    var listData = arrayListOf(
        Pair(ClickType.START_ORTHO, "星星正交相机"),
        Pair(ClickType.START_FRUSTUM, "星星透视相机")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val rvContent = findViewById<RecyclerView>(R.id.rv_content)
        val adapter = ContentAdapter()
        rvContent.adapter = adapter
        adapter.setData(listData)
    }
}