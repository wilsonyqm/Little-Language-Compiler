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

show:
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase3.micro

run:
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase3.micro > testcases/output/step4_testcase3.micro.output
	
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_expr.micro > testcases/output/test_expr.micro.output
	

diff:
	diff -bBw testcases/output/step4_testcase3.micro.output testcases/output/step4_testcase3.out 
	diff -bBw testcases/output/test_expr.micro.output testcases/output/test_expr.out 
	

clean:
	rm -rf classes build

.PHONY: all group compiler clean
