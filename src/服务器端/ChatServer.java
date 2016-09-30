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
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//����������
public class ChatServer {
	JFrame frame;
	JPanel pane;
	JButton button1;
	JButton button2;
	boolean started = false;
	ServerSocket ss = null;

	List<Client> clients = new ArrayList<Client>(); // ����ͻ����߳���

	// ����������

	public static void main(String[] args) {

		new ChatServer();
	}

	// �������Ĺ��췽��
	public ChatServer() {
		// ���������߳�
		final Thread t = new Thread(new CarryOut());
		// ����ͼ���û�����
		frame = new JFrame("���������");
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
			ss = new ServerSocket(6666); // ��������˶���
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
				System.out.println("���ճɹ�");
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
		private String account = "888888888";
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
		public DataInputStream getDis() {
			return dis;
		}
		public Socket getS(){
			return s;
			
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

			// ѭ�������ͻ��˵��ַ������ж�
			while (bConnected) {

				// ��ֿͻ��˵��ַ�
				String str = null;
				try {
					str = dis.readUTF();
					//JOptionPane.showMessageDialog(new JFrame(), "��");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
				String m[] = str.split("_");
				// ��¼ÿ����¼�û����˺�
				if (m[0].equals("��¼")) {
					account = m[1];
				} else if ("exit".equals(m[0])) {
					//ɾ�����ߵĿͻ�
					for (int i = clients.size() - 1; i >= 0; i--) {
						Client c = clients.get(i);
						if (m[0].equals(c.getAccout())) {
							System.out.println(clients.size());
							clients.remove(c);
							System.out.println(clients.size());
							bConnected=false;
							c.getDos().close();
							c.getDis().close();
							c.getS().close();	
						}
					}
				} else {
					for (int i = clients.size() - 1; i >= 0; i--) {
						if (m[0].equals(clients.get(i).getAccout())) {
							if(m.length==3)
								clients.get(i).getDos().writeUTF(m[1] + "_" + m[2]);
							else if(m.length==4)
								clients.get(i).getDos().writeUTF(m[1] + "_" + m[2]+"_" + m[3]);

							//	JOptionPane.showMessageDialog(new JFrame(), m[1] + "_" + m[2]);
							} 
						}
					}

				}catch (IOException e) {
					e.printStackTrace();
				}catch( NullPointerException e){
					bConnected=false;
					System.out.println("������죡");
				}
			}
		}
	}
}
