package com.DrW0if.code;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import jssc.SerialPortList;

public class gui_menu implements Runnable{

	public gui_menu() { run(); }
	
	@Override
	public void run() {
		//Main window
		JFrame window = new JFrame();
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setPreferredSize(new Dimension(200, 238));
			window.setResizable(false);

		//Main panel
		JPanel panel = new JPanel();
			panel.setBackground(Color.white);
			window.getContentPane().add(panel);
		
		//Layout
		SpringLayout layout = new SpringLayout();
			panel.setLayout(layout);
		
		//Port label
		JLabel portLabel = new JLabel("Select port:");
			layout.putConstraint(SpringLayout.NORTH, 	portLabel, 5, 		SpringLayout.NORTH, panel);
			layout.putConstraint(SpringLayout.WEST, 	portLabel, 10, 		SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	portLabel, -10, 	SpringLayout.EAST, panel);
			portLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(portLabel);
		
		//Port selector
		JComboBox<String> portSelector = new JComboBox<String>();
			portSelector.setBackground(Color.WHITE);
			layout.putConstraint(SpringLayout.NORTH, 	portSelector, 10, 	SpringLayout.SOUTH, portLabel);
			layout.putConstraint(SpringLayout.WEST, 	portSelector, 10, 	SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	portSelector, -10, 	SpringLayout.EAST, panel);
			for(String port: SerialPortList.getPortNames())
				portSelector.addItem(port);
			panel.add(portSelector);
		
		//Baudrate label
		JLabel baudLabel = new JLabel("Insert baudrate:");
			layout.putConstraint(SpringLayout.NORTH, 	baudLabel, 10, 		SpringLayout.SOUTH, portSelector);
			layout.putConstraint(SpringLayout.WEST, 	baudLabel, 10, 		SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	baudLabel, -10, 	SpringLayout.EAST, panel);
			baudLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(baudLabel);
		
		//Baudrate selector 
		JTextField baudSelector = new JTextField();
			baudSelector.setText("115200");
			layout.putConstraint(SpringLayout.NORTH, 	baudSelector, 10, 	SpringLayout.SOUTH, baudLabel);
			layout.putConstraint(SpringLayout.WEST, 	baudSelector, 10, 	SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	baudSelector, -10, 	SpringLayout.EAST, panel);
			panel.add(baudSelector);
		
		//Connect button
		JButton connectButton = new JButton("Dial");
			connectButton.setBackground(Color.WHITE);
			layout.putConstraint(SpringLayout.NORTH, 	connectButton, 15, 	SpringLayout.SOUTH, baudSelector);
			layout.putConstraint(SpringLayout.WEST, 	connectButton, 10, 	SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	connectButton, -10, SpringLayout.EAST, panel);
			//Open monitor with choosen port
			panel.add(connectButton);
			
		//Refresh Button
		JButton refreshButton = new JButton("Refresh");
			refreshButton.setBackground(Color.WHITE);
			layout.putConstraint(SpringLayout.NORTH, 	refreshButton, 10, 	SpringLayout.SOUTH, connectButton);
			layout.putConstraint(SpringLayout.WEST, 	refreshButton, 10, 	SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.EAST, 	refreshButton, -10, SpringLayout.EAST, panel);
			layout.putConstraint(SpringLayout.SOUTH, 	panel, 10, SpringLayout.SOUTH, refreshButton);
			panel.add(refreshButton);
		
		//Events
		baudSelector.addActionListener(e -> connectButton.doClick());
			
		connectButton.addActionListener(e -> {
			if(Utility.isInt(baudSelector.getText())) {
				window.dispose(); 
				new gui_monitor((String)portSelector.getSelectedItem(), (int)Integer.parseInt(baudSelector.getText())); 
			}
			else JOptionPane.showMessageDialog(window, "Invalid baudrate, please insert a valid value!", "Invalid baudrate", JOptionPane.ERROR_MESSAGE);
		});
		
		refreshButton.addActionListener(e -> { 
			portSelector.removeAllItems(); 
			for(String port: SerialPortList.getPortNames())
				portSelector.addItem(port); 
		});
				
		window.pack();
		window.setVisible(true);		
	}
}
