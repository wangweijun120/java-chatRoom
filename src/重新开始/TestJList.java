package ���¿�ʼ;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("serial")
public class TestJList extends JFrame implements ActionListener, MouseListener, Runnable {
	private JList list1;
	@SuppressWarnings("rawtypes")
	private static DefaultListModel m;
	private Border border1;
	private Border border2;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private Border border3;
	private JComponent j;
	private JComponent contentPane;
	private static JLabel label;
	private static JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField find;
	private JComboBox skinBox;
	private JButton update;
	private JButton delete;
	private JComponent glassPane;
	private JComponent rootPane;
	private static Login login;
	static String str;
	private JButton btnNewButton;
	Thread thread = null;
	// �������촰�ڵ��б�
	List<ChatRoom> rooms = new ArrayList<ChatRoom>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	// ���췽��

	public TestJList(Login login) {
		this.login = login;
		m = new DefaultListModel();
		init();
		AddData(m);
		try {
			// �������������
			clientSocket = new Socket(login.getIp(), 6666);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			out.writeUTF("��¼_" + login.code);
			out.flush();
		} catch (IOException e) {
			System.out.print("δ���ӵ���");
			e.printStackTrace();
			return;
		}
		chat();
		new Thread( new Runnable(){
			@Override
			public void run() {
				AudioStream	 as=null;
				FileInputStream	fileau =null;
				try {
					// 1.wav �ļ�����java project ����
						fileau = new FileInputStream(System.getProperty("user.dir") + "\\logo.wav");
						 as = new AudioStream(fileau);
					 AudioPlayer.player.start(as);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					as.close();
					fileau.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}

	/**
	 * @return the m
	 */
	@SuppressWarnings("rawtypes")
	public static DefaultListModel getM() {
		return m;
	}

	@SuppressWarnings("unchecked")
	private void init() {

		find = new JTextField("���Һ��ѣ���ϵ��");
		find.setBackground(Color.WHITE);
		find.setSize(239, 34);
		find.setActionCommand("find");
		find.addActionListener(this);
		find.setLocation(30, 91);
		find.setOpaque(false);
		find.setFont(new Font("����", Font.PLAIN, 15));
		find.setForeground(Color.GRAY);
		find.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));

		find.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				find.setText("");
				find.setBackground(Color.white);
				find.setForeground(Color.black);
				find.setOpaque(true);
			}

			public void mouseClicked(MouseEvent e) {
				find.setText("");
				find.setBackground(Color.white);
				find.setForeground(Color.black);
				find.setOpaque(true);
			}
		});

		skinBox = new JComboBox();
		skinBox.setBounds(283, 102, 87, 24);
		skinBox.addItem("����Ƥ��");
		skinBox.addItem("��");
		skinBox.addItem("��1");
		skinBox.addItem("��2");
		skinBox.addItem("��3");
		skinBox.setActionCommand("skin");
		skinBox.addActionListener(this);

		skinBox.setOpaque(false);
		this.getContentPane().add(skinBox);

		update = new JButton("����");
		update.setSize(80, 20);
		update.setLocation(170, 600);
		update.setBackground(Color.white);
		update.setFont(new Font("����", Font.PLAIN, 15));

		delete = new JButton("ɾ��");
		delete.setToolTipText("�ں����б���ѡ�к��Ѻ���ɾ��");
		delete.setSize(80, 20);
		delete.setLocation(264, 637);
		delete.setBackground(Color.white);
		delete.setFont(new Font("����", Font.PLAIN, 15));
		delete.addActionListener(this);

		JButton button3 = new JButton(LoginUI.getImageIcon("�˳�.jpg", 20, 20));
		button3.addActionListener(this);
		button3.setActionCommand("exit");

		button3.setSize(20, 20);
		button3.setLocation(380, 0);

		JButton button = new JButton(LoginUI.getImageIcon("small.jpg", 20, 20));
		button.addActionListener(this);
		button.setActionCommand("small");
		button.addActionListener(this);
		button.setSize(20, 20);
		button.setLocation(355, 0);

		label2 = new JLabel();
		label2.setSize(400, 100);
		label2.setLocation(0, 0);
		label2.setIcon(LoginUI.getImageIcon("��2.jpg", 400, 100));

		label3 = new JLabel();
		label3.setSize(400, 590);
		label3.setLocation(0, 100);
		label3.setIcon(LoginUI.getImageIcon("��2.jpg", 400, 590));

		rootPane = this.getRootPane();
		rootPane.add(label3);

		glassPane = (JComponent) this.getGlassPane();
		glassPane.setLayout(null);
		glassPane.setVisible(true);
		// pane.setSize(400,100);
		// pane.setLocation(0, 0);
		glassPane.setOpaque(false);

		label = new JLabel();
		label.setSize(80, 80);
		label.setLocation(10, 10);
		label.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
		label.addMouseListener(this);

		label1 = new JLabel();
		label1.setSize(200, 30);
		label1.setLocation(100, 60);
		label1.setForeground(Color.WHITE);
		label.setFont(new Font("�����п�", Font.BOLD, 35));
		label1.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));

		glassPane.add(label);
		glassPane.add(label1);
		glassPane.add(button3);
		glassPane.add(button);

		border1 = BorderFactory.createLineBorder(Color.blue, 1);
		border2 = BorderFactory.createLineBorder(Color.blue, 1);
		list1 = new JList(m);
		list1.addMouseListener(this);
		list1.setBackground(new Color(230, 230, 230));
		list1.setForeground(Color.blue);
		// list1.setOpaque(false);
		list1.setBorder(BorderFactory.createTitledBorder(border1));
		/*
		 * ������JList�л���ͼ���ڴ˲����У����ǲ���һ��CellRenderer���󣬴˶���ʵ����ListCellRenderer
		 * interface, ��˿��Է���һ��ListCellRenderer������setCellRenderer()�����Ĳ���.
		 */
		list1.setCellRenderer(new CellRenderer());

		j = new JScrollPane(list1);
		JScrollBar bar = ((JScrollPane) j).getVerticalScrollBar();
		bar.setBackground(Color.gray);
		j.setBorder(BorderFactory.createTitledBorder(border2, "�����б�", 0, 0, new Font("����", Font.BOLD, 20)));
		j.setSize(340, 500);
		j.setLocation(30, 125);

		// j.setOpaque(false);

		contentPane = (JComponent) this.getContentPane();
		contentPane.setLayout(null);
		contentPane.add(label2);
		contentPane.add(j);
		contentPane.add(find);
		// contentPane.add(update);
		contentPane.add(delete);

		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(343, 10, 25, 20);
		getContentPane().add(btnNewButton);
		
	
	
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image image = kit.getImage("image\\logo.jpg");
		setIconImage(image);
		this.setSize(400, 670);
		this.setUndecorated(true);
		// com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);
		LoginUI.setDragable(this);
		SetCenter.setScreenCenter(this);

		contentPane.setOpaque(false);
		this.setVisible(true);
		login.dispose();

	}

	// / Ϊ�����б������Ԫ�صķ���
	@SuppressWarnings("unchecked")
	static void AddData(DefaultListModel m) {
		try {
			login.getOut().writeUTF("myInfo");
			login.getOut().flush();
			str = login.getIn().readUTF();

			String array[] = str.split("_");
			label.setIcon(LoginUI.getImageIcon(array[0], 80, 80));
			label.setToolTipText(array[1] + "����鿴���޸ĸ�����Ϣ");
			label1.setText(array[1]);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			m.clear();
			login.getOut().writeUTF("friend");
			login.getOut().flush();
			String str = login.getIn().readUTF();
			// JOptionPane.showMessageDialog(new JFrame(), str);
			if (!str.equals("false")) {
				String array[] = str.split("&");
				for (int i = 0; i < array.length; i++) {
					m.addElement(array[i]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the in
	 */
	public DataInputStream getIn() {
		return in;
	}

	/**
	 * @return the out
	 */
	public DataOutputStream getOut() {
		return out;
	}

	// ��ʼ�����̣߳�������������������Ϣ
	public void chat() {

		// �����߳�׼�����ܶԷ�����Ϣ
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();

	}

	// ��Ӧ�ڲ��Һ��ѿ��еĻس��¼�
	private void actionForFind(ActionEvent e) {

		new Information(login, find.getText());
		find.setText("���Һ��ѣ���ϵ��");

	}

	// ɾ������
	private void actionForDelete(ActionEvent e) {
		try {
			String array[] = list1.getSelectedValue().toString().split("_");
			if (array[0].equals("") || array[0] == null)
				return;
			int a = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫɾ��" + array[0] + "�ĺ�����");
			if (a == JOptionPane.YES_OPTION) {
				login.getOut().writeUTF("delete_" + array[0]);
				String bool = login.getIn().readUTF();
				if (bool.equals("true")) {
					m.removeElement(list1.getSelectedValue());
				} else {
					JOptionPane.showMessageDialog(this, "������˼��ɾ��\nʧ�ܣ����Ժ�����");
				}
			}
		} catch (NullPointerException e1) {

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// ���ݲ�ͬ�������������Ӧ���¼���������Ӧ�¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		// ��Ӧ�ڲ��Һ��ѿ��еĻس��¼�
		if (e.getActionCommand().equals("find")) {
			actionForFind(e);
		}
		// ɾ���¼�
		else if ("ɾ��".equals(e.getActionCommand())) {
			actionForDelete(e);
		} else if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		}
		// �ر��������¼�
		else if ("exit".equals(e.getActionCommand())) {
			int a = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ�˳�����������");

			if (a == JOptionPane.YES_OPTION) {
				try {
					String array[] = str.split("_");
					Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
					Matcher m = p.matcher(array[1]);
					m.find();
					m.find();
					String number = m.group();
					out.writeUTF("exit_" + number);
					login.getOut().writeUTF("logout");
					String st = login.getIn().readUTF();

					if (st.equals("true"))
						System.exit(0);
					else {
						JOptionPane.showMessageDialog(this, "����ʧ�ܣ�");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
		// ��Ӧ����Ƥ���¼�
		else if ("skin".equals(e.getActionCommand())) {
			label2.setIcon(LoginUI.getImageIcon(skinBox.getSelectedItem().toString() + ".jpg", 400, 100));
			label3.setIcon(LoginUI.getImageIcon(skinBox.getSelectedItem().toString() + ".jpg", 400, 590));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// ��Ӧ�û������Լ���ͷ��
		if (e.getSource().equals(label)) {
			new MyInfo(login);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(list1)) {
			find.setOpaque(false);
			find.setText("���Һ��ѣ���ϵ��");
			find.setForeground(Color.gray);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// ��Ӧ�ں���ͷ�����浥����˫���¼�
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(list1)) {
			if (e.getClickCount() == 1) {
				find.setOpaque(false);
				find.setText("���Һ��ѣ���ϵ��");
				find.setForeground(Color.gray);
			} else if (e.getClickCount() == 2) {
				String array[] = list1.getSelectedValue().toString().split("_");
				Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
				Matcher m = p.matcher(array[0]);
				m.find();
				m.find();
				String number = m.group();
				// JList��ͷ����ǳƴ���ChatRoom���캯�������������
				// System.out.println(list1.getSelectedValue());
				if (rooms.size() == 0) {
					ChatRoom room = new ChatRoom(login, this, list1.getSelectedValue().toString());
					rooms.add(room);
				} else {
					int i = get(number);
					if (i == -1) {
						ChatRoom room = new ChatRoom(login, this, list1.getSelectedValue().toString());
						room.toFront();
						room.setExtendedState(JFrame.NORMAL);
						rooms.add(room);
					} else {
						rooms.get(i).toFront();
						rooms.get(i).setExtendedState(JFrame.NORMAL);
					}
				}
			}
		}
	}

	public int get(String s) {
		for (int i = rooms.size() - 1; i >= 0; i--)
			if (rooms.get(i).getNumber().equals(s)) {
				return i;
			}
		return -1;
	}

	
	// ���߳��н�������
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			try {
				// JOptionPane.showMessageDialog(this, "������ ");
				String message = this.getIn().readUTF();
				String msgstr[] = message.split("_");
				// �ж��Ƿ��Ǻ���
				Boolean bool = judge(msgstr[0]);
				if (bool) {
					
					if ("game".equals(msgstr[1])) {
						if(ChatRoom.game){
						System.out.println(message);
						ChatRoom.game = false;
						
			
						}else{
							getOut().writeUTF(msgstr[0]+"_"+login.code +"_gaming");
						}
					} else if("gaming".equals(msgstr[1])){
						ChatRoom.allow=false;
						ChatRoom.game=true;
						new Thread( new Runnable(){
							@Override
							public void run() {
							JOptionPane.showMessageDialog(new JFrame(), "�Է����ں��������Ѷ�ս��\n�����ĵȴ���");
							}
						}).start();
					//	JOptionPane.showMessageDialog(this, "�Է����ں��������Ѷ�ս��\n�����ĵȴ���");
					}
					else {
						int i = get(msgstr[0]);
						if (i != -1) {
							rooms.get(i).getDialogArea().append(msgstr[1] + "\n");
							rooms.get(i).getDialogArea()
									.setCaretPosition(rooms.get(i).getDialogArea().getText().length());
							rooms.get(i).toFront();
							rooms.get(i).setExtendedState(JFrame.NORMAL);
						} else {
							ChatRoom room = new ChatRoom(login, this, judge1(msgstr[0]));
							room.getDialogArea().append(msgstr[1] + "\n");
							room.getDialogArea().setCaretPosition(room.getDialogArea().getText().length());
							room.toFront();
							room.setExtendedState(JFrame.NORMAL);
							rooms.add(room);
						}

					}
				} else {
					Receive r = new Receive(login, msgstr[0], msgstr[1] + "\n");
					r.toFront();
					r.setExtendedState(JFrame.NORMAL);
				}
			} catch (IOException e1) {
				for (int i = rooms.size() - 1; i >= 0; i--) {
					rooms.get(i).getDialogArea().append(" �������ݳ���\n");
					rooms.get(i).getDialogArea().setCaretPosition(rooms.get(i).getDialogArea().getText().length());
				}
			}
		}
	}

	/**
	 * @return the rooms
	 */
	public List<ChatRoom> getRooms() {
		return rooms;
	}

	// �ж��Ƿ����Լ��ĺ���
	public Boolean judge(String str) {
		for (int i = m.size() - 1; i >= 0; i--) {
			String array[] = ((String) m.getElementAt(i)).split("_");
			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
			Matcher m = p.matcher(array[0]);
			m.find();
			m.find();
			String number = m.group();
			if (number.equals(str)) {
				return true;
			}
		}
		return false;

	}

	public String judge1(String str) {
		for (int i = m.size() - 1; i >= 0; i--) {
			String string = ((String) m.getElementAt(i));
			String array[] = string.split("_");
			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
			Matcher m = p.matcher(array[0]);
			m.find();
			m.find();
			String number = m.group();
			if (number.equals(str)) {
				return string;
			}
		}
		return null;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}

class CellRenderer extends JLabel implements ListCellRenderer {
	/*
	 * ��CellRenderer�̳�JLabel��ʵ��ListCellRenderer.������������JLabel���ڲ�ͼ�����ԣ�
	 * ���CellRenderer�̳���JLabel ����JList�е�ÿ����Ŀ����Ϊ��һ��JLabel.
	 */
	CellRenderer() {
		setOpaque(true);
	}

	/*
	 * �����ﵽ������ʵ��getListCellRendererComponent()���� ��������һ���Ա�ǩΪ��Ŀ��JList
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value != null) {
			String[] s = value.toString().split("_");
			setIcon(LoginUI.getImageIcon(s[1], 50, 40));
			setFont(new Font("�����п�", Font.ITALIC, 30));
			setText(s[0]);
			setIconTextGap(20);
			Border border3 = BorderFactory.createLineBorder(new Color(235, 234, 245), 1);
			setBorder(BorderFactory.createTitledBorder(border3));
			setToolTipText(s[0] + "���ҽ��ܣ�" + s[2]);
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			// ����ѡȡ��ȡ��ѡȡ��ǰ���뱳����ɫ.
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}

}
