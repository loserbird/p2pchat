/**
 * 
 */
package service;

import java.awt.event.MouseWheelEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.alibaba.fastjson.JSON;

import po.IpConstant;
import po.Msg;
import po.SocketStatus;
import view.P2PFriend;


/**发送广播信息
 * @author bingqin
 * @date 2017年6月8日
 */
public class SendAllMsgThread{
	P2PFriend friend ;
	Msg msg = new Msg();
	public SendAllMsgThread(P2PFriend friend){
		this.friend = friend;
	}
	
	 public void run(){ 
		// while(true){       
			 String content = friend.mainFrame.pb.msg.getText();
			 friend.mainFrame.pb.msg.setText("");
			 msg.setContent(content);
			 msg.setSocketStatus(SocketStatus.RECIVE_ALL_MSG);
			 msg.setUser(friend.user);
			 String message = JSON.toJSONString(msg);
			 while(friend.send){           
				 if(content!=null && content!=""){               
				 try {             
					 DatagramPacket packet=new DatagramPacket(message.getBytes(), message.getBytes().length,InetAddress.getByName(IpConstant.broadcastIp),IpConstant.broadcastIpPort);          
					 DatagramSocket socket=new DatagramSocket();            
					 socket.send(packet);              
					 socket.close();            
					// sleep(1000);  
				 } catch (Exception e) {   
					 e.printStackTrace();            
					 }               
				 }
				 friend.send=false;         
				 }
	//	 }
	 }
}
