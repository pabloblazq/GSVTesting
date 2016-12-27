package com.blame.gsv.swing;

import javax.swing.JButton;

public class NavigateButton extends JButton {

	private static final long serialVersionUID = 8854356044472227608L;

	protected Float directionDegrees;

	public NavigateButton(Float directionDegrees) {
		super("Go to " + directionDegrees);
		this.directionDegrees = directionDegrees;
	}
	
	
}
