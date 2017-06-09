package view;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.print.Doc;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import po.IpConstant;
import po.User;
import service.SendFile;
import util.FileUtil;
/**
 * 双击ip时开启的对话窗口
 * @author bingqin
 * @date 2017年6月4日
 */
public class ChatClient extends JFrame{
	//对方用户
	public User user;
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;

	TextArea chat;  
	TextField sendchat;  
	Button sendButton,sendFileButton,receiveFileButton; 
	public ChatClient(User user){
		this.user = user;
	}
	
	Thread tRecv = new Thread(new RecvThread()); 

	public static void main(String[] args) {
		//new ChatClient("haha").launchFrame(); 
	}
	//初始化
	public void launchFrame() {
		Label title=new Label("密函:");    
		
		//创建个只读文本，用来显示别人发过来的消息   
		chat=new TextArea(20,20);   
		chat.setEditable(false);   
		
		//创建个允许编辑的文本，用来发送一对一消息  
		sendchat=new TextField(25); //  
		sendButton=new Button("发送");   
		sendFileButton=new Button("发送文件");   
		//receiveFileButton=new Button("接收文件");   
		//receiveFileButton.setEnabled(false);    
		Panel p=new Panel(new FlowLayout(FlowLayout.LEFT));   
		setBackground(Color.YELLOW);    
		setTitle(user.getUsername()+"--"+user.getIp());
		setSize(400, 200);       
		setLocation(450, 100);       
		setResizable(false);       
		add("North",title);   
		add("Center",chat);  
		p.add(sendchat);   
		p.add(sendButton);  
		p.add(sendFileButton);   
		//p.add(receiveFileButton);   
		add("South",p);  
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				e.getWindow().dispose();
			}
			
		});
		sendchat.addActionListener(new sendMsgListener());
		sendButton.addActionListener(new sendMsgListener());
		sendFileButton.addActionListener(new sendFileListener(this));
		//receiveFileButton.addActionListener(new sendFileListener(this));
		setVisible(true);
		connect();
		
		tRecv.start();
	}
	//连接
	public void connect() {
		try {
			s = new Socket(user.getIp(), IpConstant.oneToOnePort);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
System.out.println("connected!");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//退出，释放连接
	public void disconnect() {
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//文本域监听器，向服务器发送文本
	private class sendMsgListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = sendchat.getText().trim();
			str = user.getIp()+":"+str;
			sendchat.setText("");
			chat.append(str+"\n");
			try {
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	private class sendFileListener implements ActionListener{
		
		ChatClient client = null;
		public sendFileListener(ChatClient client){
			this.client = client;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			FileUtil.sendFileRequest(new User(null,IpConstant.localIp));
			JFileChooser jFileChooser=new JFileChooser();       
			jFileChooser.setMultiSelectionEnabled(false); 
			jFileChooser.showOpenDialog(client);
			File file = jFileChooser.getSelectedFile();
			String sendfilepath = file.getAbsolutePath();
			jFileChooser.setToolTipText(sendfilepath);
			System.out.println("begin send file");
			FileInputStream fos = null;
			//FileUtil.sendFile(dos, file);
			//JOptionPane.showMessageDialog(client, "文件发送成功", null, JOptionPane.OK_OPTION); 
			new SendFile(file).run();
			
		}
	
		
	}
	//接收线程，接收从服务器发过来的文本
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					chat.setText(chat.getText() + str + '\n');
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye!");
			} catch (EOFException e) {
				System.out.println("推出了，bye - bye!");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}

