/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import po.User;



/**
 * @author bingqin
 * @date 2017年6月8日
 */
public class MainFrame extends JFrame{
	 public class Apanel extends Panel{  
		 public TextArea chat;  
		 public IpListPanel ipListPanel;  
		 public Apanel(){   
			 //创建个只读文本   
			 chat=new TextArea(40,40);   
			 chat.setEditable(false);   
			 ipListPanel=new IpListPanel();   
			 //布置容器的边框布局  
			 setLayout(new BorderLayout());   
			 add("West",chat);   
			 add("East",ipListPanel);    
			}  
	 	}  
	 public class IpListPanel extends Panel{  
		public List ipList;  
		IpListPanel(){   
			ipList=new List(25,false);    
			 setLayout(new BorderLayout());    
			 add("Center",ipList);   
			 add("North",new Label("在线用户列表"));   
			 add("East",new Label());    
			 add("South",new Label("双击一个人的ip可以与他聊天"));      
			 }  
		 }    
	 public class BPanel extends Panel{  
		 public TextField msg;   
		 Button sendButton,refreshMsgButton,refreshUserButton; 
		 public BPanel(){  
			 msg=new TextField(38);   
			 sendButton=new Button("发送");
			 refreshMsgButton=new Button("清空聊天区");     
			 refreshUserButton=new Button("刷新在线用户");         
			 setLayout(new FlowLayout(FlowLayout.LEFT));   
			// add(new Label("发送给所有用户"));    
			 add(msg);
			 add(sendButton);
			 add(refreshMsgButton);
			 add(refreshUserButton);
		 }
	 }
	 
	 public Label label;        
	 public Apanel pa;        
	 public BPanel pb;                
	 public  MainFrame(User user) {
		 label=new Label("聊天记录");         
	      pa=new Apanel();          
	      pb=new BPanel();          
	      setLayout(new BorderLayout());         
	      setBackground(Color.YELLOW);         
	      setSize(550, 450);         
	      setLocation(450, 100);         
	      setResizable(false);         
	      add("North",label);         
	      add("Center",pa);         
	      add("South",pb);          
	      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
	      this.setTitle(user.getUsername()+"--"+user.getIp());         
	      setVisible(true); 
	    }
	 
}
