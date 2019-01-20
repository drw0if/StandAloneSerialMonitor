package com.DrW0if.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import jssc.SerialPort;
import jssc.SerialPortException;

public class gui_monitor implements Runnable{
	
	String port;
	int baudrate;
	SerialPort connection = null;
	
	public gui_monitor(String port, int baudrate) { 
		this.port = port;
		this.baudrate = baudrate;
		connection = new SerialPort(this.port);
		run();
	}
	
	@Override
	public void run() {
		//Frame
		JFrame window = new JFrame("StandAloneSerialMonitor");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setPreferredSize(new Dimension(800,300));
						
		//Panel
		JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			window.getContentPane().add(panel);
				
		//layout
		SpringLayout layout = new SpringLayout();
			panel.setLayout(layout);
			
		//Input field
		JTextField input = new JTextField();
			input.setPreferredSize(new Dimension(700, 30));
			layout.putConstraint(SpringLayout.NORTH, 	input, 	5, SpringLayout.NORTH, 	panel);
			layout.putConstraint(SpringLayout.WEST, 	input, 	5, SpringLayout.WEST, 	panel);
			panel.add(input);
		
		//Submit button
		JButton submit = new JButton("Send");
			submit.setPreferredSize(new Dimension(100, 29));
			submit.setBackground(Color.white);
			layout.putConstraint(SpringLayout.NORTH, 	submit, 5, SpringLayout.NORTH, 	panel);
			layout.putConstraint(SpringLayout.EAST, 	input, 	-5, SpringLayout.WEST, 	submit);
			layout.putConstraint(SpringLayout.EAST, 	submit, -5, SpringLayout.EAST, 	panel);
			panel.add(submit);
		
		//Output area
		JTextPane output = new JTextPane();
			output.setEditable(false);
		
		//Output scroller
		JScrollPane scroll = new JScrollPane(new JPanel().add(output), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			layout.putConstraint(SpringLayout.WEST, 	scroll, 5, SpringLayout.WEST, 	panel);
			layout.putConstraint(SpringLayout.NORTH, 	scroll, 5, SpringLayout.SOUTH, 	input);
			layout.putConstraint(SpringLayout.EAST, 	scroll, -5, SpringLayout.EAST, 	panel);
			layout.putConstraint(SpringLayout.SOUTH, 	scroll, -5, SpringLayout.SOUTH, panel);
			panel.add(scroll);
		
		//Events
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if(connection != null && connection.isOpened())
					try { connection.closePort();}
					catch(Exception e){ e.printStackTrace(); }
			}
		});
		
		//Show frame
		window.pack();
		window.setVisible(true);
		
		//Open connection and add listener to the connection
		try {
			connection.openPort();
			connection.setParams(baudrate, 8, 1, 0);
			
			connection.addEventListener(e -> {
				if(e.isRXCHAR()) {
					try {
						byte[] buffer = connection.readBytes();
						String readed = "";
						
						if(buffer != null)
							for(byte b : buffer) 
								readed += (char)b;
						
						Document doc = output.getDocument();					
						doc.insertString(doc.getLength(), readed, null);
						
					} 
					catch (SerialPortException ex) { JOptionPane.showMessageDialog(window, "An error occurred while reading from port", "Error while reading from port", JOptionPane.ERROR_MESSAGE); } 
					catch (BadLocationException ex) { ex.printStackTrace(); }
				}
				
			}, SerialPort.MASK_RXCHAR);
			
		} catch (SerialPortException e) {
			JOptionPane.showMessageDialog(window, "It's impossible to connect to the port", "Error while communicating to the port", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}