package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
/**
 * 如果有Tcp连接进来则开一个chatDialog
 * @author bingqin
 * @date 2017年6月10日
 */
public class ChatDialog extends JFrame{
	public TextArea chat;  
	public TextField sendchat;  
	public Button sendButton;
	Button sendFileButton;
	public Button receiveFileButton;  
	public ChatDialog() {   
		Label title=new Label("密函:");    
		
		//创建个只读文本，用来显示别人发过来的消息   
		chat=new TextArea(20,20);   
		chat.setEditable(false);   
		
		//创建个允许编辑的文本，用来发送一对一消息  
		sendchat=new TextField(25); //  
		sendButton=new Button("发送");   
		//sendFileButton=new Button("发送文件");   
		receiveFileButton=new Button("接收文件");   
		receiveFileButton.setEnabled(false);    
		Panel p=new Panel(new FlowLayout(FlowLayout.LEFT));   
		setBackground(Color.YELLOW);    
	//	setTitle(name);
		setSize(400, 200);       
		setLocation(450, 100);       
		setResizable(false);       
		add("North",title);   
		add("Center",chat);  
		p.add(sendchat);   
		p.add(sendButton);  
		//p.add(sendFileButton);   
		p.add(receiveFileButton);   
		add("South",p);  
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e){
				e.getWindow().dispose();
			}
		});
		setVisible(true);
	}
	public static void main(String[] args) {
		//new ChatDialog("dddf");
	}
}
