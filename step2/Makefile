LIB_ANTLR := lib/antlr.jar
ANTLR_SCRIPT := Micro.g4
SEQ:= $(shell seq 1 22)

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
	for i in $(SEQ);\
	do \
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test$$i.micro > testcases/output/test$$i.micro.output;\
	done;

diff:
	for i in $(SEQ);\
	do \
	diff -a -b testcases/output/test$$i.micro.output testcases/output/test$$i.out >>testcases/result.txt;\
	done;

clean:
	rm -rf classes build

.PHONY: all group compiler clean
