package com.blame.gsv.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
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

public class GSVSemiAutoNavigationFrame extends JFrame {

	private static final long serialVersionUID = -2759470829122997567L;

	protected GSVSemiAutoNavigationFrame thisFrame;
	protected JPanel contentPane;
	protected ImagePanel imagePanel;
	protected JTextPane textPanel;
	protected JButton btnNewButton;

	protected Vector<NavigateButton> navButtons;

	protected GSVPanorama gp;
	protected Panorama panorama;
	
	protected boolean autoNavigation = false;
	protected Timer autoNavigationTimer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GSVSemiAutoNavigationFrame frame = new GSVSemiAutoNavigationFrame();
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
	public GSVSemiAutoNavigationFrame() throws Exception {

		thisFrame = this;
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
		navButtons = new Vector<NavigateButton>();
		for(Link link : panorama.getAnnotationProperties().getLink()) {
			NavigateButton button = new NavigateButton(link.getYawDeg());
			button.addActionListener(new NavigateButtonActionListener());
			button.setBounds(xPosition, 12, 150, 23);
			contentPane.add(button);
			navButtons.add(button);
			xPosition += 160;
		}

	}
	
	protected void startTimer() {
		autoNavigationTimer = new Timer(1000, new AutoNavigationTimerListener());
		autoNavigationTimer.setRepeats(false);
		autoNavigationTimer.start();
	}

	class NavigateButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				NavigateButton originButton = (NavigateButton) e.getSource();

				String previousPanoramaId = panorama.getDataProperties().getPanoId();
				
				// look for the link having the same directionDegree than the original button
				Link linkToFollow = null;
				for(Link link : panorama.getAnnotationProperties().getLink()) {
					if(link.getYawDeg() == originButton.getDirectionDegrees()) {
						linkToFollow = link;
						break;
					}
				}				
				// get the panoramaId for that link
				String panoramaId = linkToFollow.getPanoId();
				
				// refresh the image for that panoramaId
				BufferedImage bi = gp.getPanoramaImageFromURL(panoramaId, 640, 480, 120, linkToFollow.getYawDeg(), 0, "AIzaSyDxcLOH13FSJrUV2KfU1RfBR6TsuJ6DDRI");
				imagePanel.setImage(bi);
				imagePanel.repaint();
				
				// refresh the panorama object
				panorama = gp.getPanoramaMetadataFromURL(panoramaId);
				
				// if the new panorama contains only a new link (a link with a panoramaId different from the previous one), then enable the auto navigation mode
				int count = 0;
				for(Link link : panorama.getAnnotationProperties().getLink())
					if(!link.getPanoId().equals(previousPanoramaId))
						count++;
				if(count == 1)
					autoNavigation = true;
				else
					autoNavigation = false;

				// remove the old buttons
				if(navButtons != null) {
					for(JButton button : navButtons)
						contentPane.remove(button);
					navButtons.removeAllElements();
				}

				// show new buttons for the new panorama object
				int xPosition = 10;
				for(Link link : panorama.getAnnotationProperties().getLink())
					if(!link.getPanoId().equals(previousPanoramaId)) {
						NavigateButton button = new NavigateButton(link.getYawDeg());
						button.addActionListener(new NavigateButtonActionListener());
						button.setBounds(xPosition, 12, 150, 23);
						contentPane.add(button);
						navButtons.add(button);
						button.setEnabled(!autoNavigation);
						xPosition += 160;
					}

				if(autoNavigation)
					thisFrame.startTimer();
				
				contentPane.repaint();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	class AutoNavigationTimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			NavigateButton originButton = navButtons.get(0);
			originButton.setEnabled(true);
			originButton.doClick(100);
		}
		
	}
}
