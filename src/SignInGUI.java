import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JPasswordField;

public class SignInGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUni;
	private JButton btnCourseworks;
	private JButton btnSsol;
	private JPasswordField pwdPassword;
	private SignInController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInGUI frame = new SignInGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignInGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 281, 162);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		txtUni = new JTextField();
		txtUni.setText("UNI");
		GridBagConstraints gbc_txtUni = new GridBagConstraints();
		gbc_txtUni.insets = new Insets(0, 0, 5, 0);
		gbc_txtUni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUni.gridx = 0;
		gbc_txtUni.gridy = 0;
		contentPane.add(txtUni, gbc_txtUni);
		txtUni.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
		gbc_pwdPassword.insets = new Insets(0, 0, 5, 0);
		gbc_pwdPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdPassword.gridx = 0;
		gbc_pwdPassword.gridy = 1;
		contentPane.add(pwdPassword, gbc_pwdPassword);

		btnCourseworks = new JButton("Courseworks");
		GridBagConstraints gbc_btnCourseworks = new GridBagConstraints();
		gbc_btnCourseworks.insets = new Insets(0, 0, 5, 0);
		gbc_btnCourseworks.gridx = 0;
		gbc_btnCourseworks.gridy = 2;
		contentPane.add(btnCourseworks, gbc_btnCourseworks);

		btnSsol = new JButton("SSOL");
		GridBagConstraints gbc_btnSsol = new GridBagConstraints();
		gbc_btnSsol.gridx = 0;
		gbc_btnSsol.gridy = 3;
		contentPane.add(btnSsol, gbc_btnSsol);

		controller = new SignInController();
		txtUni.setText(controller.getUsername());
		pwdPassword.setText(controller.getPassword());
		btnCourseworks.addActionListener(new ButtonListener());
		btnSsol.addActionListener(new ButtonListener());
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controller.setProfile(txtUni.getText(), String.valueOf(pwdPassword.getPassword()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (e.getSource() == btnCourseworks) {
				ControllerWorker worker = new ControllerWorker("courseworks");
				worker.execute();
			} else if (e.getSource() == btnSsol) {
				ControllerWorker worker = new ControllerWorker("ssol");
				worker.execute();
			}
		}

	}

	private class ControllerWorker extends SwingWorker<Void, Void> {

		private String page;

		public ControllerWorker(String page) {
			this.page = page;
		}

		@Override
		protected Void doInBackground() throws Exception {
			SignInGUI.this.controller.run(page);
			return null;
		}
	}
}
