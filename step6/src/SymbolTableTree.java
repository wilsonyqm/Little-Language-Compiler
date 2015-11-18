import java.util.*;
public class SymbolTableTree{
	public SymbolTable root;
	public SymbolTable currscope;

	
	public SymbolTableTree(){
		this.root = new SymbolTable("GLOBAL");
		this.currscope= root;
	}
	public void enterscope() {
		currscope = currscope.children.get(currscope.children.size() - 1);
	}
	public void exitscope() {
		currscope = currscope.parent;
	}
	
	public String checkType(String value){
		try{
			int integer = Integer.parseInt(value);
			return "INT";
		}
		catch(NumberFormatException e){
		}
		
		try{
			float floatnum = Float.parseFloat(value);
			return "FLOAT";
		}
		catch(NumberFormatException e){
		}
		
		SymbolTable scope = currscope;
		while(scope!=null){
			if(scope.checkType(value)!=null)
				return scope.checkType(value);
			
			scope = scope.parent;
		}
		return null;
	}
	
	
	
	public void printAll(SymbolTable table) {
		if (table == null)
		return;
		table.printTable();
		if(table.children!=null){
		for(int i=0;i<table.children.size();i++){
			SymbolTable child_table = table.children.get(i);
			printAll(child_table);
		}
		}
	}

	public ArrayList<Symbol> getAllsymbols(SymbolTable table) {
		ArrayList<Symbol> res = new ArrayList<>();
		if (table == null)
			return res;
		
		res.addAll(table.getSymbols());
		if(table.children!=null){
			for(int i=0;i<table.children.size();i++){
				SymbolTable child_table = table.children.get(i);
				res.addAll(getAllsymbols(child_table));
			}
		}
		return res;
	}
}