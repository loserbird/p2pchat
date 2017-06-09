/**
 * 
 */
package service;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import po.IpConstant;


/**
 * @author bingqin
 * @date 2017年6月9日
 */
public class ReceiveFile {
	private String   friendIp = null;
	InetAddress addr;
	private File file;      
	  public ReceiveFile(String friendIp,File file){        
		 this.friendIp=friendIp;
		 this.file=file;
		 }      
	  public void run(){      
		  int length;      
		  FileOutputStream fileOutputStream;      
		  Socket socket;   
		  try {    
			  fileOutputStream = new FileOutputStream(file);      
			  if(friendIp!=null){        
				  try {         
					  addr=InetAddress.getByName(friendIp);       
					  try {       
						  socket = new Socket(addr,IpConstant.filePort);
						  System.out.println("hahah");
					      try{        
					    	  InputStream netin=socket.getInputStream();              
					    	  InputStream in=new DataInputStream(new BufferedInputStream(netin));              
					    	  byte[] buf=new byte[2048];                    
					    	  length=in.read(buf);              
					    	  while(length!=(-1)){          
					    		  fileOutputStream.write(buf, 0, length);                
					    		  length=in.read(buf);              
					    		  }               
					    	      fileOutputStream.flush();    
					    	  }finally{   
					    		  fileOutputStream.close(); 
					    		  socket.close();
					    	  }
					  } catch (IOException e) {      
						  e.printStackTrace();        
						  }      
					  } catch (UnknownHostException e) {       
						  e.printStackTrace();      
						  }  }      
				      } catch (FileNotFoundException e) {    
					  	e.printStackTrace(); 
					  }
			  }
		 
}
