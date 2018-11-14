package com.DrW0if.test;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizableFrame extends JFrame implements ComponentListener{

	JLabel label;
	
	
	public ResizableFrame(String title){
		super(title);
		label = new JLabel();
		getContentPane().add(label);
		getContentPane().addComponentListener(this);
	}
	
	
	@Override
	public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void componentMoved(ComponentEvent arg0) {}

	@Override
	public void componentResized(ComponentEvent arg0) {
		label.setText(this.getWidth() + " " + this.getHeight());
	}

	@Override
	public void componentShown(ComponentEvent arg0) {}

	
	
	
}
