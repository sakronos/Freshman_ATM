package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.Choice;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

import BAMSException.RegisterException;
import atm_feature.Bank;

public class RegisterPanel {

	private JFrame frame;
	private JTextField textName;
	private JTextField textPersonalID;
	private JTextField textEmail;
	private JPasswordField passwordField;
	private JPasswordField passagainField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPanel window = new RegisterPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public RegisterPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("注册账户");
		frame.getContentPane().setFont(new Font("宋体", Font.BOLD, 15));
		frame.setBounds(100, 100, 450, 372);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Choice choice = new Choice();
		choice.setBounds(128, 37, 180, 24);
		frame.getContentPane().add(choice);
		choice.add("SavingAccount");
		choice.add("CreditAccount");
		choice.add("LoanSavingAccount");
		choice.add("LoanCreditAccount");
		choice.select(1);
		choice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel labelType = new JLabel("账户类型：");
		labelType.setBounds(41, 43, 85, 18);
		frame.getContentPane().add(labelType);
		
		JLabel label_name = new JLabel("用户名 ：");
		label_name.setBounds(41, 82, 72, 18);
		frame.getContentPane().add(label_name);
		
		JLabel label_password = new JLabel("密码 ：");
		label_password.setBounds(41, 117, 72, 18);
		frame.getContentPane().add(label_password);
		
		JLabel label_passagain = new JLabel("确定密码：");
		label_passagain.setBounds(41, 159, 98, 18);
		frame.getContentPane().add(label_passagain);
		
		JLabel label_personalID = new JLabel("身份证号 ：");
		label_personalID.setBounds(41, 200, 98, 18);
		frame.getContentPane().add(label_personalID);
		
		JLabel lblEmail = new JLabel("E-Mail ：");
		lblEmail.setBounds(41, 231, 72, 18);
		frame.getContentPane().add(lblEmail);
		
		textName = new JTextField();
		textName.setBounds(128, 79, 180, 24);
		frame.getContentPane().add(textName);
		textName.setColumns(10);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				
				try {
					if (passagainField.getText().equals("")|passwordField.getText().equals("")|textName.getText().equals("")|
							textPersonalID.getText().equals("")|textEmail.getText().equals("")) {
						throw new Exception("请输入有效内容");
					}
					Bank.getInstance().register(Integer.parseInt(passwordField.getText()),
							Integer.parseInt(passagainField.getText()), textName.getText(), textPersonalID.getText(), 
							textEmail.getText(), choice.getSelectedItem());
					frame.dispose();
					MainPanel.main(null);
				} catch (NumberFormatException | RegisterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					passwordField.setText("");
					passagainField.setText("");
					textEmail.setText("");
					textName.setText("");
					textPersonalID.setText("");
					e.printStackTrace();
				}
			}
		});
		btnSignUp.setBounds(51, 273, 113, 27);
		frame.getContentPane().add(btnSignUp);
		
		textPersonalID = new JTextField();
		textPersonalID.setBounds(128, 197, 180, 24);
		frame.getContentPane().add(textPersonalID);
		textPersonalID.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(127, 228, 180, 24);
		frame.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(214, 273, 113, 27);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainPanel().main(null);
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(127, 114, 180, 24);
		frame.getContentPane().add(passwordField);
		
		passagainField = new JPasswordField();
		passagainField.setBounds(128, 156, 180, 24);
		frame.getContentPane().add(passagainField);
		
		
	}
}
