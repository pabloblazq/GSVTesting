package com.blame.gsv.swing;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.blame.gsv.jaxbgenerated.Panorama.AnnotationProperties.Link;

public class NavigateButton extends JButton {

	private static final long serialVersionUID = 8854356044472227608L;

	protected Link link;

	public NavigateButton(Link link) {
		super("Go to " + link.getYawDeg());
		this.link = link;
		this.setHorizontalAlignment(SwingConstants.LEFT);
	}

	public NavigateButton(Link previousLink, Link link) {
		super();
		String text = null;
		if(previousLink.getRoadArgb().equals(link.getRoadArgb())) {
			text = "Continue on road ";
		}
		else {
			Float differenceDegree = link.getYawDeg() - previousLink.getYawDeg();
			if(differenceDegree <= -180)
				differenceDegree += 360;
			else if(differenceDegree > 180)
				differenceDegree -= 360;
			
			if(differenceDegree < 0)
				text = "Turn left on road ";
			else
				text = "Turn right on road ";
		}

		text += "\"" + link.getLinkText() + "\" (" + link.getYawDeg() + ")";

		this.setText(text);
		this.link = link;
		this.setHorizontalAlignment(SwingConstants.LEFT);
	}

	public Link getLink() {
		return link;
	}

}
