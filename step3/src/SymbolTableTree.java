import java.util.*;
public class SymbolTableTree{
	public SymbolTable root;
	public SymbolTable currscope;
	public int blocknum;

	
	public SymbolTableTree(){
		this.root = new SymbolTable("GLOBAL");
		this.currscope= root;
		this.blocknum = 0;
	}
	public void enterscope() {
		currscope = currscope.children.get(currscope.children.size() - 1);
	}
	public void exitscope() {
		currscope = currscope.parent;
	}
	public void printAll() {
		System.out.println("ALL");
	}
}