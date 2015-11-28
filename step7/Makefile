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
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fma.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/factorial2.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fibonacci2.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase2.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase3.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_573.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_adv.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_expr.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_for.micro
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_if.micro
	
run:
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fma.micro > testcases/output/fma.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/factorial2.micro > testcases/output/factorial2.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fibonacci2.micro > testcases/output/fibonacci2.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase.micro > testcases/output/step4_testcase.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase2.micro > testcases/output/step4_testcase2.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/step4_testcase3.micro > testcases/output/step4_testcase3.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_573.micro > testcases/output/test_573.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_adv.micro > testcases/output/test_adv.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_expr.micro > testcases/output/test_expr.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_for.micro > testcases/output/test_for.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/test_if.micro > testcases/output/test_if.micro.out
	
diff:
	diff -bBw testcases/output/fma.micro.out testcases/output/fma.out 
	diff -bBw testcases/output/factorial2.micro.out testcases/output/factorial2.out 
	diff -bBw testcases/output/fibonacci2.micro.out testcases/output/fibonacci2.out 
	diff -bBw testcases/output/step4_testcase.micro.out testcases/output/step4_testcase.out 
	diff -bBw testcases/output/step4_testcase2.micro.out testcases/output/step4_testcase2.out 
	diff -bBw testcases/output/step4_testcase3.micro.out testcases/output/step4_testcase3.out 
	diff -bBw testcases/output/test_573.micro.out testcases/output/test_573.out 
	diff -bBw testcases/output/test_adv.micro.out testcases/output/test_adv.out 
	diff -bBw testcases/output/test_expr.micro.out testcases/output/test_expr.out
	diff -bBw testcases/output/test_for.micro.out testcases/output/test_for.out 
	diff -bBw testcases/output/test_if.micro.out testcases/output/test_if.out 
	
clean:
	rm -rf classes build

.PHONY: all group compiler clean
