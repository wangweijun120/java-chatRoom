/** 
* @author  ���ߣ���ΰ��
 E-mail: 
* @date ����ʱ�䣺2016��3��4�� ����12:05:43 
* @version  
* @parameter  
* @since  
* @return  
*/
package ��������;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//����������
public class Server {
	JFrame frame;
	JPanel pane;
	JButton button1;
	JButton button2;
	boolean started = false;
	ServerSocket ss = null;
	
	List<Client> clients = new ArrayList<Client>(); // ����ͻ����߳���

	// ����������

	public static void main(String[] args) {

		new Server();
	}

	// �������Ĺ��췽��
	public Server() {
		// ���������߳�
		final Thread t = new Thread(new CarryOut());
		// ����ͼ���û�����
		frame = new JFrame("�������������");
		pane = new JPanel();
		button1 = new JButton("��ʼ����");
		button2 = new JButton("ֹͣ����");
		// ��Ӽ���
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// �����������Ĺ����߳�
				t.start();
				// ���ÿɵ����
				button2.setEnabled(true);
				button1.setEnabled(false);
			}

		});
		// ��Ӽ���
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.exit(1);
			}

		});
		button2.setEnabled(false);
		pane.add(button1);
		pane.add(button2);
		frame.add(pane);
		frame.setLocation(400, 200);
		frame.setSize(400, 100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// �����߳�
	// �ڲ���
	class CarryOut implements Runnable {
		public void run() {
			start1();
		}
	}

	// ��ʼ���еĹ�������
	void start1() {
		try {
			
			ss = new ServerSocket(8888); // ��������˶���
			started = true;
		} catch (BindException e) {
			System.out.println("�˿�ʹ����");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ѭ�������ͻ��˵�����
		// ��Ϊÿ����������Ӧ�Ĵ����߳�
		try {
			while (started) {
				Socket s = ss.accept(); // ���տͻ���
				Client c = new Client(s);
				System.out.println("�͑��˽��ճɹ�");
				new Thread(c).start(); // �����߳�
				clients.add(c); // ����߳���

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
				System.exit(1);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	
	class Client implements Runnable { // �����ͻ����߳̽��գ���������
		// �����߳����˽������
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		private java.sql.Statement sta;
		private Connection con;
		private ResultSet rs = null;
		private String account;
		private int port;

		public int getPort() {
			return port;
		}

		public String getAccout() {
			return account;
		}

		public DataOutputStream getDos() {
			return dos;
		}

		// �߳���Ĺ��췽��
		public Client(Socket s) {
			this.s = s;

			try {
				// �������������
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// �̵߳ĺ��ķ���
		public void run() {
			try {
				// ѭ�������ͻ��˵��ַ������ж�
				while (bConnected) {

					// ��ֿͻ��˵��ַ�
					String str = dis.readUTF();
					String m[] = str.split("_");

					// ���ݲ�ֵĲ�ͬ������в�ͬ�Ĵ���
					if (m[0].equals("��¼")) {
						try {
							// �����½����
							search(m);
							account = m[1];
						} catch (SQLException e) {

							e.printStackTrace();
						}
					} else if (m[0].equals("register")) {
						// ����ע���¼�
						try {
							register(m);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// �����ѯ�Լ���Ϣ�¼�
					} else if (m[0].equals("myInfo")) {
						queryMe(m);
					} else if (m[0].equals("@")) {
						send(m);
					} else if (m[0].equals("friend")) {
						queryFriend(m);
					} else if (m[0].equals("find")) {
						// ����ȡ���¼�
						findFriend(m);
					} else if (m[0].equals("addFriend")) {
						// ����ת���¼�
						addFriend(m);
					} else if (m[0].equals("delete")) {
						// �����޸������¼�
						delete(m);
					} else if (m[0].equals("myInformation")) {
						// �����޸������¼�
						myInfo(m);
					} else if (m[0].equals("logout")) {
						// �����޸������¼�
						logout();
					} else if (m[0].equals("chat")) {
						// �����޸������¼�
						chat(m);
					} else if (m[0].equals("modify")) {
						// �����޸������¼�
						try {
							modifyMe(m);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							bConnected=false;
							System.out.println("�ټ���");
							//e.printStackTrace();
						}
					}
				}
			} catch (java.net.SocketException e) {
				e.printStackTrace();
			} catch (EOFException e) {
				System.out.println("�͑����˳���");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (dis != null)
					if (s != null)
						try {
							dis.close();
							s.close();
							dos.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		}

		public void send(String array[]) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				Client client = clients.get(i);
				if (client.getAccout().equals(array[1])) {
					try {
						client.getDos().writeUTF("@_" + this.account);
						client.getDos().flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// ���������¼�
		public void chat(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				//jdbc:mysql://127.0.0.1:3306/dbname?useUnicode=true&characterEncoding=GBK
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String state = rs.getString(9);
				String ip = rs.getString(10) + "_";
				int port = rs.getInt(11);
				dos.writeUTF(state + "_" + ip + port);
				if (Integer.parseInt(state) == 1) {
					port++;
					String sql4 = "update user_info set PORT=" + port + " where NUM=" + array[1];
					PreparedStatement ps4 = con.prepareStatement(sql4);
					ps4.executeUpdate();
				}
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// �޸��Լ���Ϣ
		public void modifyMe(String array[]) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			String sql = "update USER_INFO set NAME=?,INFO=?,PIC=?,PLACE=?,SEX=? where NUM=" + account;
			PreparedStatement pre = con.prepareCall(sql);
			pre.clearParameters();
			try {
				// ��ȡ�ͻ���������Ϣ
				String name = array[2];
				String info = array[5];
				String pic = array[1];
				String sex = array[3];
				String place = array[4];
				pre.setString(1, name);
				pre.setString(2, info);
				pre.setString(3, pic);
				pre.setString(5, sex);
				pre.setString(4, place);
				pre.executeUpdate();

				dos.writeUTF("true");
				pre.close();
			} catch (SQLException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("false");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("false");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		// ɾ�������¼�
		@SuppressWarnings("null")
		public void delete(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from friend where QQNUM=" + account;
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				StringBuffer buffer = new StringBuffer(81);
				String s1 = rs.getString(2);
				String array1[] = s1.split("&");

				for (int i = 0; i < array1.length; i++) {
					String a[] = array1[i].split("_");
					if (!(a[0].equals(array[1])))
						buffer.append(array1[i]);
				}
				// JOptionPane.showMessageDialog(new
				// JFrame(),buffer.toString());
				String sql1 = "update friend set friend='" + buffer.toString() + "'" + "  where QQNUM=" + account;
				PreparedStatement ps2 = con.prepareStatement(sql1);
				ps2.executeUpdate();
				sta.close();
				con.close();

				dos.writeUTF("true");

			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// �ָ��ַ���������
		public String cut(String str) {
			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
			Matcher m = p.matcher(str);
			m.find();
			m.find();
			return m.group();
		}

		// ��������������ߵ��û�
		public void logout() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			} catch (ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedStatement pre;
			try {
				String sql2 = "update user_info set STATUS=0  where NUM=" + account;
				String sql3 = "update user_info set IP='null'   where NUM=" + account;
				String sql4 = "update user_info set PORT=0 where NUM=" + account;

				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.executeUpdate();
				PreparedStatement ps3 = con.prepareStatement(sql3);
				ps3.executeUpdate();
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.executeUpdate();
				dos.writeUTF("true");
				con.close();
				dos.close();
				dis.close();
				s.close();
			} catch (SQLException e) {
				try {
					dos.writeUTF("logoutFail");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (NumberFormatException e) {
				try {
					dos.writeUTF("logoutFail");
				} catch (IOException e1) {
					try {
						dos.writeUTF("logoutFail");
					} catch (IOException e2) {

						e2.printStackTrace();
					}
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// ������Ӻ����¼��ķ���
		public void addFriend(String array[]) throws IOException {
			if (account.equals(array[1])) {
				dos.writeUTF("�ظ�");
				return;
			}
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				// ��ѯ���ѵ���ϸ��Ϣ
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = "_" + rs.getString(2) + "_";
				String name = rs.getString(3);
				String info = rs.getString(8);
				String in1 = name + array[1] + pic + info;
				// �������е�����
				String sql2 = "select  * from friend where QQNUM=" + account;
				sta = con.createStatement();
				rs = sta.executeQuery(sql2);
				rs.next();
				String in = null;
				String out = rs.getString(2);

				if (out == null || out.equals("")) {
				//	JOptionPane.showMessageDialog(new JFrame(), in1);
					in = in1;
				} else {
					String array1[] = out.split("&");
					for (int i = 0; i < array1.length; i++) {
						String a[] = array1[i].split("_");
						if (cut(a[0]).equals(array[1])) {
							sta.close();
							con.close();
							dos.writeUTF("�ظ�");
							return;
						}
					}
					in = out + "&" + in1;
				}
				// JOptionPane.showMessageDialog(new JFrame(), in);
				// ���º�����Ϣ�ı�
				String sql1 = "update friend set friend='" + in + "'" + "  where QQNUM=" + account;
				PreparedStatement ps2 = con.prepareStatement(sql1);
				ps2.executeUpdate();
				sta.close();
				con.close();
				dos.writeUTF("true");
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
				return;
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
				return;
			}

		}

		// �����Լ�����ϸ��Ϣ
		public void myInfo(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + account;
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = rs.getString(2) + "_";
				String name = rs.getString(3) + "_";
				// String password=rs.getString(4)+"_";
				String sex = rs.getString(5) + "_";
				String birth = rs.getString(6) + "_";
				String place = rs.getString(7) + "_";
				String info = rs.getString(8);

				dos.writeUTF(pic + account + "_" + name + sex + birth + place + info);
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// ���Һ���
		public void findFriend(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = rs.getString(2) + "_";
				String name = rs.getString(3) + "_";
				String sex = rs.getString(5) + "_";
				String birth = rs.getString(6) + "_";
				String place = rs.getString(7) + "_";
				String info = rs.getString(8);

				dos.writeUTF(pic + name + sex + birth + place + info);
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// ��¼��ˢ�²��Һ�����Ϣ
		public void queryFriend(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from friend where QQNUM=" + account;
				// String tableName1="select *from " + "�ͻ���Ϣ where
				// �˺�=2219962313";
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String s1 = rs.getString(2);
				if (!(s1 == null)) {
					String str = "��" + account + "_0.jpg_���Լ����˻�&" + s1;
					sta.close();
					con.close();
					dos.writeUTF(str);
				} else
					dos.writeUTF("��" + account + "_0.jpg_���Լ����˻�");
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}
//��ѯ�Լ�����Ϣ�ķ���
		public void queryMe(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + account;
				// String tableName1="select *from " + "�ͻ���Ϣ where
				// �˺�=2219962313";
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String s1 = rs.getString(2);
				String s2 = rs.getString(3);
				sta.close();
				con.close();
				dos.writeUTF(s1 + "_" + s2 + " @@@    " + account);
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// ����ע���¼��ķ���
		@SuppressWarnings("unused")
		public void register(String array[]) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			String sql = "insert into user_info (NUM,PIC,NAME,PASSWORD,SEX,BIRTHDAY,PLACE,INFO)values (?,?,?,?,?,?,?,?)";
			String sql1 = "insert into friend (QQNUM,FRIEND)values(?,?)";
			// ��ȡ�Ѿ�ע��ĺ���
			String sql2 = "select QQNUM from qqnum where ID=1";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql2);
				rs.next();
				int qqnum = rs.getInt("QQNUM");
				PreparedStatement pre1 = con.prepareCall(sql);
				PreparedStatement pre3 = con.prepareCall(sql1);
				qqnum++;
				// ��ע����˺�д�뵽���ѱ���
				pre3.setInt(1, qqnum);
				pre3.setString(2, null);
				pre3.executeUpdate();

				// ��ȡ�ͻ���������Ϣ
				String name = array[2];
				String password = array[3];
				String info = array[7];
				String pic = array[1];
				String sex = array[4];

				String place = array[6];
				String birthday = array[5];

				pre1.setInt(1, qqnum);
				pre1.setString(3, name);
				pre1.setString(4, password);
				pre1.setString(8, info);
				pre1.setString(2, pic);
				pre1.setString(5, sex);

				pre1.setString(7, place);
				pre1.setString(6, birthday);
				pre1.executeUpdate();

				// ����qq�ŵ����
				String sql3 = "update QQNUM set QQNUM=? where ID=1";
				PreparedStatement pre2 = con.prepareCall(sql3);
				pre2.clearParameters();
				pre2.setInt(1, qqnum);
				pre2.executeUpdate();

				// ���û��˷���ע��ĺ���
				dos.writeUTF(qqnum + "");
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("fail");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("fail");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		// �����½�¼��ķ���
		public void search(String array[]) throws SQLException, IOException {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  PASSWORD from user_info where NUM=" + array[1];

				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);

			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}
			try {
				rs.next();
				String s1 = rs.getString(1);

				if (s1.equals(array[2])) {

					String sql2 = "update user_info set STATUS=1  where NUM=" + array[1];
					String sql3 = "update user_info set IP='" + s.getInetAddress().getHostAddress() + "'"
							+ "  where NUM=" + array[1];
					String sql4 = "update user_info set PORT=" + array[3] + " where NUM=" + array[1];

					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.executeUpdate();
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.executeUpdate();
					PreparedStatement ps4 = con.prepareStatement(sql4);
					ps4.executeUpdate();

					dos.writeUTF("true");
				} else {
					dos.writeUTF("false");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					dos.writeUTF("false");
				} catch (IOException e1) {
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
			sta.close();
			con.close();
		}
	}
}
