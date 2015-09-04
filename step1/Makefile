LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Microlexer.g4

all: group compiler

group:
	@echo "Ziheng Liu, Qiaomu Yao"
compiler:
	rm -rf build
	mkdir build
	java -cp $(LIB_ANTLR) org.antlr.v4.Tool -o build $(ANTLR_SCRIPT)
	rm -rf classes
	mkdir classes
	javac -cp $(LIB_ANTLR) -d classes src/*.java build/*.java
run:
	java -cp lib/antlr.jar:classes/ Micro input/sqrt.micro > output/sqrt.micro.output
	java -cp lib/antlr.jar:classes/ Micro input/nested.micro > output/nested.micro.output
	java -cp lib/antlr.jar:classes/ Micro input/loopbreak.micro > output/loopbreak.micro.output
	java -cp lib/antlr.jar:classes/ Micro input/loop.micro > output/loop.micro.output
	java -cp lib/antlr.jar:classes/ Micro input/fibonacci.micro > output/fibonacci.micro.output
clean:
	rm -rf classes build

.PHONY: all group compiler clean
