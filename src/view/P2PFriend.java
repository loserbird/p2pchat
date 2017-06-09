package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import po.IpConstant;
import po.SocketStatus;
import po.User;
import service.*;
import service.Server;

/**
 * 一个主线程类，代表一个端点
 * @author bingqin
 * @date 2017年6月10日
 */
public class P2PFriend{
	public Server server;
	//主窗口
	public MainFrame mainFrame;
	//当前用户
	public User user = new User();
	//所有在线用户
	public Set<User> users = new HashSet<User>();
	
	//广播的地址
	String broadcastIp = IpConstant.broadcastIp;
	
	
	SocketStatus socketStatus = new SocketStatus();
	
	//是否点击了发送按钮，发送给所有人信息
	public boolean send = false;
	boolean isstart = false;
	boolean flag = true;
	
	SendAllMsgThread sendAllMsgThread ;
	SendIpThread sendIpThread ;
	ReceiveIpThread receiveIp;
	
	
	public P2PFriend(String username) {
		
		user.setUsername(username);
		user.setIp(IpConstant.localIp);
		
		mainFrame = new MainFrame(user);
		this.actionPerformed(this);  
		
		//开启线程,发送自己的ip,接收所有在线用户的ip
		if(flag){
			sendAllMsgThread = new SendAllMsgThread(this);
			sendIpThread = new SendIpThread(this);
			receiveIp=new ReceiveIpThread(this); 
			sendIpThread.start();
			receiveIp.start();
			//ReceiveFileThread receiveFileThread = new ReceiveFileThread(this);
			//receiveFileThread.start();
		}
		//开启服务器，以便与其他人建立TCP连接
		server = new Server();
		server.start();
		
	}
	public void actionPerformed(P2PFriend friend) {
		final P2PFriend pfriend;  
		pfriend=friend;   
		//为“发送”按键添加按键触发  
		mainFrame.pb.sendButton.addActionListener(new ActionListener() {     
			@Override    
			public void actionPerformed(ActionEvent e) {    
				send=true; 
				sendAllMsgThread.run();       
				 }    
		}); 
		
		//为双击在线用户的ip添加按键触发
		mainFrame.pa.ipListPanel.ipList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = mainFrame.pa.ipListPanel.ipList.getSelectedItem();
				String[] info = str.split("--");
				String username = info[0];
				String ip = info[1];
				User user = new User(username,ip); 
				ChatClient client = new ChatClient(user);
				client.launchFrame();
			}
		});
		//刷新在线用户的按键处理
		mainFrame.pb.refreshUserButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.pa.ipListPanel.ipList.removeAll();
				users.clear();
			}
		});
		 //刷新会话信息的按键处理
		mainFrame.pb.refreshMsgButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.pa.chat.setText("");
				
			}
		});
		
	}
	
	

	
	
	/*public static void main(String[] args) {
		new P2PFriend("bingqin");
	}*/
	
}
