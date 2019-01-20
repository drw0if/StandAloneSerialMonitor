package com.DrW0if.code;

public class StandAloneSerialMonitor {
	public static void main(String... args) {
			
		//0 = help, 1 = list, 2 = read, 3 = wip(interactive mode), 4 = GUI
		int op = 4;
		String strSerial = null,
			   strBaud = null,
			   fileName = null;
		
		for(int i = 0; i < args.length; i++) {
			
			if(args[i].equals("-h")) {
				op = 0;
				break;
			}
			
			//--interactive for GUI
			if(args[i].equals("--interactive")) {
				op = 4;
				break;
			}
			
			//-l for the list of the ports
			if(args[i].equals("-l")) {
				op = 1;
				break;
			}
			
			//-r for reading 
			if(args[i].equals("-r")) {
				op = 2;
				try {
					if(!args[i+1].startsWith("-")) {
						strSerial = args[i+1];
						i++;
					}
					else {
						System.err.println("Port not specified");
						System.exit(1);
					}
				}
				catch(ArrayIndexOutOfBoundsException e) { 
					System.err.println("Port not specified");
					Utility.help();
					System.exit(1);
				}
				
				try {
					if(!args[i+1].startsWith("-")) {
						strBaud = args[i+1];
						i++;
					}
				}
				catch(ArrayIndexOutOfBoundsException e) { 
					System.out.println("No baudrate, 9600 is default");
					strBaud = "9600";
				}
			}
			
			//-f for output on file
			if(args[i].equals("-f")) {
				try {
					if(!args[i+1].startsWith("-")) {
						fileName = args[i+1];
						i++;
					}
				}
				catch(ArrayIndexOutOfBoundsException e) { 
					System.out.println("File not specified, assuming no dump to file");
				}
			}
		}
		
		switch(op) {
			case 0: Utility.help(); break;
			case 1: Utility.portList(); break;
			case 2: Utility.readFromConnection(strSerial, strBaud, fileName); break;
			case 4: new gui_menu(); 
		}
		
	}
}
