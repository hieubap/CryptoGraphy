package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window extends JFrame{
	public File file;
	public String hashcode;
	
	public Window() {
		setTitle("check signature");
		setSize(300,280);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JButton buttonchoose = new JButton("Open"),
				buttoncheck = new JButton("Check"),
				buttonhash = new JButton("Hash");
		TextField textfile = new TextField(),
				textpublickey1 = new TextField(),
				textpublickey2 = new TextField();
		
		
		buttonchoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				JFileChooser choose = new JFileChooser();
				choose.showOpenDialog(Window.this);
				file = choose.getSelectedFile();
				Graphics g = Window.this.getGraphics();
				g.setFont(new Font(Font.SANS_SERIF, ABORT, 13));
				g.drawString(file.getPath(), 55, 70);
				
				
			}
		});
		buttonchoose.setBounds(20,150,80,40);
		
		buttoncheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hashcheck();
				
			}
		});
		buttoncheck.setBounds(100,150,80,40);
		
		buttonhash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(file != null)
				System.out.println(hashvalue(file));
				textfile.setText(hashvalue(file));
			}
		});
		buttonhash.setBounds(180,150,80,40);
		
		
		textfile.setBounds(100, 60, 150, 20);
		textfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hashcode = textfile.getText();
				System.out.println(hashcode);
			}
		});
		textpublickey1.setBounds(50, 120, 40, 20);
		textpublickey1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hashcode = textfile.getText();
				System.out.println(hashcode);
			}
		});
		textpublickey2.setBounds(150, 120, 40, 20);
		textpublickey2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hashcode = textfile.getText();
				System.out.println(hashcode);
			}
		});
		
		add(textfile);
		add(textpublickey1);
		add(textpublickey2);
		
		add(buttonchoose);
		add(buttoncheck);
		add(buttonhash);
		
		setVisible(true);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font(Font.SANS_SERIF, ABORT, 15));
		g.drawString("File :", 15, 70);
		g.drawString("d", 20, 160);
		g.drawString("d", 120, 160);
		
		g.drawString("Hash Value :", 15, 100);
	}
	public static void main(String[] args)
	{
		Window window = new Window();


	}
	public void hashcheck() {
		try {
			InputStream input = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
            int nread;
            while ((nread = input.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
            StringBuilder result = new StringBuilder();
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            
			System.out.println(result.toString());
			
			
			if(result.toString().equals(hashcode)) {
				JOptionPane.showMessageDialog(null,"file an toàn");
			}
			else {
				JOptionPane.showMessageDialog(null,"file không an toàn");
			}
			input.close();
			}
			catch(IOException ex) {
				JOptionPane.showMessageDialog(null,"không tìm thấy file");
			}
			catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "bạn chưa chọn file");
			}
			catch(NoSuchAlgorithmException ex) {
				
			}
	}
	
	public String hashvalue(File file) {
		try {
			InputStream input = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] buffer = new byte[1024];
            int nread;
            while ((nread = input.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
            StringBuilder result = new StringBuilder();
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            input.close();
            return result.toString();
		}
			catch(IOException ex) {
				JOptionPane.showMessageDialog(null,"không tìm thấy file");
			}
			catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "bạn chưa chọn file");
			}
			catch(NoSuchAlgorithmException ex) {
				
			}
		return null;
	}
}
