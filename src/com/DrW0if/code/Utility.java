package com.DrW0if.code;

import java.io.FileWriter;
import java.io.IOException;

import jssc.SerialPortList;
import jssc.SerialPort;
import jssc.SerialPortException;

public interface Utility {
	
	public static void portList(){
		String list[] = SerialPortList.getPortNames();
		
		if(list.length == 0)
			System.out.println("No ports found!");
		
		for(String app : list)
			System.out.println(app);
	}
	
	public static void readFromConnection(String strSerial, String strBaudRate, String filename) {
		int baudRate;
		
		if(strBaudRate == null) 
			baudRate = 9600;
		else {
			try {
				baudRate = Integer.parseInt(strBaudRate);
			}
			catch(NumberFormatException e) {
				System.err.println("Invalid Baudrate, assuming 9600");
				baudRate = 9600;
			}
		}
		
		boolean dump = false;
		FileWriter fw = null;
		
		if(filename != null)
			dump = true;
		
		SerialPort port = new SerialPort(strSerial);
		try {
			if(dump)
				fw = new FileWriter(filename);
			
			port.openPort();
			port.setParams(baudRate, 8, 1, 0);
			System.out.println("Connected to " + strSerial + " at " + baudRate + " baud");
			while(true) {
				byte[] buffer = port.readBytes();
				
				if(buffer != null)
					for(int i = 0; i < buffer.length; i++) {
						System.out.print((char)buffer[i]);
						if(dump)
							fw.write((char)buffer[i]);
					}
				
			}
		}
		catch(SerialPortException e) {
			System.err.println("Impossible to communicate with port");
		}
		catch (IOException e) {
			System.err.println("Impossible writing to file");
			help();
		}
		finally {
			if(dump) {
				try { fw.close(); } 
				catch (IOException e) { System.err.println("Impossible closing the file"); }
			}
			
			System.exit(1);
		}
	}
	
	public static void help() {
		String strHelp = "StandAlone Serial monitor is used as normal arduino serial monitor"
						+ " but is used without arduino IDE";
		
		System.out.println(strHelp);
	}

}
