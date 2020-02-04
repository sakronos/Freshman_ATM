package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import BAMSException.RegisterException;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainPanel {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPanel window = new MainPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("ATM终端");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAtm = new JLabel("ATM\u7EC8\u7AEF",JLabel.CENTER);
		lblAtm.setFont(new Font("华文中宋", Font.BOLD, 26));
		lblAtm.setBounds(114, 13, 199, 55);
		frame.getContentPane().add(lblAtm);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(54, 138, 113, 27);
		frame.getContentPane().add(btnSignUp);
		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new RegisterPanel().main(null);
			}
			
		});
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.setBounds(240, 138, 113, 27);
		frame.getContentPane().add(btnSignIn);
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				try {
					new LoginPanel().main(null);
				} catch (RegisterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
}
