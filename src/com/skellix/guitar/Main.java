package com.skellix.guitar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main implements Runnable {

	public static void main(String[] args) {
		System.out.println("starting");
		new Main().run();
		System.out.println("stopping");
	}
	
	public Main() {
		Options.setBasic();
		
	}

	@Override
	public void run() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
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
		System.out.println("pin 2 value:" + pin2.getValue());
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
