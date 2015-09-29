LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Micro.g4
SEQ1:= $(shell seq 5 9)
SEQ2:= $(shell seq 18 22)

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
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test1.micro > testcases/output/test1.micro.output
	for i in $(SEQ1);\
	do \
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test$$i.micro > testcases/output/test$$i.micro.output;\
	done;
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test11.micro > testcases/output/test11.micro.output
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test13.micro > testcases/output/test13.micro.output
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test14.micro > testcases/output/test14.micro.output
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test16.micro > testcases/output/test16.micro.output
	for i in $(SEQ2);\
	do \
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test$$i.micro > testcases/output/test$$i.micro.output;\
	done;

diff:
	diff -a -b -B testcases/output/test1.micro.output testcases/output/test1.out 
	diff -a -b -B testcases/output/test11.micro.output testcases/output/test11.out 
	diff -a -b -B testcases/output/test13.micro.output testcases/output/test13.out 
	diff -a -b -B testcases/output/test14.micro.output testcases/output/test14.out 
	diff -a -b -B testcases/output/test16.micro.output testcases/output/test16.out 
	for i in $(SEQ1);\
	do \
	diff -a -b -B testcases/output/test$$i.micro.output testcases/output/test$$i.out;\
	done;
	for i in $(SEQ2);\
	do \
	diff -a -b -B testcases/output/test$$i.micro.output testcases/output/test$$i.out;\
	done;

clean:
	rm -rf classes build

.PHONY: all group compiler clean
