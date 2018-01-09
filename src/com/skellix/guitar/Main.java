package com.skellix.guitar;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main implements Runnable {

	private Robot robot;

	public static void main(String[] args) {
		System.out.println("starting");
		new Main().run();
	}
	
	public Main() {
		Options.setBasic();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		final AtomicBoolean exit = new AtomicBoolean(false);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				exit.set(true);
				System.out.println("stopping");
				unexportPin(2);
				unexportPin(3);
				unexportPin(4);
				unexportPin(5);
				unexportPin(6);
				unexportPin(7);
			}
		}));
		
		Pin pin2 = exportPin(2);
		Pin pin3 = exportPin(3);
		Pin pin4 = exportPin(4);
		Pin pin5 = exportPin(5);
		Pin pin6 = exportPin(6);
		Pin pin7 = exportPin(7);
		
		pin2.setDirection(Pin.DIRECTION_IN);
		pin3.setDirection(Pin.DIRECTION_IN);
		pin4.setDirection(Pin.DIRECTION_IN);
		pin5.setDirection(Pin.DIRECTION_IN);
		pin6.setDirection(Pin.DIRECTION_IN);
		pin7.setDirection(Pin.DIRECTION_IN);
		
		Pin[] keys = new Pin[] {pin2, pin3, pin4, pin5, pin6};
		
		char[] keyMapping = new char[] {
				'Z','X','C','V','B','N','M','<','>','/',
				'S','D','G','H','J','L',';',
				'Q','W','E','R','T','Y','U','I','O','P','[',']',
				'2','3','5','6','7','9','0','=','\n',
				};
		
		outer:
		while (true) {
			
			int code = 0;
			
			for (int i = 0 ; i < keys.length ; i ++) {
				
				if (exit.get()) {
					break outer;
				}
				
				Pin key = keys[i];
				String value = key.getValue();
				System.out.println("pin " + i + " = " + value);
				
				if (value.equals(Pin.VALUE_HIGH)) {
					
					code = (code << 1) | 1;
					
				} else if (value.equals(Pin.VALUE_LOW)) {
					
					code = (code << 1) | 0;
					
				} else if (value.equals(Pin.NONE)) {
					
					code = (code << 1) | 0;
				}
			}
			
			code = 0b11111 ^ code;
			
			if (code > 0 && code <= keyMapping.length) {
				
				System.out.println("code: " + code);
				
				robot.keyPress(keyMapping[code - 1]);
			}
		}
		
//		System.out.println("pin 2 value:" + pin2.getValue());
	}
	
	private Pin exportPin(int pin) {
		
		int index = Options.pinsStart + pin;
		byte[] bytes = String.format("%d", index).getBytes();
		
		try {
			Files.write(Options.exportPath, bytes, StandardOpenOption.WRITE);
			return new Pin(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void unexportPin(int pin) {
		
		int index = Options.pinsStart + pin;
		byte[] bytes = String.format("%d", index).getBytes();
		
		try {
			Files.write(Options.unexportPath, bytes, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
