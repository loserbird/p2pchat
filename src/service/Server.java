package service;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import po.IpConstant;
import view.ChatDialog;

/**
 * 本地服务器，主窗口一旦启动，将会运行一个服务器进程
 * @author bingqin
 * @date 2017年6月10日
 */
public class Server {
	boolean started = false;
	ServerSocket ss = null;
	//保存对所有客户端的使用
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args) {
		//new Server().start();
		
	}

	//主线程，负责接收客户端的连接
	public void start() {
		try {
			ss = new ServerSocket(IpConstant.oneToOnePort);
			started = true;
			
		} catch (BindException e) {
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
				System.out.println("a client connected!");
				//启动一个线程去读取数据
				new Thread(c).start();
				clients.add(c);
				//dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//读取数据线程，包装类，用来读取从客户端发过来的数据，并发送到所有客户端
 public class Client implements Runnable {
		ChatDialog chatDialog ;
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		public String sendIp;
		
		public Client(Socket s) {
			chatDialog = new ChatDialog();
			this.s = s;
			this.sendIp = s.getInetAddress().getHostAddress();
			chatDialog.setTitle(sendIp);
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
				
				chatDialog.sendchat.addActionListener(sendListener);
				chatDialog.sendButton.addActionListener(sendListener);
				//chatDialog.sendFileButton.addActionListener(sendFileListener);
				chatDialog.receiveFileButton.addActionListener(receiveFileListener);
				chatDialog.setTitle(sendIp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ActionListener sendListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = chatDialog.sendchat.getText();
				str = IpConstant.localIp+":"+str;
				if(str!=null && ""!=str){
					send(str);
				}
				chatDialog.sendchat.setText("");
				chatDialog.chat.append(str+"\n");
			}
		};
	
		ActionListener receiveFileListener  = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser(); 
				File savefile;
				jFileChooser.showSaveDialog(chatDialog);
				 savefile=jFileChooser.getSelectedFile();
				 String saveFilePath = savefile.getAbsolutePath();
				 //FileUtil.receiveFile(dis, savefile);
				 new ReceiveFile(sendIp, savefile).run();
				 JOptionPane.showMessageDialog(chatDialog, "文件接收成功！",null,JOptionPane.OK_OPTION);
				 chatDialog.receiveFileButton.setEnabled(false);
			}
		};
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				clients.remove(this);
				System.out.println("对方退出了！我从List里面去掉了！");
			}
		}
		
		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					this. chatDialog.chat.append(str+"\n");
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
				//销毁窗口
				chatDialog.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) dis.close();
					if(dos != null) dos.close();
					if(s != null)  {
						s.close();
						//s = null;
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
			}
		}
		
	}
}
