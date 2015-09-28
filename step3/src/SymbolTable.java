import java.util.*;
public class SymbolTable{
	private String scope;
	private SymbolTable parent;
	private ArrayList<SymbolTable> children;
	private LinkedHashMap<String,Symbol> table;
	
	public SymbolTable(String scope){
		this.scope = scope;
		this.parent = null;
		this.children = new ArrayList<SymbolTable>();
		this.table = new LinkedHashMap<String,Symbol>();
	}
	
	public SymbolTable(String scope, SymbolTable parent){
		this.scope = scope;
		this.parent = parent;
		this.children = new ArrayList<SymbolTable>();
		this.table = new LinkedHashMap<String,Symbol>();
	}
	
	public SymbolTable getParent(){
		return this.parent;
	}
	
	public LinkedHashMap<String,Symbol> getTable(){
		return this.table;
	}
	public void addChild(SymbolTable child){
		children.add(child);
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
				table.put(name,entry);
			}
	}
	
	public boolean shadowCheck(SymbolTable s,String name){
			if (s==null)
				return false;
	
			if(s.getTable().containsKey(name)){
				return true;
			}
			
			s=s.getParent();
			return shadowCheck(s,name);
			
	}
	
}