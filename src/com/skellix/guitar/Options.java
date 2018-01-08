package com.skellix.guitar;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Options {
	
	public static Path basePath;
	public static Path exportPath;
	public static Path unexportPath;
	public static int pinsStart;

	public static void setBasic() {
		basePath = Paths.get("/")
				.resolve("sys").resolve("class").resolve("gpio");
		exportPath = basePath.resolve("export");
		unexportPath = basePath.resolve("unexport");
		pinsStart = 408;
	}

}
