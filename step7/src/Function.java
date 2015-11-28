import java.util.*;

public class Function{
	CodeGenerater codeGenerater;
	SymbolTable symbolTable;
	int paraNum;
	int localVarNum;
	int registernum;
	
	public Function(SymbolTable table, ArrayList<Symbol> globalSymbols){
		this.codeGenerater = new CodeGenerater(globalSymbols);
		this.symbolTable = table;
		this.registernum = 1;
	}
	
	public CodeGenerater getCodeGenerater(){
		return this.codeGenerater;
	}
	
	public SymbolTable getTable(){
		return this.symbolTable;
	}
	
	public String getRegister(){
			
		return "$T"+registernum++; 
	}
	
}