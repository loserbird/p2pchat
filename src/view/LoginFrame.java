package view;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * 登录窗口
 * @author bingqin
 * @date 2017年6月10日
 */
public class LoginFrame extends JFrame {
	JPanel panel;
	JPanel jp1;
	JPanel jp2;
	JTextField usernameField; // 用户名
	JPasswordField passwordField; // 密码
	JLabel usernameLabel,passwordLabel;
	JButton loginbutton;
	
	public LoginFrame(){
		panel = new JPanel();
		jp1 = new JPanel();
		jp2 = new JPanel();
		usernameField = new JTextField(24);
		passwordField = new JPasswordField(24);
		
		usernameLabel = new JLabel("账号");
		passwordLabel = new JLabel("密码");
		
		loginbutton = new JButton("登录");
		
		jp1.add(usernameLabel);
		jp1.add(usernameField);
		jp2.add(passwordLabel);
		jp2.add(passwordField);
		panel.add(jp1);
		panel.add(jp2);
		panel.add(loginbutton);
		add(panel);
		
		this.setResizable(false); // 不允许放大，改变窗口大小等
		this.setSize(380, 275);
		this.setLocation(500, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		loginbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if(username == null || username == ""){
					JOptionPane.showMessageDialog(null, "用户名不能为空");
				}else{
					new P2PFriend(username);
				}
				
			}
		});
	}
	public static void main(String[] args) {
		new LoginFrame();
	}
}
