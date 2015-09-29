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
	
}