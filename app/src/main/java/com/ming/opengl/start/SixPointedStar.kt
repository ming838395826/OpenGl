package com.ming.opengl.start

import android.opengl.GLES30
import android.opengl.Matrix
import android.view.SurfaceView
import com.ming.opengl.util.BufferUtil
import com.ming.opengl.util.ShaderUtil
import java.lang.Math.toRadians
import java.nio.FloatBuffer
import kotlin.math.*

/**
 * @Description 六角星
 * @Author ming
 * @Date 2022/3/28 23:12
 */
/**
 * 大的半径，小的半劲，z轴大小
 */
class SixPointedStar(val surfaceView: SurfaceView, r: Float, R: Float, z: Float) {

    val UNIT_SIZE = 1f

    private lateinit var mVertexShader: String//顶点着色器代码脚本
    private lateinit var mFragmentShader: String//片元着色器代码脚本
    private var mProgram: Int = 0//自定义渲染管线着色器程序id
    private var muMVPMatrixHandle = 0 //总变换矩阵引用
    private var maPositionHandle = 0 //顶点位置属性引用
    private var maColorHandle = 0 //顶点颜色属性引用


    private lateinit var mVertexBuffer: FloatBuffer
    private lateinit var mColorBuffer: FloatBuffer
    var mMMatrix = FloatArray(16) //具体物体的3D变换矩阵，包括旋转、平移、缩放
    var vCount = 0
    var mYAngle = 0f //绕y轴旋转的角度
    var mXAngle = 0f //绕x轴旋转的角度


    init {
        initVertexData(R, r, z)
        initShader()
    }

    /**
     * 生成矩阵数据
     */
    private fun initVertexData(R: Float, r: Float, z: Float) {
        val vertexList = mutableListOf<Float>()
        val itemAngle = 360 / 6
        repeat(6) { index ->
            val angle = 360 / 6 * index
            //三角形 3个顶点
            vertexList.addAll(arrayOf(0f, 0f, z))
            vertexList.addAll(
                arrayOf(
                    UNIT_SIZE * R * cos(toRadians(angle.toDouble())).toFloat(),
                    UNIT_SIZE * R * sin(toRadians(angle.toDouble())).toFloat(),
                    z
                )
            )
            vertexList.addAll(
                arrayOf(
                    UNIT_SIZE * r * cos(toRadians(angle + itemAngle / 2.toDouble())).toFloat(),
                    UNIT_SIZE * r * sin(toRadians(angle + itemAngle / 2.toDouble())).toFloat(),
                    z
                )
            )

            //第二个三角形
            vertexList.addAll(arrayOf(0f, 0f, z))
            vertexList.addAll(
                arrayOf(
                    UNIT_SIZE * r * cos(toRadians(angle + itemAngle / 2.toDouble())).toFloat(),
                    UNIT_SIZE * r * sin(toRadians(angle + itemAngle / 2.toDouble())).toFloat(),
                    z
                )
            )
            vertexList.addAll(
                arrayOf(
                    UNIT_SIZE * R * cos(toRadians(angle + itemAngle.toDouble())).toFloat(),
                    UNIT_SIZE * R * sin(toRadians(angle + itemAngle.toDouble())).toFloat(),
                    z
                )
            )
        }

        mVertexBuffer = BufferUtil.iBuffer(vertexList.toFloatArray())

        //生成颜色数组
        val colorList = mutableListOf<Float>()
        //因为xyz 得到所有顶点的数量
        repeat(vertexList.size / 3) { index ->
            //中心点为白色，RGBA 4个通道[1,1,1,0]\
            //边上的点为淡蓝色，RGBA 4个通道[0.45,0.75,0.75,0]
            //第一个点是圆点 其他是其他颜色
            colorList.addAll(arrayOf(1f, 1f, 1f, 0f))
            colorList.addAll(arrayOf(0.45f, 0.75f, 0.75f, 0f))
            colorList.addAll(arrayOf(0.45f, 0.75f, 0.75f, 0f))
        }
        mColorBuffer = BufferUtil.iBuffer(colorList.toFloatArray())
    }

    //初始化着色器的initShader方法
    fun initShader() {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", surfaceView.resources)
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", surfaceView.resources)
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader)
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition")
        //获取程序中顶点颜色属性引用id
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor")
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix")
    }

    /**
     * 绘制内容
     */
    fun drawSelf(){
        //指定使用某套着色器程序
        GLES30.glUseProgram(mProgram)
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix,0,0f,0f,1f,0f)
        //设置沿Z轴正向位移1
        Matrix.translateM(mMMatrix,0,0f,0f,1f)
        //设置绕y轴旋转yAngle度
        Matrix.rotateM(mMMatrix,0,mYAngle,0f,1f,0f)
        //设置绕x轴旋转xAngle度
        Matrix.rotateM(mMMatrix,0,mXAngle,1f,0f,0f)
    }
}