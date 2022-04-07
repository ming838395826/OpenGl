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

    private var mMVPMatrix: FloatArray? = null// 最后起作用的总变换矩阵

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

    //设置透视投影
    fun setProjectFrustum(
        left: Float,  // near面的left
        right: Float,  // near面的right
        bottom: Float,  // near面的bottom
        top: Float,  // near面的top
        near: Float,  // near面与视点的距离
        far: Float // far面与视点的距离
    ) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far)
    }

    //获取具体物体的总变换矩阵
    fun getFinalMatrix(spec: FloatArray?): FloatArray? { //生成物体总变换矩阵的方法
        mMVPMatrix = FloatArray(16) //创建用来存放最终变换矩阵的数组
        //将摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0)
        //将投影矩阵乘以上一步的结果矩阵得到最终变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0)
        return mMVPMatrix
    }
}