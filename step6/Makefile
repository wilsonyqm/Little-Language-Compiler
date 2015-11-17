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
	
run:
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fma.micro > testcases/output/fma.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/factorial2.micro > testcases/output/factorial2.micro.out
	java -cp $(LIB_ANTLR):classes/ Micro testcases/input/fibonacci2.micro > testcases/output/fibonacci2.micro.out
	
diff:
	diff -bBw testcases/output/fma.micro.out testcases/output/fma.out 
	diff -bBw testcases/output/factorial2.micro.out testcases/output/factorial2.out 
	diff -bBw testcases/output/fibonacci2.micro.out testcases/output/fibonacci2.out 
	
clean:
	rm -rf classes build

.PHONY: all group compiler clean
