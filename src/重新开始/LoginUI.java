package ���¿�ʼ;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;  
  
  
  
/** 
 * ������������� 
 *  
 * @author Administrator 

�����ʲô��˼
 *  
 */  
public class LoginUI extends JFrame {  
    private static boolean isMoved;  
    private static Point pre_point;  
    private static Point end_point;  
  
    public static void main(String args[]) {  
         LoginUI lui = new LoginUI();  
        LoginUI.setDragable(lui);
         lui.showUI();  
          
    }  
  
    // ���캯��  
    public LoginUI() {  
        this.setSize(300, 370);  
        this.setLocationRelativeTo(null);  
        this.setUndecorated(true);// ȥ�����ڵı߿�  
  
    }  
  
    // ��ʾ���ڵĺ���  
    public void showUI() {  
        this.setVisible(true);  
        this.setDragable(this);  
    }  
    public static ImageIcon getImageIcon(String path, int width, int height) {
    	ImageIcon img=new ImageIcon("image\\"+path);
		if (width == 0 || height == 0) {
		return new ImageIcon(img.getClass().getResource("image\\"+path));
		}
		ImageIcon icon = new ImageIcon();
	
		icon.setImage(img.getImage().getScaledInstance(width, height,
		Image.SCALE_DEFAULT));
		return icon;
		}
  
    // Ϊ���ڼ��ϼ�������ʹ�ô��ڿ��Ա��϶�  
  
    public static void setDragable( final JFrame lui) {  
        lui.addMouseListener(new java.awt.event.MouseAdapter() {  
            public void mouseReleased(java.awt.event.MouseEvent e) {  
                isMoved = false;// ����ͷ����Ժ��ǲ�������ק����  
                lui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
            }  
  
            public void mousePressed(java.awt.event.MouseEvent e) {  
                isMoved = true;  
                pre_point = new Point(e.getX(), e.getY());// �õ�����ȥ��λ��  
                lui.setCursor(new Cursor(Cursor.MOVE_CURSOR));  
            }  
        });  
        //�϶�ʱ��ǰ�������ȥ��갴��ȥʱ�����꣬���ǽ�����Ҫ�ƶ���������  
        lui.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {  
            public void mouseDragged(java.awt.event.MouseEvent e) {  
                if (isMoved) {// �ж��Ƿ������ק  
                    end_point = new Point(lui.getLocation().x + e.getX() - pre_point.x,  
                            lui.getLocation().y + e.getY() - pre_point.y);  
                    lui.setLocation(end_point);  
                }  
            }  
        });  
    }  
  
}  
 