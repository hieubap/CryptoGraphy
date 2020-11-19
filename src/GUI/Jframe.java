package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Jframe extends JFrame {

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jframe frame = new Jframe();
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
	public Jframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("check");
		btnNewButton.setBounds(22, 36, 85, 21);
		getContentPane().add(btnNewButton);
	}
}
