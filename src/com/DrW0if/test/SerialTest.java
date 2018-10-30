package com.DrW0if.test;

import jssc.SerialPortList;

public class SerialTest{
	public static void main(String... args) {
		String list[] = SerialPortList.getPortNames();
		
		for(String app : list)
			System.out.println(app);
		
	}
}
