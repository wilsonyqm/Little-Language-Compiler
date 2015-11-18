import java.util.*;

public class Function{
	CodeGenerater codeGenerater;
	SymbolTable symbolTable;
	int paraNum;
	int localVarNum;
	int registernum;
	
	public function(SymbolTalbe table){
		this.codeGenerater = new CodeGenerater();
		this.symbolTable = table;
		this.registernum = 1;
	}
	
	public CodeGenerater getCodeGenerater(){
		return this.codeGenerater;
	}
	
	public SymbolTable getTable(){
		return this.symbolTable;
	}
	
	private String getRegister(){
			
		return "$T"+registernum++; 
	}
	
	private String getLocal(){
		return "$L"+localVarNum++;
	}
	
	private String getPara(){
		return "$P"+paraNum++;
	}
}