package com.DrW0if.code;

import java.io.FileWriter;
import java.io.IOException;

import jssc.SerialPortList;
import jssc.SerialPort;
import jssc.SerialPortException;

public class Utility {
	
	public static boolean isInt(String s) {
		try { Integer.parseInt(s);}
		catch(NumberFormatException e){ return false; }
		return true;
	}
	
	public static void portList(){
		String list[] = SerialPortList.getPortNames();
		
		if(list.length == 0)
			System.out.println("No ports found!");
		
		for(String app : list)
			System.out.println(app);
	}
	
	public static void readFromConnection(String strSerial, String strBaudRate, String filename) {
		
		int baudRate = 9600;
		
		if(strBaudRate == null) {
			System.err.println("Invalid Baudrate");
			System.exit(1);
		}
		
		else {
			try {
				baudRate = Integer.parseInt(strBaudRate);
			}
			catch(NumberFormatException e) {
				System.err.println("Invalid Baudrate");
				System.exit(1);
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
						if(dump)	fw.write((char)buffer[i]);
					}
				
			}
		}
		catch(SerialPortException e) {
			System.err.println("Impossible to communicate with port");
		}
		catch (IOException e) {
			System.err.println("Impossible to write to file");
			help();
		}
		finally {
			if(dump) {
				try { fw.close(); } 
				catch (IOException e) { System.err.println("Impossible to close the file"); }
			}
		}
		System.exit(1);
	}
	
	public static void help() {
		String strHelp = "StandAloneSerialMonitor\n\n" +
				"A standalone serial monitor useful with arduino that needs just java jre and jssc to be used\n\n" +
				"Usages:\n" +
				"--interactive or no flag: GUI usage\n" +
				"-h: for the flag list\n" +
				"-l: for the list of available ports\n" + 
				"-r <port> [baudrate]: reads and prints to console, port is mandatory while baudrate can be omitted (9600 default)\n" +
				"-f <nomeFile>: used with -r parameter reads from specified port (and baudrate) and logs everything to specified file";
		System.out.println(strHelp);
	}

}
