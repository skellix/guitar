package com.skellix.guitar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Pin {
//	private int index;
	private Path directionPath;
	private Path valuePath;
	
	public static String DIRECTION_IN = "in";
	public static String DIRECTION_OUT = "out";
	public static String VALUE_HIGH = "1";
	public static String VALUE_LOW = "0";
	public static String NONE = "";

	public Pin(int index) {
//		this.index = index;
		Path pinPath = Options.basePath.resolve(String.format("gpio%d", index));
		directionPath = pinPath.resolve("direction");
		valuePath = pinPath.resolve("value");
	}
	
	public String getDirection() {
		
		try {
			byte[] bytes = Files.readAllBytes(directionPath);
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void setDirection(String direction) {
		
		try {
			Files.write(directionPath, direction.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getValue() {
		
		try {
			byte[] bytes = Files.readAllBytes(valuePath);
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void setValue(String value) {
		
		try {
			Files.write(valuePath, value.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
