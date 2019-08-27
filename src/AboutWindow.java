import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class AboutWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutWindow window = new AboutWindow();
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
	public AboutWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 372, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblSrbCrossplatformLauncher = new JLabel("SRB2 Cross-platform launcher");
		lblSrbCrossplatformLauncher.setBounds(10, 11, 179, 14);
		frame.getContentPane().add(lblSrbCrossplatformLauncher);
		
		JLabel lblVersion = new JLabel("Version 2.0.2");
		lblVersion.setBounds(10, 36, 179, 14);
		frame.getContentPane().add(lblVersion);
		
		JLabel lblCopyrightHitcoder = new JLabel("HitCoder // 2019");
		lblCopyrightHitcoder.setBounds(10, 61, 145, 14);
		frame.getContentPane().add(lblCopyrightHitcoder);
		
		JTextArea txtrMain = new JTextArea();
		txtrMain.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrMain.setEditable(false);
		txtrMain.setWrapStyleWord(true);
		txtrMain.setLineWrap(true);
		txtrMain.setText("This is a frontend that allows you to launch SRB2 with any settings desired.\r\n\r\nThis program was initially modeled after M. J. Spangler's \"StartSRB2 Beta\" and so many elements are made to be set out in a similar way, though there are a lot of custom features to the launcher to try to give it a comfortable and clean feel.\r\n\r\nModification and distribution of this program is permitted, providing credit is given to the original creator, HitCoder.\r\n\r\nThe source code for this launcher can be found at https://github.com/HitCoder9768/LauncherBlast2\r\n\r\nContact:\r\nhttp://mb.srb2.org/member.php?u=5287\r\nhttp://github.com/HitCoder9768/");
		txtrMain.setBounds(10, 86, 346, 330);
		frame.getContentPane().add(txtrMain);
		
		JButton btnOk = new JButton("Close");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnOk.setBounds(267, 427, 89, 23);
		frame.getContentPane().add(btnOk);
	}
}
