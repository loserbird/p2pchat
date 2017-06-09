/**
 * 
 */
package service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;

import po.IpConstant;
import po.Msg;
import po.SocketStatus;
import service.Server.Client;
import view.P2PFriend;

/**接收广播ip,广播信息，和文件请求
 * @author bingqin
 * @date 2017年6月8日
 */
public class ReceiveIpThread extends Thread{
	P2PFriend friend;
	SocketStatus socketStatus=new SocketStatus();   
	String message;     
	String sendip="";      
	boolean socket=false;  
	public ReceiveIpThread(P2PFriend friend){
		 super();  
		this.friend = friend;
	}
	
	 public void run(){    
		//默认缓冲的大小   
		 byte[] inbuf=new byte[300];
		 //用来发送和接收数据报包的套接字     
		 DatagramSocket socket;     
		 try {    
			 socket = new DatagramSocket(IpConstant.broadcastIpPort);    
			 while(true){      
				 try {       
					 //构造 DatagramPacket用来接收长度为 length 的数据包
					 DatagramPacket packet=new DatagramPacket(inbuf, inbuf.length);       
					 synchronized (socket) {       
						 try { 
							 //从此套接字接收数据报包        
							 socket.receive(packet); 
							 } catch (Exception e) {        
								 e.printStackTrace();        
							}       
						 }                   
					 //接受数据       
					 message=new String(packet.getData()).trim();
					// System.out.println("=========== msg:"+message);
					 Msg msg = JSON.parseObject(message,Msg.class);
					 int status = msg.getSocketStatus();
					 String friendIp = msg.getUser().getIp();
					 //清空缓冲区
					 for (int i = 0; i < inbuf.length; i++) {     
						 inbuf[i]=(byte)0;     
						 }        
					 switch (status) {    
					 case 0x01:      
						 if(msg.getUser().getIp().equals(friend.user.getIp())){
							 //什么也不做
						 }else if(friend.users.add(msg.getUser())){
							 friend.mainFrame.pa.ipListPanel.ipList.add(msg.getUser().getUsername()+"--"+msg.getUser().getIp());
						 }
						 
						 break;     
					case 0x02:
							 if(msg!=null){        
								 friend.mainFrame.pa.chat.append(msg.getUser().getUsername()+"--"+msg.getUser().getIp()
										 +":"+msg.getContent()+"\n");       
								 }     
							 break; 
					case 0x04:
						System.out.println(msg.getUser().getIp());
						if(msg.getUser().getIp().equals(IpConstant.localIp)){
							//什么也不做
						}else{
						 JOptionPane.showMessageDialog(null, friendIp+" 发文件给您，请点击接收按钮接收",null,JOptionPane.OK_OPTION);  
						 List<Client>  clients = friend.server.clients;
						 for(Client client:clients){
							 if(client.sendIp.equals(friendIp)){
								 if(client.chatDialog != null){
									 client.chatDialog.receiveFileButton.setEnabled(true);
								 }
							 }
						 }
						}
							 default:       break;     
							 }  
					} catch (Exception e) {      
						 e.printStackTrace();         
					 }
			 }
			 }catch (Exception e1) {   
						 e1.printStackTrace();   
						 
				 }
	 }
}
