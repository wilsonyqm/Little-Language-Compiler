import java.util.*;
public class Symbol {
	private String name;
	private String type;
	private String value;
	private String attr;
	
	public Symbol(String name, String type){
		this.name = name;
		this.type = type;
		this.value = null;
		this.attr = null;
	}
	
	public Symbol(String name, String type,String attr){
		this.name = name;
		this.type = type;
		this.value = null;
		this.attr = attr;
	}
	
	
	public Symbol(String name, String type, String value, String attr){
		this.name = name;
		this.type = type;
		this.value = value;
		this.attr = attr;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getValue(){
		return this.value;
	}
	
}