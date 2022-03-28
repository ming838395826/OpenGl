package com.ming.opengl.util

import android.opengl.Matrix

/**
 * @Description 存储系统矩阵状态的类
 * @Author ming
 * @Date 2022/3/23 22:09
 */
object MatrixState {

    private val mProjMatrix = FloatArray(16) // 4x4矩阵 投影用

    private val mVMatrix = FloatArray(16) // 摄像机位置朝向9参数矩阵

    private val mMVPMatrix: FloatArray? = null// 最后起作用的总变换矩阵

    //设置摄像机的方法
    fun setCamera(
        cx: Float,
        cy: Float,
        cz: Float,
        tx: Float,
        ty: Float,
        tz: Float,
        upx: Float,
        upy: Float,
        upz: Float
    ) {
        Matrix.setLookAtM(
            mVMatrix,  //存储生成矩阵元素的float[]类型数组
            0,  //填充起始偏移量
            cx, cy, cz,  //摄像机位置的X、Y、Z坐标
            tx, ty, tz,  //观察目标点X、Y、Z坐标
            upx, upy, upz //up向量在X、Y、Z轴上的分量
        )
    }

    //设置正交投影的方法
    fun setProjectOrtho(
        left: Float,
        right: Float,
        bottom: Float,
        top: Float,
        near: Float,
        far: Float
    ) {
        Matrix.orthoM(
            mProjMatrix,  //存储生成矩阵元素的float[]类型数组
            0,  //填充起始偏移量
            left, right,  //near面的left、right
            bottom, top,  //near面的bottom、top
            near, far //near面、far面与视点的距离
        )
    }
}