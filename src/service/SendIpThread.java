/**
 * 
 */
package service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;

import po.IpConstant;
import po.Msg;
import po.SocketStatus;
import view.P2PFriend;


/**广播自己的ip
 * @author bingqin
 * @date 2017年6月8日
 */
public class SendIpThread extends Thread{
	P2PFriend friend;
	 
	 public SendIpThread(P2PFriend friend) {
		 this.friend = friend;
	 }

	public void run(){      
			 Msg msg = new Msg();
			 msg.setSocketStatus(SocketStatus.RECIVE_IP);
			 msg.setUser(friend.user);
			 String message = JSON.toJSONString(msg);
			 
		 while(true){          
			 try {          
				 //构造数据报包，用来将长度为 length 偏移量为 offset 的包发送到指定主机上的指定端口号        
				 DatagramPacket packet=new  DatagramPacket(message.getBytes(),message.getBytes().length,InetAddress.getByName(IpConstant.broadcastIp),IpConstant.broadcastIpPort);
				 DatagramSocket socket=new DatagramSocket();          
				 socket.send(packet);          
				 socket.close();         
				 sleep(2000);  
				 } catch (Exception e) {            
					 e.printStackTrace();
				 }
			 }
	 }
}
