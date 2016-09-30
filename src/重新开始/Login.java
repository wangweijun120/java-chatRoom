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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	private JTextArea number;
	private JPasswordField passWord;
	private JButton button1;
	private JButton Registerbutton;
	private JButton button3;
	private JPanel pane1;
	private JPanel pane2;
	private JPanel pane3;
	private JPanel Pane;
	private JLabel label;
	private JLabel label1;
	private JLabel label2;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private String ip;
	Boolean bool = false;
	static int port;
	static String code;
	private JTextField ipField;

	public Login() {

		init();
		/*try {
			// �������������
			clientSocket = new Socket("172.20.10.3", 8888);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.print("δ���ӵ���");
			e.printStackTrace();
		}*/
	}

	private void init() {

		number = new JTextArea();
		number.setForeground(Color.GRAY);
		number.setText("�˺ţ�");
		number.setBounds(5, 6, 193, 24);
		number.setLineWrap(true);
		number.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
			//	number.setText("");
				if(number.getText().equals("�˺ţ�")){
					number.setForeground(Color.GRAY);
					number.setText("");
				}
				number.setForeground(Color.black);
				if(passWord.getText().equals("")){
					passWord.setForeground(Color.GRAY);
					
				}
				// number.setBackground(Color.white);
				// number.setOpaque(true);
			}
		});

		passWord = new JPasswordField();
		passWord.setToolTipText("");
		passWord.setBounds(5, 40, 193, 24);
		passWord.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
		passWord.setActionCommand("��ȫ��¼");
		passWord.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
			//	passWord.setText("");
				
				passWord.setForeground(Color.black);
				if(ipField.getText().equals("")){
					ipField.setForeground(Color.GRAY);
					ipField.setText("123.207.163.159");
				}
				if(number.getText().equals("")){
					number.setForeground(Color.GRAY);
					number.setText("�˺ţ�");
				}
			}
		});
		passWord.addActionListener(this);

		pane1 = new JPanel();
		pane2 = new JPanel();
		pane3 = new JPanel();
		pane3.setBorder(BorderFactory.createLineBorder(Color.white));

		JButton button3 = new JButton(LoginUI.getImageIcon("�˳�.jpg", 20, 20));
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button3.setSize(20, 20);
		button3.setLocation(430, 0);

		button1 = new JButton("��ȫ��¼");
		button1.setBounds(8, 77, 182, 36);
		button1.setForeground(Color.white);
		button1.setBackground(Color.blue);
		button1.addActionListener(this);

		Registerbutton = new JButton("ע���˺�");
		Registerbutton.setBackground(Color.white);
		Registerbutton.setBorder(BorderFactory.createLineBorder(Color.white));
		Registerbutton.setSize(60, 20);
		Registerbutton.setLocation(360, 68);
		Registerbutton.addActionListener(this);

		label = new JLabel("��������");
		label.setFont(new Font("�����п�", Font.ITALIC, 30));
		label.setForeground(Color.white);
		label.setSize(150, 30);
		label.setLocation(250, 100);

		label2 = new JLabel(LoginUI.getImageIcon("0.jpg", 150, 130));
		label2.setBorder(BorderFactory.createLineBorder(Color.white, 8, true));

		label2.setSize(150, 130);
		label2.setLocation(90, 30);

		pane1.setLayout(null);
		pane2.setLayout(null);

		pane1.setBackground(Color.blue);
		pane1.add(label2);
		pane1.add(label);
		pane1.add(button3);

		pane3.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
		pane3.setLayout(null);
		pane3.add(number);
		pane3.add(passWord);
		pane3.add(button1);
		pane3.setSize(197, 121);
		pane3.setLocation(133, 10);

		pane2.add(pane3);

		// pane2.add(label1);
		pane2.add(Registerbutton);
		
		 
		
		this.setSize(450, 360);
		this.setUndecorated(true);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(pane1);
		
		JButton button = new JButton((Icon) null);
		button.addActionListener(this);
		button.setIcon(LoginUI.getImageIcon("small.jpg", 20, 20));
		button.setActionCommand("small");
		button.setBounds(406, 0, 20, 20);
		
		pane1.add(button);
		getContentPane().add(pane2);
		
		ipField = new JTextField("123.207.163.159");
		ipField.setForeground(Color.GRAY);
		ipField.setFont(new Font("����", Font.BOLD, 12));
		ipField.setBackground(Color.WHITE);
		ipField.setToolTipText("\u5728\u6B64\u8F93\u5165\u670D\u52A1\u5668IP");
		ipField.setBounds(23, 68, 84, 20);
		ipField.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
		ipField.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(ipField.getText().equals("123.207.163.159")){
					ipField.setForeground(Color.black);
					ipField.setText("");
				}
			
				if(number.getText().equals("")){
					number.setForeground(Color.GRAY);
					number.setText("�˺ţ�");
				}
			}
		});
		
		pane2.add(ipField);
		ipField.setColumns(10);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\logo.jpg");
		this.setIconImage(image);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SetCenter.setScreenCenter(this);
		this.setVisible(true);
		LoginUI.setDragable(this);

	}

	// ��Ӧע���¼�
	private void actionForRegister(ActionEvent e) {
		ip=ipField.getText();
		try {
			
			// �������������
			clientSocket = new Socket(ip, 8888);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e1) {
			System.out.print("δ���ӵ���");
			e1.printStackTrace();
		}
		new Register(this);
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the clientSocket
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	// ��Ӧ��½�¼�
	private void actionForLoad(ActionEvent e) {
		String sr = number.getText().trim();
		// �ж��˺ŵĺϷ���
		boolean isNum = sr.matches("[0-9]+");
		if (!isNum) {
			JOptionPane.showMessageDialog(this, "�˺�ֻ��Ϊ�����֣�����������!!", "��ʾ", JOptionPane.YES_OPTION);
			return;
		} 
		// ���ӷ�����
		ip=ipField.getText();
		try {
			// �������������
			
			clientSocket = new Socket(ip, 8888);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e1) {
			System.out.print("δ���ӵ���");
			e1.printStackTrace();
		}
		try {
			this.getOut()
					.writeUTF("��¼_" + sr + "_" + String.valueOf(passWord.getPassword()) + "_" +"8866");
			bool = Boolean.parseBoolean(this.getIn().readUTF());
			// System.out.println(bool);
		} catch (IOException e1) {
			// e.printStackTrace();
		}

		// �������ʾ
		if (!bool) {
			JOptionPane.showMessageDialog(this, "������������˻�������������������!!", "��ʾ", JOptionPane.YES_OPTION);
		} else {
			port = Integer.parseInt("8866");
			code =number.getText();
			new TestJList(this);

		}
	}

	//��Ӧ�����¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ע���˺�".equals(e.getActionCommand())) {
			actionForRegister(e);
		} else if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		} else if ("��ȫ��¼".equals(e.getActionCommand())) {
			actionForLoad(e);

		}

	}

	public static void main(String[] args) {
		Login login = new Login();

	}

	/**
	 * @return the in
	 */
	public DataInputStream getIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(DataInputStream in) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public DataOutputStream getOut() {
		return out;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut(DataOutputStream out) {
		this.out = out;
	}
}
