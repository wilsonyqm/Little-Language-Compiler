import java.util.*;
public class SymbolTable{
	public String scope;
	public SymbolTable parent;
	public ArrayList<SymbolTable> children;
	public LinkedHashMap<String,Symbol> table;
	
	public SymbolTable(String scope){
		this.scope = scope;
		this.parent = null;
		this.children = new ArrayList<SymbolTable>();
		this.table = new LinkedHashMap<String,Symbol>();
	}
	
	// public SymbolTable(String scope, SymbolTable parent){
	// 	this.scope = scope;
	// 	this.parent = parent;
	// 	this.children = new ArrayList<SymbolTable>();
	// 	this.table = new LinkedHashMap<String,Symbol>();
	// }
	
	public SymbolTable getParent(){
		return this.parent;
	}
	
	public LinkedHashMap<String,Symbol> getTable(){
		return this.table;
	}
	public void addChild(SymbolTable child){
		children.add(child);
		child.parent = this;
	}
	
	public void addEntry(Symbol entry) throws IllegalArgumentException{
		String name = entry.getName();
		if(table.containsKey(name)){
			throw new IllegalArgumentException("DECLARATION ERROR "+name);
		}
		else{
			
			if (shadowCheck(this.parent,name)){
				System.out.println("SHADOW WARNING "+name);
			}
			//System.out.println("HASH"+name);
			table.put(name,entry);
		}
	}
	
	public boolean shadowCheck(SymbolTable s,String name){
		boolean b = false;
		while (s != null) {
			if(s.getTable().containsKey(name)){
				b = true;
			}
			s = s.getParent();
		}
		return b;
	}
	
}