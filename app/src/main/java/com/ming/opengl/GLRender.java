package com.ming.opengl;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	int one = 0x10000;
	
	// ���ڿ��������κ���������ת�ĽǶ�
	float rotateTri, rotateQuad;
	
	//��������������
	 private IntBuffer triggerBuffer = BufferUtil.iBuffer(new int[]{
			0, one, 0,    //�϶���
			-one, -one, 0,    //���µ�
			one, -one, 0,}); //���µ�
	 
	 //�����ε�4������
	 private IntBuffer quaterBuffer = BufferUtil.iBuffer(new int[]{
				 one, one,0,
				-one, one,0,
				 one,-one,0,
				-one,-one,0});
	 
	 //�����εĶ�����ɫֵ(r,g,b,a)
	 private IntBuffer colorBuffer = BufferUtil.iBuffer(new int[]{
			 	one,  0,  0,one,
			 	  0,one,  0,one,
			 	  0,  0,one,one,
	 });
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// TODO Auto-generated method stub
		
		// ����������Ļ
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// ����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		//���þ���
		gl.glLoadIdentity();
		
		// �ӵ�任
		GLU.gluLookAt(gl, 0, 0, 3, 0, 0, 0, 0, 1, 0);
		
		// ����ģ��λ��
		gl.glTranslatef(-0.0f, 0.0f, -4.0f);
		
		//������ת(y��)
		gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
		
		// �������ö���
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		// ����������ɫ����
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		//������ɫ����
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		
		// ���������εĶ�������
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
		
		//�Ŵ�������
		gl.glScalef(2.0f, 2.0f, 2.0f);
		
		//����������
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		//�ر���ɫ���������
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
		/*****������*****/
		
		//���������ε���ɫ
		gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
		
		// ���õ�ǰ��ģ�͹۲����
	    gl.glLoadIdentity();
	    
	    // ����ģ��λ��
	    gl.glTranslatef(1.0f, 0.0f, -4.0f);
	    
	    //������ת(x��)
	    gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0.0f);
	    
	    //���������ζ�������
	    gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
	    
	    //����������
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	    
	    /* ���Ƴ��������߿� */
	    //gl.glDrawArrays(GL10.GL_LINES, 0, 4);

	    // ȡ����������
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    
	    //�ı���ת�ĽǶ�
	    rotateTri += 0.5f;
	    rotateQuad -= 0.5f;
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// TODO Auto-generated method stub
		
		float ratio = (float) width / height;
		
		// �����ӿ�(OpenGL�����Ĵ�С)
		gl.glViewport(0, 0, width, height);

		// ����ͶӰ����Ϊ͸��ͶӰ
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		// ����ͶӰ������Ϊ��λ����
		gl.glLoadIdentity();
		
		//����һ��͸��ͶӰ���������ӿڴ�С��
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// TODO Auto-generated method stub
		
		//����ϵͳ��Ҫ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		//����������Ļ����ɫ
		gl.glClearColor(0, 0, 0, 1);
		
		//������Ȼ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

}

