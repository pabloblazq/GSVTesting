package com.blame.gsv.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;

import com.blame.gsv.GSVPanorama;
import com.blame.gsv.jaxbgenerated.Panorama;
import com.blame.gsv.jaxbgenerated.Panorama.AnnotationProperties.Link;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class TestSwing extends JFrame {

	private static final long serialVersionUID = -2759470829122997567L;

	protected JPanel contentPane;
	protected ImagePanel imagePanel;
	protected JTextPane textPanel;
	protected JButton btnNewButton;

	protected Vector<JButton> navButtons;

	protected GSVPanorama gp;
	protected Panorama panorama;

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
	 * @throws Exception 
	 */
	public TestSwing() throws Exception {

		gp = new GSVPanorama(); 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		imagePanel = new ImagePanel();
		imagePanel.setBounds(10, 46, 640, 480);
		contentPane.add(imagePanel);

		textPanel = new JTextPane();
		textPanel.setBounds(10, 540, 640, 128);
		contentPane.add(textPanel);

		BufferedImage bi = gp.getPanoramaImageFromURL("XYEpYsvzCXYck-Am0FypTQ", 640, 480, 120, 0, 0, "AIzaSyDxcLOH13FSJrUV2KfU1RfBR6TsuJ6DDRI");
		imagePanel.setImage(bi);
		imagePanel.repaint();

		panorama = gp.getPanoramaMetadataFromURL("XYEpYsvzCXYck-Am0FypTQ");
		textPanel.setText(panorama.toString());
		int xPosition = 10;
		for(Link link : panorama.getAnnotationProperties().getLink()) {
			NavigateButton button = new NavigateButton(link.getYawDeg());
			button.addActionListener(new ButtonActionListener());
			button.setBounds(xPosition, 12, 150, 23);
			contentPane.add(button);
			xPosition += 160;
		}

	}

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				NavigateButton originButton = (NavigateButton) e.getSource();
				
				//TODO: look for the link having the same directionDegree than the original button
				
				//TODO: get the panoramaId for that link and refresh the image for that panoramaId, and the panorama object, so that the buttons are shown for the new panorama object
				
				BufferedImage bi = gp.getPanoramaImageFromURL("XYEpYsvzCXYck-Am0FypTQ", 640, 480, 120, 0, 0, "AIzaSyDxcLOH13FSJrUV2KfU1RfBR6TsuJ6DDRI");
				imagePanel.setImage(bi);
				imagePanel.repaint();
				
				if(navButtons != null)
					for(JButton button : navButtons)
						contentPane.remove(button);

				int xPosition = 10;
				for(Link link : panorama.getAnnotationProperties().getLink()) {
					NavigateButton button = new NavigateButton(link.getYawDeg());
					button.addActionListener(new ButtonActionListener());
					button.setBounds(xPosition, 12, 150, 23);
					contentPane.add(button);
					xPosition += 160;
				}
				contentPane.repaint();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
