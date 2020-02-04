package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import BAMSException.ATMException;
import BAMSException.BalanceNotEnoughException;
import BAMSException.LoginException;
import atm_feature.Bank;
import atm_feature.CreditAccount;
import atm_feature.LoanCreditAccount;
import atm_feature.LoanSavingAccount;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;

public class BusinessPanel {

	private JFrame frame;
	private JTextField numberbox;
	private JTextField idbox;
	JComboBox todoBox = new JComboBox();
	JLabel showbalance = new JLabel();
	JLabel showlimit = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusinessPanel window = new BusinessPanel(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws LoginException 
	 */
	public BusinessPanel(String[] arg) throws LoginException {
		initialize(arg);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws LoginException 
	 */
	private void initialize(String[] args) throws LoginException {
		long id=Long.parseLong(args[0]);
		atm_feature.Account act=atm_feature.Bank.getInstance().getAct(id);
		frame = new JFrame("Client");
		frame.setBounds(100, 100, 450, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("账户 ：");
		lblId.setBounds(36, 23, 72, 18);
		frame.getContentPane().add(lblId);
		
		JLabel lblName = new JLabel("姓名 ：");
		lblName.setBounds(36, 60, 72, 18);
		frame.getContentPane().add(lblName);
		
		JLabel lblBalance = new JLabel("余额 ：");
		lblBalance.setBounds(36, 100, 72, 18);
		frame.getContentPane().add(lblBalance);
		
		JLabel lblLimit = new JLabel("信用额度 ：");
		lblLimit.setBounds(36, 143, 93, 18);
		frame.getContentPane().add(lblLimit);
		
		JLabel lblLoan = new JLabel("贷款额 ：");
		lblLoan.setBounds(36, 188, 72, 18);
		frame.getContentPane().add(lblLoan);
		
		numberbox = new JTextField();
		numberbox.setBounds(178, 231, 100, 24);
		frame.getContentPane().add(numberbox);
		numberbox.setColumns(10);
		
		
		
		JButton btnCancel = new JButton("退出");
		btnCancel.setBounds(211, 268, 113, 27);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainPanel().main(null);
				
			}
		});
		
		JLabel showId = new JLabel();
		showId.setHorizontalAlignment(SwingConstants.RIGHT);
		showId.setBounds(189, 23, 175, 18);
		frame.getContentPane().add(showId);
		showId.setText(String.valueOf(act.getId()));
		
		JLabel showname = new JLabel();
		showname.setHorizontalAlignment(SwingConstants.RIGHT);
		showname.setBounds(189, 60, 175, 18);
		frame.getContentPane().add(showname);
		showname.setText(String.valueOf(act.getName()));
		
		
		showbalance.setHorizontalAlignment(SwingConstants.RIGHT);
		showbalance.setBounds(189, 100, 175, 18);
		frame.getContentPane().add(showbalance);
		showbalance.setText(String.valueOf(act.getBalance()));
		
		
		showlimit.setHorizontalAlignment(SwingConstants.RIGHT);
		showlimit.setBounds(189, 143, 175, 18);
		frame.getContentPane().add(showlimit);
		if (act instanceof atm_feature.CreditAccount) {
			CreditAccount cract=(CreditAccount) act;
			showlimit.setText(String.valueOf(cract.getCeiling()));
		} else {
			showlimit.setText("NULL");
		}
		
		JLabel showloan = new JLabel();
		showloan.setHorizontalAlignment(SwingConstants.RIGHT);
		showloan.setBounds(189, 188, 175, 18);
		frame.getContentPane().add(showloan);
		if (act instanceof LoanCreditAccount) {
			LoanCreditAccount LCA=(LoanCreditAccount) act;
			showloan.setText(String.valueOf(LCA.getLoan()));
		}else if (act instanceof LoanSavingAccount) {
			LoanSavingAccount LSA=(LoanSavingAccount)act;
			showloan.setText(String.valueOf(LSA.getLoan()));
		} else {
			showloan.setText("NULL");
		}
		
		idbox = new JTextField();
		idbox.setBounds(300, 231, 100, 24);
		idbox.setColumns(10);
		idbox.setToolTipText("汇入账号");
		
		
		todoBox.setFont(new Font("宋体", Font.BOLD, 15));
		todoBox.setBounds(36, 231, 128, 24);
		frame.getContentPane().add(todoBox);
		todoBox.addItem("贷款");		//0
		todoBox.addItem("取款");		//1
		todoBox.addItem("设置透支额度");		//2
		todoBox.addItem("存款");		//3
		todoBox.addItem("还贷");		//4
		todoBox.addItem("转账");		//5
		todoBox.getPreferredSize();
		todoBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (todoBox.getSelectedItem().equals("转账")) {
					frame.getContentPane().add(idbox);
				} else {
					frame.getContentPane().remove(idbox);
				}
			}
		});
		
		JButton btnSubmit = new JButton("提交");
		btnSubmit.setBounds(64, 270, 113, 27);
		frame.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int fea=todoBox.getSelectedIndex();
				switch (fea) {
				case 0:
					try {
						if (act instanceof LoanCreditAccount|act instanceof LoanSavingAccount) {
							
							Bank.getInstance().requestLoan(act.getId(), Double.parseDouble(numberbox.getText()));
							if (act instanceof LoanSavingAccount) {
								LoanSavingAccount LSA=(LoanSavingAccount)act;
								showloan.setText(String.valueOf(LSA.getLoan()));
							} else {
								LoanCreditAccount LCA=(LoanCreditAccount)act;
								showloan.setText(String.valueOf(LCA.getLoan()));
							}
						} else throw new ATMException("账户不支持该功能");
					} catch (ATMException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						numberbox.setText("");
						showbalance.setText(String.valueOf(act.getBalance()));
					}
					break;
				case 1: 
					try {
						Bank.getInstance().withdraw(act.getId(), Double.parseDouble(numberbox.getText()));
						showbalance.setText(String.valueOf(act.getBalance()));
					} catch (NumberFormatException | LoginException | BalanceNotEnoughException e1) {
						
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						numberbox.setText("");
					}
					break;
				case 2:
					try {
						Bank.getInstance().setCeiling(act.getId(), Double.parseDouble(numberbox.getText()));
						CreditAccount cat=(CreditAccount)act;
						showlimit.setText(String.valueOf(cat.getCeiling()));
					} catch (NumberFormatException | LoginException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ATMException e1) {
						
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						numberbox.setText("");
					}
					break;
				case 3:
					try {
						Bank.getInstance().deposit(act.getId(), Double.parseDouble(numberbox.getText()));
						showbalance.setText(String.valueOf(act.getBalance()));
					} catch (NumberFormatException | LoginException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						numberbox.setText("");
					}
					break;
				case 4:
					try {
						Bank.getInstance().payLoan(act.getId(), Double.parseDouble(numberbox.getText()));
						if (act instanceof LoanSavingAccount) {
							LoanSavingAccount LA=(LoanSavingAccount)act;
							showloan.setText(String.valueOf(LA.getLoan()));
							showbalance.setText(String.valueOf(act.getBalance()));
						} else {
							LoanCreditAccount LA=(LoanCreditAccount)act;
							showloan.setText(String.valueOf(LA.getLoan()));
							showbalance.setText(String.valueOf(act.getBalance()));
						} 
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						numberbox.setText("");
					}
					break;
				case 5:
					try {
						if (idbox.getText().equals("")) {
							throw new ATMException("请输入汇入账户账号");
						}
						Bank.getInstance().transfer(act.getId(), Long.parseLong(idbox.getText()), Double.parseDouble(numberbox.getText()));
						showbalance.setText(String.valueOf(act.getBalance()));
					} catch (NumberFormatException | ATMException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						idbox.setText("");
						numberbox.setText("");
					}
					break;

				default:
					break;
				}
			}
		});
		
	}
}
