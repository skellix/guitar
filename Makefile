build.log: src/com/skellix/guitar/*.java
	mkdir -p bin
	javac -d bin $^ > $@
run: build.log
	sudo java -cp bin com.skellix.guitar.Main
