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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	private JLabel label;
	private JLabel iconLabel;
	private JLabel label1;
	private JLabel label2;
	private JComponent pane;
	private JComponent iconPane;
	private JComponent rootPane;
	private String picture = null;
	private JTextField number;
	private JPasswordField pass1;
	private JPasswordField pass2;
	private JTextField place;
	private TextArea area;
	private Login login;
	private JComboBox year;
	private JComboBox month;
	private JComboBox day;
	private String sex = "��";
	ButtonGroup group;
	JRadioButton woman;

	@SuppressWarnings("restriction")
	public Register(JFrame login) {
		this.login = (Login) login;
		init();

	}

	@SuppressWarnings("restriction")
	private void init() {
		rootPane = this.getRootPane();
		label2 = new JLabel();
		label2.setSize(400, 700);
		label2.setIcon(LoginUI.getImageIcon("����.jpg", 400, 700));
		label2.setLocation(0, 0);
		rootPane.add(label2);

		pane = new JPanel();
		pane.setSize(400, 600);
		pane.setLocation(0, 100);
		pane.setLayout(new GridLayout(2, 1));
		pane.add(getPane1());
		pane.add(getPane2());
		pane.setOpaque(false);

		// pane.setBorder(BorderFactory.createLineBorder(Color.black, 3, true));
		label1 = new JLabel();
		label1.setBorder(BorderFactory.createLineBorder(Color.blue, 3, true));
		label1.setIcon(LoginUI.getImageIcon("��.jpg", 100, 80));
		label1.setSize(100, 80);

		label1.setLocation(30, 0);

		label = new JLabel("ע���˺�");
		label.setFont(new Font("�����п�", Font.ITALIC, 25));
		label.setSize(120, 30);
		label.setLocation(200, 50);

		this.getContentPane().setLayout(null);

		JButton button3 = new JButton(LoginUI.getImageIcon("�˳�.jpg", 20, 20));

		button3.setActionCommand("exit");
		button3.addActionListener(this);
		button3.setSize(20, 20);
		button3.setLocation(375, 20);

		JComponent conPane = (JComponent) this.getContentPane();
		conPane.add(button3);
		conPane.add(label1);
		conPane.add(label);
		conPane.add(pane);
		
		JButton button = new JButton((Icon) null);
		button.addActionListener(this);
		button.setIcon(LoginUI.getImageIcon("small.jpg", 20, 20));
		button.setActionCommand("small");
		button.setBounds(352, 20, 20, 20);
		getContentPane().add(button);
		conPane.setOpaque(false);
		this.setSize(400, 700);

		/** ����Բ�� */
		this.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 90.0D, 90.0D));
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\0.jpg");
		this.setIconImage(image);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginUI.setDragable(this);
		SetCenter.setScreenCenter(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComponent getPane1() {
		JComponent pane2 = new JPanel();
		pane2.setLayout(null);
		JComponent pane3 = new JPanel();
		pane3.setSize(200, 150);
		pane3.setLocation(150, 5);
		GridLayout layout1 = new GridLayout(3, 1);
		layout1.setHgap(0);
		layout1.setVgap(10);
		pane3.setLayout(layout1);
		JLabel label2 = new JLabel("�ǳ�");
		label2.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label2.setSize(100, 30);
		label2.setLocation(90, 10);

		JLabel label3 = new JLabel("����");
		label3.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label3.setSize(100, 30);
		label3.setLocation(90, 65);

		JLabel label4 = new JLabel("ȷ������");
		label4.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label4.setSize(100, 30);
		label4.setLocation(60, 120);
		JLabel label5 = new JLabel("����");
		label5.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label5.setSize(100, 30);
		label5.setLocation(90, 200);
		year = new JComboBox();
		year.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		year.setSize(80, 25);
		year.setLocation(140, 200);
		month = new JComboBox();
		month.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		month.setSize(70, 25);
		month.setLocation(230, 200);
		day = new JComboBox();
		day.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		day.setSize(70, 25);
		day.setLocation(310, 200);
		for (int i1 = 2016; i1 >= 1897; i1--)
			year.addItem(i1 + "   ��");
		for (int a = 1; a < 13; a++)
			month.addItem(a + "  ��");
		for (int a = 1; a < 32; a++)
			day.addItem(a + "   ��");

		JLabel label6 = new JLabel("���ڵ�");
		label6.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label6.setSize(120, 30);
		label6.setLocation(70, 240);
		place = new JTextField();
		place.setSize(200, 30);
		place.setLocation(150, 240);

		JLabel label7 = new JLabel("�Ա�");
		label7.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		label7.setSize(100, 30);
		label7.setLocation(90, 160);
		JRadioButton man = new JRadioButton("��", true);
		man.setSize(50, 30);
		man.setLocation(200, 160);

		woman = new JRadioButton("Ů", false);
		woman.setSize(50, 30);
		woman.setLocation(250, 160);
		group = new ButtonGroup();
		group.add(man);
		group.add(woman);

		number = new JTextField();
		number.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
		pass1 = new JPasswordField();
		pass2 = new JPasswordField();
		pass1.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
		pass2.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));

		pane3.add(number);
		pane3.add(pass1);
		pane3.add(pass2);
		pane3.setOpaque(false);

		pane2.add(label2);
		pane2.add(label3);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);
		pane2.add(pane3);
		pane2.add(man);
		pane2.add(woman);
		pane2.add(year);
		pane2.add(month);
		pane2.add(day);
		pane2.add(place);
		pane2.setOpaque(false);
		return pane2;

	}

	@SuppressWarnings("unused")
	public JComponent getPane2() {
		JComponent pan = new JPanel();
		pan.setLayout(null);
		JComponent pane3 = new JPanel();
		JLabel label1 = new JLabel("���ҽ���");
		label1.setSize(120, 30);
		label1.setLocation(25, 125);
		label1.setFont(new Font("����", Font.PLAIN, 20));
		area = new TextArea("", 50, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
		area.setFont(new Font("����", Font.PLAIN, 15));
		JLabel label9 = new JLabel("��ѡ��ͷ��:");
		label9.setSize(250, 30);
		label9.setLocation(25, 0);
		label9.setFont(new Font("����", Font.PLAIN, 17));

		makeIcon();
		JScrollPane jS = new JScrollPane();
		jS.setViewportView(iconPane);
		jS.setSize(350, 80);
		jS.setLocation(25, 35);

		area.setSize(350, 80);
		area.setLocation(25, 155);

		JButton button = new JButton("�ύ");
		button.setFont(new Font("����", Font.PLAIN, 20));
		button.setForeground(Color.blue);
		button.setBackground(Color.white);
		button.setBorder(BorderFactory.createLineBorder(Color.white));
		button.setSize(60, 30);
		button.setLocation(160, 240);
		button.addActionListener(this);

		// pan.setBackground(Color.red);
		pan.add(label1);
		pan.add(label9);
		pan.add(jS);
		pan.add(area);
		pan.add(button);
		pan.setOpaque(false);
		return pan;

	}

	// �ṩͼƬ
	private void makeIcon() {
		iconPane = new JPanel();
		GridLayout layout = new GridLayout(1, 0);
		layout.setHgap(20);
		iconPane.setLayout(layout);
		String pic[] = { "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113",
				"114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125" };

		for (int i = 0; i <= 24; i++) {
			final String s = pic[i] + ".jpg";
			JLabel l = new JLabel();
			l.setIcon(LoginUI.getImageIcon(s, 50, 50));
			l.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					label1.setIcon(LoginUI.getImageIcon(s, 100, 80));
					picture = s;
				}
			});
			iconPane.add(l);
		}
	}

	//�ж��ַ����Ƿ�������
	public   boolean isNumeric(String str){  
	  	  for (int i = str.length();--i>=0;){    
	  	   if (Character.isDigit(str.charAt(i))){  
	  	    return true;  
	  	   }  
	  	  }  
	  	  return false;  
	  	}  
	//�ж��ַ����Ƿ������ĸ
	boolean isEn(String str)
	{
		char	c;
		for(int i=str.length()-1;i>=0;i--){
	c=str.charAt(i);
	if(c>=0x0000 && c<=0x00FF)   //Ӣ���ַ�
	return true;
		}
	return false;
	}

	private void HandOn(ActionEvent e) {
		// �ж��ǳ��Ƿ�Ϊ��
		  
		if (number.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "�ǳƲ���Ϊ�գ�\n �������ǳ�", "������Ϣ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(isNumeric(number.getText())||isEn(number.getText())){
			JOptionPane.showMessageDialog(this, "���˹�ѧ���ǳƱ���Ϊ�����֣�����\n �����������ǳ�", "������Ϣ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if ("".equals(pass1.getText()) || "".equals(pass2.getText())) {
			JOptionPane.showMessageDialog(this, "�������벻��Ϊ�գ�\n ����������", "������Ϣ", JOptionPane.ERROR_MESSAGE);
			return;
		} // �ж�����������ȷ�������Ƿ�һ��
		if (pass1.getText().equals(pass2.getText())) {
		} else {
			JOptionPane.showMessageDialog(this, "�����������벻ͬ��\n  ����������", "������Ϣ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ("".equals(place.getText())) {
			JOptionPane.showMessageDialog(this, "���ڵز���Ϊ�գ���\n  ����������", "������Ϣ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ("".equals(area.getText())) {
			JOptionPane.showMessageDialog(this, "���ҽ��ܲ���Ϊ�գ���\n  ����������", "������Ϣ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// �ж��Ƿ�ѡ��ͷ��
		if (picture == null) {
			JOptionPane.showMessageDialog(this, "��ѡ��ͷ��", "������Ϣ", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (woman.isSelected()) {
			sex = "Ů";
		}
		else {
			sex="��";
		}
		//JOptionPane.showMessageDialog(this, sex+picture);
		try {
			String birth = year.getSelectedItem().toString() + month.getSelectedItem().toString()
					+ day.getSelectedItem().toString();
			login.getOut().writeUTF("register_" + picture + "_" + number.getText() + "_" + pass1.getText() + "_" + sex
					+ "_" + birth + "_" + place.getText() + "_" + area.getText());
		//	JOptionPane.showMessageDialog(new JFrame(), "gsg");
			String code = login.getIn().readUTF();
			
			if ("fail".equals(code)) {
				JOptionPane.showMessageDialog(this, "ע��ʧ��!", "ע��ʧ��", JOptionPane.OK_OPTION);
				return;
			}
			JOptionPane.showMessageDialog(this, "ע��ɹ�!\n  �����˺�Ϊ��" + code + "\n  ��������Ϊ:" + pass1.getText(), "ע��ɹ�",
					JOptionPane.OK_OPTION);
			login.getIn().close();
			login.getOut().close();
			login.getClientSocket().close();
			this.dispose();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "ע��ʧ��!", "ע��ʧ��", JOptionPane.OK_OPTION);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ��Ӧ�رմ����¼�
		if (e.getActionCommand().equals("exit")) {
			try {
				login.getIn().close();
				login.getOut().close();
				login.getClientSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
		} else if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		} else if ("�ύ".equals(e.getActionCommand())) {
			HandOn(e);
		}

	}
}