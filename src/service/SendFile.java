/**
 * 
 */
package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import po.IpConstant;


/**发送文件
 * @author bingqin
 * @date 2017年6月9日
 */
public class SendFile {
	 File file;
	 public SendFile(File file){     
		 this.file=file;      
	 }
	  public void run(){     
		  int length;      
		  Socket socket;     
		  if(file!=null){      
			  try{     
				  FileInputStream fos=new FileInputStream(file);        
				  // 创建绑定到特定端口的服务器套接字。        
				  ServerSocket s=new ServerSocket(IpConstant.filePort);       
				  socket = s.accept();     
				  try {         
					  try{                
						  OutputStream netout=socket.getOutputStream();         
						  OutputStream doc=new DataOutputStream(new BufferedOutputStream(netout));          
						  byte[] buf=new byte[2048];          
						  length=fos.read(buf);          
						  while(length!=(-1)){     
							  doc.write(buf, 0, length);
							  doc.flush();        
							  length=fos.read(buf);
							  }           
						  fos.close();           
						  doc.close();         
						  }finally{           
							  JOptionPane.showMessageDialog(null, "文件传输成功", null, JOptionPane.OK_OPTION);           
							  socket.close();
							  }          
					  }finally{                 
						  s.close();
						  }
					  }catch(IOException e){
						  e.printStackTrace();
					  }
			  }
	  }
	
}
