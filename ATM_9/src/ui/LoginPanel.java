package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import BAMSException.ATMException;
import BAMSException.RegisterException;

import javax.swing.JButton;
import atm_feature.*;
public class LoginPanel {

	private JFrame frame;
	private JTextField id;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPanel window = new LoginPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws RegisterException 
	 */
	public LoginPanel() throws RegisterException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws RegisterException 
	 */
	private void initialize() throws RegisterException {
		frame = new JFrame("登陆");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel name = new JLabel("用户账号 ：");
		name.setBounds(52, 55, 88, 18);
		frame.getContentPane().add(name);
		
		JLabel label_password = new JLabel("用户密码 ：");
		label_password.setBounds(52, 124, 88, 18);
		frame.getContentPane().add(label_password);
		
		id = new JTextField();
		id.setBounds(153, 52, 140, 24);
		frame.getContentPane().add(id);
		id.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(154, 121, 139, 24);
		frame.getContentPane().add(password);
		password.setColumns(10);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setBounds(64, 180, 113, 27);
		frame.getContentPane().add(btnSignIn);
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					atm_feature.Account account=atm_feature.Bank.getInstance().login(Long.parseLong(id.getText()), Integer.parseInt(password.getText()));
					
			        
					if (account!=null) {
						frame.dispose();
						String[] c= {String.valueOf(account.id)};
						new BusinessPanel(c).main(c);
					}
				} catch (ATMException e2) {
					e2.printStackTrace();
				}
				
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(229, 180, 113, 27);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainPanel().main(null);
			}
		});
	}
}
