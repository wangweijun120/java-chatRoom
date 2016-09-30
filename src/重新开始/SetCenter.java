/** 
* @author  ���ߣ���ΰ��
 E-mail: 
* @date ����ʱ�䣺2016��3��4�� ����12:05:43 
* @version  
* @parameter  
* @since  
* @return  
*/
package ���¿�ʼ;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class SetCenter {
public SetCenter(){}

//�ô�����ʾ����Ļ�м�
public static void setScreenCenter(JFrame frame){
	Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize=frame.getSize();
	if(frameSize.height>screenSize.height){
		frameSize.height=screenSize.height;
	}
	if(frameSize.width>screenSize.width){
		frameSize.width=screenSize.width;
	}
	frame.setLocation((screenSize.width-frameSize.width)/2,
			(screenSize.height-frameSize.height)/2);
}
	//���öԻ����ڸ��Ի��������
	public static void setDialogCenter(JFrame frame,JDialog dlg){
		Dimension dlgSize=dlg.getPreferredSize();
		Dimension frmSize=frame.getSize();
		Point loc=frame.getLocation();
		dlg.setLocation((frmSize.width-dlgSize.width)/2+loc.x,
			(frmSize.height-dlgSize.height)/2+loc.y);
}
	//���ô����ڸ����������
	public static void setFrameCenter(JFrame frame,JFrame subframe){
		Dimension subSize=subframe.getPreferredSize();
		Dimension frmSize=frame.getSize();
		Point loc=frame.getLocation();
		subframe.setLocation((frmSize.width-subSize.width)/2+loc.x,
				(frmSize.height-subSize.height)/2+loc.y);
	}
}
