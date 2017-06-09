/**
 * 
 */
package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;

import po.IpConstant;
import po.Msg;
import po.SocketStatus;
import po.User;

/**
 * @author bingqin
 * @date 2017年6月9日
 */
public class FileUtil {
	/*public static void receiveFile(DataInputStream dis, File file){
		FileOutputStream fileOutputStream = null;
		try {
			 fileOutputStream = new FileOutputStream(file);
			byte[] buf=new byte[2048]; 
			int length = 0;
			while((length = dis.read(buf))!=-1){
				fileOutputStream.write(buf, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void sendFile(DataOutputStream dos,File file){
		byte[] buf=new byte[2048];
		FileInputStream fos = null;
		try {
			 fos=new FileInputStream(file);
			int length = 0;
			while((length=fos.read(buf))!=-1){
				dos.write(buf,0,length);
				dos.flush();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}*/
	
	/**
	 * 发送文件请求
	 * @param user
	 */
	public static void sendFileRequest(User user){
		
		 Msg msg = new Msg();
		 msg.setSocketStatus(SocketStatus.RECIVE_FILE);
		 msg.setUser(new User(null,IpConstant.localIp));
		 String message = JSON.toJSONString(msg);
		 try{
		DatagramPacket packet=new  DatagramPacket(message.getBytes(),message.getBytes().length,InetAddress.getByName(IpConstant.broadcastIp),IpConstant.broadcastIpPort);
		DatagramSocket socket=new DatagramSocket();          
		socket.send(packet);          
		socket.close(); 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		
	}
}
