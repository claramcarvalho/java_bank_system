package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormManagerLogin {

	JFrame frmManagerAreaLogin;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormManagerLogin window = new FormManagerLogin();
					window.frmManagerAreaLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormManagerLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManagerAreaLogin = new JFrame();
		frmManagerAreaLogin.setTitle("Manager Area Login");
		frmManagerAreaLogin.setBounds(100, 100, 450, 300);
		frmManagerAreaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManagerAreaLogin.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(61, 53, 105, 13);
		frmManagerAreaLogin.getContentPane().add(lblUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(228, 50, 142, 19);
		frmManagerAreaLogin.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(228, 108, 142, 19);
		frmManagerAreaLogin.getContentPane().add(textFieldPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(61, 111, 105, 13);
		frmManagerAreaLogin.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(285, 197, 85, 21);
		frmManagerAreaLogin.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormLogin formLogin = new FormLogin();
				formLogin.frmWelcomeToFortis.setVisible(true);
				
				frmManagerAreaLogin.dispose();
			}
		});
		btnExit.setBounds(190, 197, 85, 21);
		frmManagerAreaLogin.getContentPane().add(btnExit);
	}
}
