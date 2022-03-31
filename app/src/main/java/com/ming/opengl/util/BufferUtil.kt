package com.ming.opengl.util

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

object BufferUtil {
    @JvmStatic
    fun iBuffer(a: IntArray): IntBuffer {
        // 先初始化buffer,数组的长度*4,因为一个int占4个字节
        val mbb = ByteBuffer.allocateDirect(a.size * 4)
        mbb.order(ByteOrder.nativeOrder())
        val intBuffer = mbb.asIntBuffer()
        intBuffer.put(a)
        intBuffer.position(0)
        return intBuffer
    }
    @JvmStatic
    fun iBuffer(f: FloatArray): FloatBuffer {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect (f.size * 4);
        //以本机字节顺序来修改此缓冲区的字节顺序
        bb.order(ByteOrder.nativeOrder());
        val floatBuffer = bb.asFloatBuffer()
        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        floatBuffer.put(f);
        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
        floatBuffer.position(0)
        return floatBuffer
    }
    @JvmStatic
    fun iBuffer(f: ByteArray): ByteBuffer {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect (f.size * 4);
        //以本机字节顺序来修改此缓冲区的字节顺序
        bb.order(ByteOrder.nativeOrder());
        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        bb.put(f);
        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
        bb.position(0)
        return bb
    }

}