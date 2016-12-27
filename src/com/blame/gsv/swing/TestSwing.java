package com.blame.gsv.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;

import com.blame.gsv.GSVPanorama;
import com.blame.gsv.jaxbgenerated.Panorama;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class TestSwing extends JFrame {

	private static final long serialVersionUID = -2759470829122997567L;

	protected JPanel contentPane;
	protected JImagePanel imagePanel;
	protected JTextPane textPanel;
	protected JButton btnNewButton;
	protected GSVPanorama gp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing frame = new TestSwing();
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
	public TestSwing() {

		gp = new GSVPanorama(); 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		imagePanel = new JImagePanel();
		imagePanel.setBounds(10, 46, 640, 480);
		contentPane.add(imagePanel);

		textPanel = new JTextPane();
		textPanel.setBounds(10, 540, 640, 128);
		contentPane.add(textPanel);

		btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ButtonActionListener());
		btnNewButton.setBounds(10, 12, 89, 23);
		contentPane.add(btnNewButton);
	}

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				BufferedImage bi = gp.getPanoramaImageFromURL("XYEpYsvzCXYck-Am0FypTQ", 640, 480, 120, 0, 0, "AIzaSyDxcLOH13FSJrUV2KfU1RfBR6TsuJ6DDRI");
				imagePanel.setImage(bi);
				imagePanel.repaint();

				Panorama panorama = gp.getPanoramaMetadataFromURL("XYEpYsvzCXYck-Am0FypTQ");
				textPanel.setText(panorama.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
