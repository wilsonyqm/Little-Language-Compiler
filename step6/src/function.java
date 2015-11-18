import java.util.*;

public class Function{
	CodeGenerater codeGenerater;
	SymbolTable symboltable;
	int paraNum;
	int localVarNum;
	
	public function(SymbolTalbe table){
		this.codeGenerater = new CodeGenerater();
		this.SymbolTable = table;
		
	}
	
	public CodeGenerater getCodeGenerater(){
		return this.codeGenerater;
	}
}