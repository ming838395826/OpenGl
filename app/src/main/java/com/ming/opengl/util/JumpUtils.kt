package com.ming.opengl.util

import android.content.Context
import com.ming.opengl.ClickType
import com.ming.opengl.start.StarActivity

/**
 * @Description 跳转帮助
 * @Author ming
 * @Date 2022/3/21 23:58
 */
object JumpUtils {

    fun open(context: Context, clickType: ClickType) {
        when (clickType) {
            ClickType.START_ORTHO -> {
                StarActivity.open(context, 0)
            }
        }
    }
}