import java.util.*;

public class Function{
	CodeGenerater codeGenerater;
	SymbolTable symbolTable;
	int paraNum;
	int localVarNum;
	int registernum;
	
	public Function(SymbolTable table){
		this.codeGenerater = new CodeGenerater();
		this.symbolTable = table;
		this.registernum = 1;
	}
	
	public CodeGenerater getCodeGenerater(){
		return this.codeGenerater;
	}
	public void setTnum() {
		this.codeGenerater.setTnum(this.registernum);
	}
	public SymbolTable getTable(){
		return this.symbolTable;
	}
	
	public String getRegister(){
			
		return "$T"+registernum++; 
	}
	
}